package coupon.sys.core.facade;

//import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.dao.db.utils.GeneralMethods;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * This class implements all the business logic associated with the customer
 * It has three attributes: one of type CustomerDao and the other of type CouponDao and a relevant customer (since a customer performs the actions) 
 * @author Yehuda.Hizmi
 *
 */
public class CustomerFacade implements ClientFacade {

	private CouponDao couponDaoDb;
	private CustomerDao customerDaoDb;
	private Customer customer;
	//private ClientFacade clientFacade;
	
	/**
	 * The constructor initialize the couponDaoDb and customerDaoDb and the customer attributes 
	 * @param couponDaoDb - reference to the couponDaoDb object
	 * @param customerDaoDb - reference to the customerDaoDb object
	 * @param customer - reference to the customer that just logged in
	 */
	public CustomerFacade(CouponDao couponDaoDb, CustomerDao customerDaoDb, Customer customer) {
		this.couponDaoDb = couponDaoDb;
		this.customerDaoDb = customerDaoDb;
		this.customer = customer;
	}

	/*
	@Override
	public ClientFacade login(String name, String password, String clientType) throws CouponSystemException {
			try {
				if (clientType.equals("Customer") && customerDaoDb.login(name, password)) {			
					clientFacade = new CustomerFacade(couponDaoDb, customerDaoDb, customerDaoDb.getCustomer(name));
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
	 * This method activate several methods in the DAOs.
	 * Before it creates a record in customer coupon table it dose the following checks:
	 * check if the coupon the customer asks to purchase exists in the system if not throws exception.
	 * Checks if the customer already purchase this coupon in case it dose throws exception.
	 * Checks if there are available coupons if not throws exception.
	 * Checks if the coupon already expired if it dose throws exception.
	 * If non of the exceptions have thrown than the method active the createCustomerCouponRecord method at the customerDaoDb class and
	 * also the coupon amount to be less 1
	 * @param coupon - The coupon to be purchased
	 * @throws CouponSystemException - throws 4 different exceptions based on the condition:
	 * "Coupon [Coupon title: " + dbCoupon.getTitle() + " , Coupon id: "	+ dbCoupon.getId() + "] has expired on " + dbCoupon.getEndDate()
	 * "Coupon [Coupon title: " + dbCoupon.getTitle() + " , Coupon id: "	+ dbCoupon.getId() + "] is out of stock"
	 * "Coupon [Coupon title: " + dbCoupon.getTitle() + " , Coupon id: "	+ dbCoupon.getId() + "] has already been purchased"
	 * "Coupon [Coupon title: " + coupon.getTitle() +  "] doesn't exists in the system"
	 * 
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		//check if the customer already purchased the coupon
		Coupon dbCoupon = couponDaoDb.getCoupon(coupon.getId());
		//System.out.println(dbCoupon);
		if(dbCoupon != null) {
			//Coupon dbCoupon = couponDaoDb.getCoupon(coupon.getTitle());
			if(!customerDaoDb.hasAlreadyPurchased(customer.getId(), dbCoupon.getId())) {
				//check if the coupon is in stock
				if(dbCoupon.getAmount() > 0) {
					//Create a calendar without the time 
					Date currentdate = GeneralMethods.getCurrentDate();
					//Check if coupon's end date is greater or equals to current date
					if(currentdate.before(dbCoupon.getEndDate())){
						//Add record to Customer - Coupon table
						customerDaoDb.createCustomerCouponRecord(customer.getId(), dbCoupon.getId());
						//after the user purchased the coupon the amount reduced by 1
						dbCoupon.setAmount(dbCoupon.getAmount()-1);
						//Update the Db
						couponDaoDb.updateCoupon(dbCoupon);
					} else {
						throw new CouponSystemException("Coupon [Coupon title: " + dbCoupon.getTitle() + " , Coupon id: "	+ dbCoupon.getId() + "] has expired on " + dbCoupon.getEndDate());
					}
				} else {
					throw new CouponSystemException("Coupon [Coupon title: " + dbCoupon.getTitle() + " , Coupon id: "	+ dbCoupon.getId() + "] is out of stock");
				}			
			} else {
				throw new CouponSystemException("Coupon [Coupon title: " + dbCoupon.getTitle() + " , Coupon id: "	+ dbCoupon.getId() + "] has already been purchased");
			}
		} else {
			throw new CouponSystemException("Coupon [Coupon title: " + coupon.getTitle() +  "] doesn't exists in the system");
		}
			
	}
	
	/*
	 * Only this.customer is linked to the current facade and no checks need to be done
	 * Only this.customer activate this method and it been sent as a method to the function 
	 */
	/**
	 * This method activate the getCoupons method at the customerDaoDb class.
	 * @return - Return a collection of coupons associated with the customer
	 * @throws CouponSystemException - throws an exception with the message "There aren't any listed coupons in the system"
	 */
	public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException{
		Collection<Coupon> coupons = customerDaoDb.getCoupons(this.customer);
		if (coupons != null) {
			return coupons;
		} else {
			throw new CouponSystemException("There aren't any listed coupons in the system");
		}
	}

	/*
	 * Only this.customer is linked to the current facade and no checks need to be done
	 * Only this.customer activate this method and it been sent as a method to the function
	 */
/*	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws CouponSystemException{
		Collection<Coupon> coupons = customerDaoDb.getCoupons(this.customer);
		if(coupons != null) {			
			Collection<Coupon> couponsByType = new HashSet<>();
			for (Coupon coupon : coupons) {
				if (coupon.getType()==couponType) {
					couponsByType.add(coupon);				
				}			
			}
			if(!couponsByType.isEmpty()) {
				return couponsByType;
			} else {				
				throw new CouponSystemException("There aren't any listed coupons of type " + couponType + " in the system");
			}
		} else {
			throw new CouponSystemException("There aren't any listed coupons in the system");
		}
	}
	*/
	/*
	 * Only this.customer is linked to the current facade and no checks need to be done
	 * Only this.customer activate this method and it been sent as a method to the function
	 */
/*	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws CouponSystemException{
		Collection<Coupon> coupons = customerDaoDb.getCoupons(this.customer);
		if(coupons != null) {			
			Collection<Coupon> couponsUpToPrice = new HashSet<>();
			for (Coupon coupon : coupons) {
				if (coupon.getPrice() <= price) {
					couponsUpToPrice.add(coupon);				
				}			
			}
			if(!couponsUpToPrice.isEmpty()) {
				return couponsUpToPrice;
			} else {				
				throw new CouponSystemException("There aren't any listed coupons with a price less than " + price + "  in the system");
			}
		} else {
			throw new CouponSystemException("There aren't any listed coupons in the system");
		}
	}
	*/
	
	/**
	 * This method activate getCouponsByType(customer,couponType) method at customerDaoDB class.
	 * @param couponType - The type of coupons that need to be returned
	 * @return - a collection of all the coupons associated with the customer
	 * @throws CouponSystemException - throws a general exception with a message "There aren't any listed coupons of " + couponType + " in the system"
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws CouponSystemException{
		/*
		 * Only this.customer is linked to the current facade and no checks need to be done
		 * Only this.customer activate this method and it been sent as a method to the function
		 */
		Collection<Coupon> coupons =  customerDaoDb.getCouponsByType(this.customer, couponType);
		if (coupons != null) {
			return coupons;
		} else {
			throw new CouponSystemException("There aren't any listed coupons of " + couponType + " in the system");
		}
	}
		
	/**
	 * This method activate getCouponsUpToPrice(customer,price) method at customerDaoDB class.
	 * @param price - The price that the coupons are equal or less
	 * @return -  a collection of all the coupons associated with the customer
	 * @throws CouponSystemException - throws a general exception with a message "There aren't any listed coupons with a price less than " + price + " in the system"
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws CouponSystemException{
		/*
		 * Only this.customer is linked to the current facade and no checks need to be done
		 * Only this.customer activate this method and it been sent as a method to the function
		 */
		Collection<Coupon> coupons =  customerDaoDb.getCouponsUpToPrice(this.customer, price);
		if (coupons != null) {
			return coupons;
		} else {
			throw new CouponSystemException("There aren't any listed coupons with a price less than " + price + " in the system");
		}
	}
	
}
