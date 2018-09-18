package coupon.sys.core.coupon.system;

import coupon.sys.core.connection.ConnectionPool;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.dao.db.CompanyDaoDB;
import coupon.sys.core.dao.db.CustomerDaoDB;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.ClientFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;
import coupon.sys.core.sub.processes.DailyCouponExpirationTask;
import coupon.sys.core.dao.db.CouponDaoDb;

/**
 * This class is a singleton class - it is basically the only instance of the system.
 * <br>
 * It responsibilities are: 
 * <br> 1.Load all the DAOs classes
 * <br> 2.To start the Daily Coupon Expiration Task - a thread that runs every 24 hours and deletes all the expired coupons
 * <br> 3.Get an instance of the connection pool 
 * @author Yehuda.Hizmi
 */
public class CouponSystem {
	
	private ClientFacade clientFacade;
	public CompanyDao companyDao;
	public CustomerDao customerDao;
	public CouponDao couponDao;
	private ConnectionPool connectionPool; 
	private DailyCouponExpirationTask dailyCouponExpirationTask;
	
	private static CouponSystem instance;
	
	/**
	 * This is a private constructor and it responsible on:
	 * <br> 1.Load all the DAOs classes
	 * <br> 2.To start the Daily Coupon Expiration Task - a thread that runs every 24 hours and deletes all the expired coupons
	 * <br> 3.Get an instance of the connection pool  
	 * @throws CouponSystemException - general exception with the message "Can get a connection pool instance or activate \"Daily Coupon Expiration Delete Task\" " and the object e that causes it
	 */
	private CouponSystem() throws CouponSystemException {		
		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		companyDao = new CompanyDaoDB();
		customerDao = new CustomerDaoDB();
		couponDao = new CouponDaoDb();
		try {
			connectionPool = ConnectionPool.getInstance();
			Thread dailyCouponExpirationTaskThread = new Thread(dailyCouponExpirationTask , "Daily Coupon Expiration Delete Task");
			dailyCouponExpirationTaskThread.start();
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Can get a connection pool instance or activate \"Daily Coupon Expiration Delete Task\" ",e);
		}
	}
	
	/**
	 * 
	 * @return - Return and instance of Coupon System
	 * @throws CouponSystemException - throws exception were thrown from other methods 
	 */
	public static synchronized CouponSystem getInstance() throws CouponSystemException {
		if(instance == null){			
			instance = new CouponSystem();
		}
		return instance;
	}

	/**
	 * This method responsible to return a Client Facade based on the login 
	 * @param name - The user name
	 * @param password - the user password
	 * @param clientType - one of the following: ADMIN, CUSTOMER, COMPANY 
	 * @return - Return the relevant facade based on the login 
	 * @throws CouponSystemException - general exception with the message "Wrong user name or password please try again" and the object e causes it
	 */
	//Get a facade according to the customer type and the success of the login process
	public ClientFacade login (String name, String password, String clientType) throws CouponSystemException {
		try {
			//in case the administrator id performing the login
			if(clientType.equals("Admin") && name.equals("admin") && password.equals("1234")){
				clientFacade = new AdminFacade(this.companyDao, this.customerDao);
			} else if (clientType.equals("Customer") && customerDao.login(name, password)) {				
				clientFacade = new CustomerFacade(couponDao, customerDao, customerDao.getCustomer(name));
			} else if (clientType.equals("Company") && companyDao.login(name, password)) {				
				clientFacade = new CompanyFacade(couponDao, companyDao, companyDao.getCompany(name));
			} else {				
				throw new CouponSystemException("Wrong user name or password please try again");
			}				
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Wrong user name or password please try again",e);
		}
		return clientFacade;
	}
	
	/**
	 * This method is activated in order to shutdown the system.
	 * It responsible to:
	 * <br> 1.Stop the Daily Coupon Expiration Task thread and
	 * <br> 2.Close all the connections in the connection pool
	 * @throws CouponSystemException - throws exception were thrown from other methods
	 * @throws InterruptedException - throws exception were thrown from other methods
	 */
	public void shutdown() throws CouponSystemException, InterruptedException {
		this.dailyCouponExpirationTask.stopTask();
		this.connectionPool.closeAllConnections();
	}

}
