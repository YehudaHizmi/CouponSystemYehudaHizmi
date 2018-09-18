package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.exceptions.CouponSystemException;
/**
 * This class in and interface class and all its methods available in  CustomerDaoDb class
 * @author Yehuda.Hizmi
 *
 */
public interface CustomerDao {

	/**
	 * This method return true if the customer name and the customer password match to the information stored in the DB else false. 
	 * Before the method check the existence it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param custName - The customer name
	 * @param password - the customer password
	 * @return - Return true in case the data is correct otherwise false
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	boolean login(String custName, String password) throws CouponSystemException;
	
	/**
	 * This method insert a new customer into the data base (Customer table)
	 * Before the method insert the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param customer - a customer object with all the relevant information to be inserted. The id is automatically given and updated back into the object 	
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void createCustomer(Customer customer) throws CouponSystemException;

	/**
	 * This method delete a customer from the data base. 
	 * The delete process is as follow: 
	 * 1.Delete all the relevant records from Customer_Coupon table 
	 * 2.Delete all the relevant records from Customer table 
	 * Before the method delete the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param customer - the customer to be deleted
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void removeCustomer(Customer customer) throws CouponSystemException;

	/**
	 * This method update the information of a customer. 
	 * Before the method update the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param customer - the customer to be updated
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void updateCustomer(Customer customer) throws CouponSystemException;

	/**
	 * This method return a customer object based on the id it gets as a parameter.
	 * the method retrieves the information from the db create a customer object and return it.  
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param id - The customer id to return 
	 * @return - Return a customer object
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Customer getCustomer(long id) throws CouponSystemException;

	/**
	 * This method returns a collection of all the customers. 
	 * the method retrieves the information from the db create a collection of all the customer and return it.
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @return - Return a collection of all the customers
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Customer> getAllCustomers() throws CouponSystemException;

	/**
	 * This method returns a collection of all the coupons of a customer it gets as a parameter. 
	 * the method retrieves the information from the db create a collection of all the coupons and return it.
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param customer - the customer its coupons need to return
	 * @return - Return a collection of the relevant coupons 
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException;

	/**
	 * This method checks if a customer with the same name already exists in the DB. in case there is it return true otherwise false
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param customer - the customer that its name need to check
	 * @return - Return true if the name of the customer already exists in the DB otherwise false 
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	boolean checkDbExistence(Customer customer) throws CouponSystemException;
	
	/**
	 * This method insert a record into Customer Coupon table every time a customer purchase a new coupon
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param cust_id - The id of the customer who purchased the coupon
	 * @param coupon_id - The id of the coupon that need to be associated to the customer
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	void createCustomerCouponRecord(long cust_id, long coupon_id) throws CouponSystemException;
	
	/**
	 * This method checks if a coupon the jas already linked to the customer. in case it has return true otherwise false
	 * @param cust_id - the customer id to be checked
	 * @param coupon_id - the coupon id to be checked
	 * @return - Return true if the coupon already linked to the customer otherwise false
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	boolean hasAlreadyPurchased(long cust_id, long coupon_id) throws CouponSystemException;

	/**
	 * This method return a customer object based on the name it gets as a parameter.
	 * the method retrieves the information from the db create a customer object and return it.  
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param name - The customer name to return 
	 * @return - Return a customer object
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Customer getCustomer(String name) throws CouponSystemException; 
		
	/**
	 * This method return a collection of coupons based on the type it gets as a parameter
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param customer - The customer its coupons need to return
	 * @param couponType - The type of coupons that need to return
	 * @return - Return a collection of coupons based on the type it gets as a parameter
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getCouponsByType(Customer customer, CouponType couponType) throws CouponSystemException;

	/**
	 * This method return a collection of coupons based on the price it gets as a parameter
	 * Before the method retrieves the information it gets a connection from the connection pool and once it finishes or we get an exception the connection is return back.
	 * @param customer - The customer its coupons need to return
	 * @param upToPrice - The price of coupons that need to return (less or equals to the price)
	 * @return - Return a collection of coupons based on the price it gets as a parameter
	 * @throws CouponSystemException - throws a general exception with a message and the original SQLException that causes it
	 */
	Collection<Coupon> getCouponsUpToPrice(Customer customer,  double upToPrice) throws CouponSystemException;

}
