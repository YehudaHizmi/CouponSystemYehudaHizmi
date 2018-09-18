package coupon.sys.core.facade;

import java.util.Collection;
import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * This class implements all the business logic associated with the company
 * It has three attributes: one of type CompanyDao and the other of type couponDaoDB and a relevant company (since a company performs the actions) 
 * @author Yehuda.Hizmi
 *
 */
public class CompanyFacade implements ClientFacade {
	
	private CouponDao couponDaoDb;
	private	CompanyDao companyDaoDb;
	private Company company;
	//private ClientFacade clientFacade;

	/**
	 * The constructor initialize the couponDaoDb and companyDaoDb and the company attributes 
	 * @param couponDaoDb - reference to the couponDaoDb object
	 * @param companyDaoDb - reference to the companyDaoDb object
	 * @param company - reference to the company that just logged in
	 */
	public CompanyFacade(CouponDao couponDaoDb,CompanyDao companyDaoDb, Company company) {
		this.couponDaoDb = couponDaoDb;
		this.companyDaoDb = companyDaoDb; 
		this.company = company;
	}
	
	/*
	@Override
	public ClientFacade login(String name, String password, String clientType) throws CouponSystemException {
			try {
				if (clientType.equals("Company") && companyDaoDb.login(name, password)) {				
					clientFacade = new CompanyFacade(couponDaoDb, companyDaoDb, companyDaoDb.getCompany(name));
				} else {				
					throw new CouponSystemException("Wrong user name or password please try again");
				}
			} catch (CouponSystemException e) {
				throw new CouponSystemException("Wrong user name or password please try again",e);
			}			
		return clientFacade;
	}
	*/

	/**
	 * This method activate the createCoupon method at the couponDaoDb class
	 * It gets a coupon object to create as a parameter.
	 * Since it is not possible to create two coupons with the same title.
	 * The method: 
	 * 1.check if a coupon with the same title already exists in the system in case there is raise exception otherwise insert it 
	 * @param coupon - the coupon to create 
	 * @throws CouponSystemException - throws a general exception with a message "Coupon title: [" + coupon.getTitle() + "] already exists in the system"
	 */
	public void createCoupon(Coupon coupon) throws CouponSystemException {
		//Check if the coupons title already exists in the DB		
		if (!couponDaoDb.checkDbExistence(coupon)) {
			//If the title doesn't exist insert it
			couponDaoDb.createCoupon(coupon);
			//Also insert a record into to company coupon table
			companyDaoDb.createCompanyCouponRecord(company.getId(), coupon.getId());
		} else {
			//If the title exist throws exception
			throw new CouponSystemException("Coupon title: [" + coupon.getTitle() + "] already exists in the system");
		}
	}


	/**
	 * This method activate the removerCoupon at the couponDaoDB class.
	 * It gets a coupon object to delete as a parameter.
	 * this method check it the coupon the company asks to delete actually belong to it by activating the isCopuonBelongsCompany method. 
	 * @param coupon - the coupon that need to be deleted
	 * @throws CouponSystemException - throws two types of exceptions: "Coupon [Coupon title: " + coupon.getTitle() +  "] doesn't belong to " + this.company.getCompName() + " company" or 
	 * "Coupon [Coupon name: " + coupon.getTitle() +  "] doesn't exists in the system"
	 * 		 
	 */
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		/*
		 * When deleting a coupon the method 
		 * 1. Since the delete coupon that is sent from the customer is not the same object the original DB object is retrieved from the db
		 * 2. Since the delete coupon method get only the coupon as parameter and a validation of the linkage between the coupon and the company must be done
		 * 2. If the deleted coupon belongs to the company - the record from the following tables will be deleted delete: Company_Coupon, Customer_Coupon, Coupon
		 */
		Coupon dbCoupon = couponDaoDb.getCoupon(coupon.getTitle());
		if(dbCoupon != null) {
			if(companyDaoDb.isCopuonBelongsCompany(this.company.getId(),dbCoupon.getId())) {
				//if(companyDaoDb.isCopuonBelongsCompany(this.company.getId(),coupon.getId())) {
				couponDaoDb.removeCoupon(dbCoupon);	
			}else {
				throw new CouponSystemException("Coupon [Coupon title: " + coupon.getTitle() +  "] doesn't belong to " + this.company.getCompName() + " company");
			} 			
		} else {
			throw new CouponSystemException("Coupon [Coupon name: " + coupon.getTitle() +  "] doesn't exists in the system");
		}
	}

	/**
	 * This method activate the updateCoupon method at the couponDaoDB class.
	 * According to the business logic only the end date and the price can be updated.
	 * this method check it the coupon the company asks to update actually belong to it by activating the isCopuonBelongsCompany method. 
	 * @param coupon - The coupon to be updated
	 * @throws CouponSystemException - throws two types of exceptions: "Coupon [Coupon title: " + coupon.getTitle() +  "] doesn't belong to " + this.company.getCompName() + " company" or 
	 * "Coupon [Coupon name: " + coupon.getTitle() +  "] doesn't exists in the system"
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		/*
		 * This method updates only 2 parameters (the end date and the price) 
		 * since the delete coupon method get only the coupon as parameter therefore a linkage validation between the coupon and the company must be done.
		 * 1. First we retrieve the coupon from DB 
		 * 2. Update the retrieved instance - only the relevant parameters 
		 * 3. Activate updateCoupon method with the updatedCoupon as parameter
		 */
		Coupon updatedCoupon = couponDaoDb.getCoupon(coupon.getTitle());
		if(updatedCoupon != null) {
			if(companyDaoDb.isCopuonBelongsCompany(this.company.getId(),updatedCoupon.getId())) {				
				updatedCoupon.setEndDate(coupon.getEndDate());
				updatedCoupon.setPrice(coupon.getPrice());
				couponDaoDb.updateCoupon(updatedCoupon);
			} else {
				throw new CouponSystemException("Coupon [Coupon title: " + coupon.getTitle() +  "] doesn't belong to " + this.company.getCompName() + " company");
			}			
		} else {
			throw new CouponSystemException("Coupon [Coupon name: " + coupon.getTitle() +  "] doesn't exists in the system");
		}
	}

	/*
	 * Since the company who activate this method already logged in there aren't any checks to do 
	 */
	/**
	 * This method return the logged in company information  
	 * @return - The company that is logged in as an object
	 * @throws CouponSystemException throws an exception that is has thrown from other methods
	 */
	public Company viewCompanyInfromation() throws CouponSystemException{
		//Since the company who activate this method already logged in there aren't any checks to do 
		return companyDaoDb.getCompany(this.company.getId());
	}
	
	/**
	 * This method activate the getCoupon(long id) at the couponDaoDB class.
	 * this method check it the coupon the company asks to get actually belong to it by activating the isCopuonBelongsCompany method. 
	 * @param coupon_id - The coupon id that need to be returned
	 * @return - A coupon as an object
	 * @throws CouponSystemException - throws two types of exceptions: "Coupon [Coupon title: " + coupon.getTitle() +  "] doesn't belong to " + this.company.getCompName() + " company" or 
	 * "Coupon [Coupon name: " + coupon.getTitle() +  "] doesn't exists in the system"
	 */
	public Coupon getCoupon(long coupon_id) throws CouponSystemException {
		//first we check if this coupon exists in the system
		Coupon coupon = couponDaoDb.getCoupon(coupon_id);
		if(coupon != null) {			
			//checks if it belongs to the company
			if(companyDaoDb.isCopuonBelongsCompany(this.company.getId(),coupon_id)) {
				return coupon;
			} else {
				throw new CouponSystemException("Coupon [Coupon title: " + coupon.getTitle() +  "] doesn't belong to " + this.company.getCompName() + " company");
			}	
		} else {
			throw new CouponSystemException("Coupon [Coupon id: " + coupon_id +  "] doesn't exists in the system");
		}
	}
	
	/**
	 * This method activate getAllCoupons(long id) method at couponDaoDB class. 
	 * @return - a collection of all the coupons associated with the company
	 * @throws CouponSystemException - throws a general exception with a message "There aren't any listed coupons in the system"
	 */
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		//Since the method gets the company as parameter and the company must be logged in in order to activate it than no validation needs to take place
		Collection<Coupon> coupons = companyDaoDb.getCoupons(this.company);
		if (coupons != null) {
			return coupons;
		} else {
			throw new CouponSystemException("There aren't any listed coupons in the system");
		}
	}

	
	/**
	 * This method activate getCouponByType(company,couponType) method at customerDaoDB class.
	 * @param couponType - The type of coupons that need to be returned 
	 * @return - a collection of all the coupons associated with the company
	 * @throws CouponSystemException - throws a general exception with a message "There aren't any listed coupons in the system"
	 */
	public Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException {
		//Since the company who activate it already logged in than it been sent also as parameter  
		Collection<Coupon> coupons =  companyDaoDb.getCouponsByType(this.company,couponType);
		if (coupons != null) {
			return coupons;
		} else {
			throw new CouponSystemException("There aren't any listed coupons of " + couponType + " in the system");
		}
	}

	/**
	 * This method activate getCouponsUpToPrice(company,price) method at customerDaoDB class.
	 * @param price - The price that the coupons are equal or less
	 * @return -  a collection of all the coupons associated with the company
	 * @throws CouponSystemException - throws a general exception with a message "There aren't any listed coupons with a price less than " + price + " in the system"
	 */
	public Collection<Coupon> getCouponByPrice(double price) throws CouponSystemException {
		//Since the company who activate it already logged in than it been sent also as parameter
		Collection<Coupon> coupons = companyDaoDb.getCouponsUpToPrice(this.company, price);
		if (coupons != null) {
			return coupons;
		} else {
			throw new CouponSystemException("There aren't any listed coupons with a price less than " + price + " in the system");
		}
	}

	/**
	 * This method activate getCouponsUpToDate(company,date) method at customerDaoDB class.
	 * @param date - The date that the coupons are equal or less
	 * @return - a collection of all the coupons associated with the company
	 * @throws CouponSystemException - throws a general exception with a message "There aren't any listed coupons with an end date less than " + date + " in the system"
	 */
	public Collection<Coupon> getCouponByDate(java.util.Date date) throws CouponSystemException {
		//Since the company who activate it already logged in than it been sent also as parameter  
		Collection<Coupon> coupons = companyDaoDb.getCouponsUpToDate(this.company, date);
		if (coupons != null) {
			return coupons;
		} else {
			throw new CouponSystemException("There aren't any listed coupons with an end date less than " + date + " in the system");
		}
	}	
}

