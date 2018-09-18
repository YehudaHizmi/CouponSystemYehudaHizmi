package coupon.sys.core.dao;

import java.util.Collection;
//import java.util.Date;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.exceptions.CouponSystemException;
/**
 * This class in and interface class and all its methods available in  CouponDaoDb class
 * @author Yehuda.Hizmi
 *
 */
public interface CouponDao {

	/**
	 * This method insert a new coupon into the data base (Coupon table)
	 * Before the method insert the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param coupon - a coupon object with all the relevant information to be inserted. The id is automatically given and updated back into the object
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void createCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * This method delete a coupon from the data base. 
	 * The delete process is as follow: 
	 * 1.Customer_Coupon 2.Company_Coupon 3.Coupon
	 * 1.Delete all the relevant records from Customer_Coupon table 
	 * 2.Delete all the relevant records from Company_Coupon table 
	 * 3.Delete all the relevant records from Coupon table 
	 * Before the method delete the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param coupon -  the coupon to be deleted
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void removeCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * This method update the information of a coupon. 
	 * Before the method update the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param coupon - The coupon to be updated
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void updateCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * This method return a coupon object based on the id it gets as a parameter.
	 * the method retrieves the information from the db create a coupon object and return it.  
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param id - The coupon id to return 
	 * @return - Return a coupon object
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Coupon getCoupon(long id) throws CouponSystemException;
	
	/**
	 * This method return a coupon object based on the title it gets as a parameter.
	 * the method retrieves the information from the db create a coupon object and return it.  
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param title - The coupon title to return 
	 * @return - Return a coupon object
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Coupon getCoupon(String title) throws CouponSystemException;

	/**
	 * This method returns a collection of all the coupons. 
	 * the method retrieves the information from the db create a collection of all the coupons and return it.
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @return - Return a collection of all the coupons
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getAllCoupons() throws CouponSystemException;

	/**
	 * This method return a collection of coupons based on the type it gets as a parameter
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param couponType - The type of coupons that need to return
	 * @return - Return a collection of coupons based on the type it gets as a parameter
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getAllCouponsByType(CouponType couponType) throws CouponSystemException;

	/**
	 * This method checks if a coupon with the same title already exists in the DB. in case there is it return true otherwise false
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param coupon - the coupon that its title need to check
	 * @return - Return true if the title of the coupon already exists in the DB otherwise false 
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	boolean checkDbExistence(Coupon coupon) throws CouponSystemException;

	/**
	 * This method return a collection of coupons based on the date it gets as a parameter
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param upToDate - The date of coupons that need to return (less or equals to the date)
	 * @return - Return a collection of coupons based on the date it gets as a parameter
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getCouponsUpToDate(java.util.Date upToDate) throws CouponSystemException;
	
	/**
	 * This Method return the Id that the DB gave at the time the coupon was created. 
	 * @param title - The title of the coupon its id you ask 
	 * @return - Return the id of the coupon.
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	long getCouponIdFromName(String title) throws CouponSystemException;
	
}
