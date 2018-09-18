package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
//import coupon.sys.core.beans.CouponType;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * This class in and interface class and all its methods available in CompanyDaoDb class
 * @author Yehuda.Hizmi
 *
 */
public interface CompanyDao {

	/**
	 * This method return true if the company name and the company password match to the information stored in the DB else false. 
	 * Before the method check the existence it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param compName - The company name
	 * @param password - the company password
	 * @return - Return true in case the data is correct otherwise false
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	boolean login(String compName, String password) throws CouponSystemException;
	/**
	 * This method insert a new company into the data base (Company table)
	 * Before the method insert the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param company - a company object with all the relevant information to be inserted. The id is automatically given and updated back into the object 	
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void createCompany(Company company) throws CouponSystemException;

	/**
	 * This method delete a company from the data base. 
	 * The delete process is as follow: 
	 * 1.Delete all the relevant records from Customer_Coupon table 
	 * 2.Delete all the relevant records from Coupon table 
	 * 3.Delete all the relevant records from Company_Coupon table 
	 * 4.Delete all the relevant records from Company table
	 * Before the method delete the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param company - the company to be deleted
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void removeCompany(Company company) throws CouponSystemException;

	/**
	 * This method update the information of a company. 
	 * Before the method update the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param company - the company to be updated
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void updateCompany(Company company) throws CouponSystemException;

	/**
	 * This method return a company object based on the id it gets as a parameter.
	 * the method retrieves the information from the db create a company object and return it.  
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param id - The company id to return 
	 * @return - Return a company object
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Company getCompany(long id) throws CouponSystemException;

	/**
	 * This method returns a collection of all the companies. 
	 * the method retrieves the information from the db create a collection of all the companies and return it.
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @return - Return a collection of all the companies
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Company> getAllCompanies() throws CouponSystemException;

	/**
	 * This method return a collection of all the coupons associated with the company sent as a parameter
	 * the method retrieves the information from the db create a collection of all the coupons and return it.
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param company - The company that its coupons need to be returned
	 * @return - Return a collection of all the coupons associated with the company sent as a parameter
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getCoupons(Company company) throws CouponSystemException;

	/**
	 * This method checks if a company with the same name already exists in the DB. in case there is it return true otherwise false
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param company - the company that its name need to check
	 * @return - Return true if the name of the company already exists in the DB otherwise false 
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	boolean checkDbExistence(Company company) throws CouponSystemException;
	
	/**
	 * This method insert a record into Company Coupon table every time a company creates a new coupon
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param comp_id - The id of the company who created the coupon
	 * @param coupon_id - The id of the coupon that need to be associated to the company
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void createCompanyCouponRecord(long comp_id, long coupon_id) throws CouponSystemException;
	
	/**
	 * This method is activated in several cases (when delete a coupon, update a coupon and get a coupon).
	 * It is activated in order to make sure the coupon is linked to the correct company. 
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param comp_id - The company id
	 * @param coupon_id - The coupon id 
	 * @return - Return true if the coupon is linked to the company otherwise false 
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	boolean isCopuonBelongsCompany(long comp_id, long coupon_id) throws CouponSystemException;

	/**
	 * This method return a company object based on the name it gets as a parameter.
	 * the method retrieves the information from the db create a company object and return it.  
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param name - The company name to return 
	 * @return - Return a company object
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Company getCompany(String name) throws CouponSystemException;
	
	/**
	 * This method return a collection of coupons based on the type it gets as a parameter
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param company - The company its coupons need to return
	 * @param couponType - The type of coupons that need to return
	 * @return - Return a collection of coupons based on the type it gets as a parameter
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getCouponsByType(Company company, CouponType couponType) throws CouponSystemException;
	
	/**
	 * This method return a collection of coupons based on the price it gets as a parameter
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param company - The company its coupons need to return
	 * @param upToPrice - The price of coupons that need to return (less or equals to the price)
	 * @return - Return a collection of coupons based on the price it gets as a parameter
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getCouponsUpToPrice(Company company, double upToPrice) throws CouponSystemException;
	
	/**
	 * This method return a collection of coupons based on the date it gets as a parameter
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param company - The company its coupons need to return
	 * @param upToDate - The date of coupons that need to return (less or equals to the date)
	 * @return - Return a collection of coupons based on the date it gets as a parameter
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getCouponsUpToDate(Company company, java.util.Date upToDate) throws CouponSystemException;
	
	/**
	 * This Method return the Id that the DB gave at the time the company was created. 
	 * @param name - The name of the company its id you ask 
	 * @return - Return the id of the company.
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	long getCompanyIdFromName(String name) throws CouponSystemException;
}
