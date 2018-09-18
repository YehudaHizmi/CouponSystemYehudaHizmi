package coupon.sys.core.beans;

import java.util.Collection;
import java.util.HashSet;

/**
 * 
 * @author Yehuda.Hizmi
 * the class is a customer template<br>
 * it has two constructors:<br>
 * 1. a full constructor and <br>
 * 2. a constructor without the if since the id id given automatically in the DB
 *
 */
public class Customer {

	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	/**
	 * 
	 * @param id - The id of the Customer - this id is automatically given in the db
	 * @param custName - Customer's Name
	 * @param password - Cuatomer's password 
	 */
	public Customer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
		this.coupons = new HashSet<>();
	}
	
	/**
	 * This constructor dosen't have the id since it is automatically given in the db
	 * @param custName - Customer's Name
	 * @param password - Cuatomer's password 
	 */
	//constructor without the id (use for create company in DB)
	public Customer(String custName, String password) {
		super();
		this.custName = custName;
		this.password = password;
		this.coupons = new HashSet<>();
	}

	/**
	 * 
	 * @return - Return the customer's id
	 */
	public long getId() {
		return id;
	}

	/**
	 * 
	 * This method updates a new id to the customer
	 * This method is activated once a new customer is created and the db generated a new id
	 * @param id - Updated the customer's id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return - Return the customer's name
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * This method updates a new name to the customer
	 * @param custName - the customer's name to be updated
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * 
	 * @return - Return the customer's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method updates a new password to the customer
	 * @param password - The customer's password to be updated
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * this method and parameter is not in use
	 * @return - Return all the customer's coupons.
	 *  
	 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * 
	 * @param coupons - A Collection of coupons to be updated
	 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	/**
	 * This method adds a new coupon to the collection
	 * This method is not in use
	 * @param coupon - The coupon to be added
	 */
	public void addCoupon(Coupon coupon) {
		this.coupons.add(coupon);
	}
	
	/**
	 * @return - Return a String with all the customer's information
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons + "]";
	}

}
