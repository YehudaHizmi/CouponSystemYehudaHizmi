package coupon.sys.core.beans;

import java.util.Collection;
import java.util.HashSet;

/**
 * 
 * @author Yehuda.Hizmi
 * The class is a company template.<br> 
 * It has two constructors:<br>
 * 1. A full constructor and <br>
 * 2. A constructor without the if since the id id given automatically in the DB
 *
 */
public class Company {

	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	/**
	 * 
	 * @param id - id of the company - auto generated 
	 * @param compName - company name
	 * @param password - company password 
	 * @param email - company email 
	 * <br> this is a full contractor 
	 */
	public Company(long id, String compName, String password, String email) {
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
		this.coupons = new HashSet<>();
	}
	
	/**
	 * partially constructor - without the email
	 * <br>
	 * @param id - id of the company - auto generated 
	 * @param compName - company name
	 * @param password - company password  
	 * <br> this is a full contractor 
	 */
	// This constructor is in case the companies don't have email
	public Company(long id, String compName, String password) {
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.coupons = new HashSet<>();
	}

	/**
	 * partially constructor - without the id since it is auto generated in the DB
	 * <br>
	 * @param compName - company name
	 * @param password - company password 
	 * @param email - company email 
	 * <br> this is a full contractor 
	 */
	//constructor without the id (use for create company in DB)
	public Company( String compName, String password, String email) {
		super();
		this.compName = compName;
		this.password = password;
		this.email = email;
		this.coupons = new HashSet<>();
	}
	

	/**
	 * 
	 * @return - Return the company's id
	 */
	public long getId() {
		return id;
	}

	/**
	 * This method updates a new id to the company
	 * This method is activated once a new company is created and the db generated a new id
	 * @param id - Update the company's id
	 * 
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return - Return the company's name
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * This method updates a new name to the company
	 * @param compName - the company's name to be updated
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * This method updates a new password to the company
	 * @param password - the company's password to be updated
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return - Return the company's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method updates a new email to the company
	 * @param email the company's email to be updated
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * this method and parameter is not in use
	 * @return - Return all the company's coupons.
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
	 * @return - Return a String with all the company's information
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + ", coupons=" + coupons + "]";
	}

}
