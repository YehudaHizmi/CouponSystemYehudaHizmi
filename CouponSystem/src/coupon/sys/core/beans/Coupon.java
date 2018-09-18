package coupon.sys.core.beans;

import java.util.Date;

/**
 * 
 *  * @author Yehuda.Hizmi
 * the class is a coupon template
 * it has two constructors:
 * 1. a full constructor and 
 * 2. a constructor without the if since the id id given automatically in the DB  
 *
 */

public class Coupon {


	private long id;
	private String title;
	private java.util.Date startDate;
	private java.util.Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	
	/**
	 * @param id - The id of the Coupon - this id is automatically given in the db
	 * @param title - Title of the coupon
	 * @param startDate - The date of which the coupon will be active
	 * @param endDate - The date of which the coupon stop being active
	 * @param amount - Amount of available coupons 
	 * @param type - The type of the coupon
	 * @param message - Message of the coupon
	 * @param price - The price of the coupon
	 * @param image - image associated with the coupon 
	 */
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message, double price, String image) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}
	
	/**
	 * The constructor has no id parameter since it is auto given<br>
	 * @param title - Title of the coupon
	 * @param startDate - The date of which the coupon will be active
	 * @param endDate - The date of which the coupon stop being active
	 * @param amount - Amount of available coupons 
	 * @param type - The type of the coupon
	 * @param message - Message of the coupon
	 * @param price - The price of the coupon
	 * @param image - image associated with the coupon 
	 */
	//constructor without the id (use for create company in DB)
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message, double price, String image) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	/**
	 * 
	 * @return - Return the coupon's id
	 */
	public long getId() {
		return id;
	}

	/**
	 * This method updates a new id to the coupon
	 * This method is activated once a new coupon is created and the db generated a new id
	 * @param id - Update the coupon's id
	 * 
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return - Return the title of the coupon
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method updates a title to the coupon
	 * @param title - the coupon's name to be updated
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return - Return the start date of the coupon
	 */
	public java.util.Date getStartDate() {
		return startDate;
	}

	/**
	 * This method updates a new start date to the coupon
	 * @param startDate - updated start date of the coupon
	 */
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 
	 * @return - Return the coupon's type
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * This method updates a new type to the coupon
	 * @param type - Updated coupons type
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * 
	 * @return - Return the end date of the coupon
	 */
	public java.util.Date getEndDate() {
		return endDate;
	}
	
	/**
	 * This method updates a new end date to the coupon
	 * @param endDate - updated end date of the coupon 
	 */
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 
	 * @return - Return the amount of available coupons
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * This method updates a new amount of coupons
	 * @param amount - A new amount of available coupons
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * 
	 * @return - Return the message associated with the coupon
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * This method updates a new message to be associated with the coupon
	 * @param message - coupon's message to be updated
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 *
	 * @return - Return the current coupon's price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * This method updates a new price to the coupon
	 * @param price - coupon's price to be updated
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * 
	 * @return - Return an image associated with the coupon
	 */
	public String getImage() {
		return image;
	}

	/**
	 * This method updates a new image to be associated with the coupon
	 * @param image - coupon's image to be updated
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * @return - Return a String with all the coupon's information
	 */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", message=" + message + ", price=" + price + ", image=" + image
				+ "]";
	}

}
