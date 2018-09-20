package coupon.sys.core.configuration;



//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
/**
 * 
 * @author Yehuda.Hizmi
 * A configuration - this class contains a general definitions and constants   
 * <br> DB_URL - the path to the DB
 * <br> DRIVER - oracle jdbc driver 
 * <br> DB_MAX_CONNECTIONS - the maximum number of available connection 
 * <br> EMAIL_BODY_SUCCESS - the body message to be send in case of a successful thread
 * <br> EMAIL_BODY_CLOSE - the close message to be send
 * <br> EMAIL_BODY_FAILURE - the body message to be send in case of a failure thread
 * <br> SUBJECT_SUCCESS - the subject message to be send in case of a successful thread
 * <br> SUBJECT_FAILURE - the subject message to be send in case of a failure thread
 * <br> TO - a list of email's
 * <br> CC - a list of email's
 * <br> SENDER - email of the sender
 * <br> SENDER_PASSWORD - email's password of the sender
 * <br> ADMIN_USERNAME - user name of administrator
 * <br> ADMIN_PASSWORD - password of administrator
 * <br> SUB_PROC_SLEEP_TIME - thread sleep time
 * 
 */
public class Configuration {

	public String DB_URL;
	public String DRIVER;
	public int DB_MAX_CONNECTIONS;
	public String EMAIL_BODY_SUCCESS; 
	public String EMAIL_BODY_CLOSE; 
	public String EMAIL_BODY_FAILURE; 
	public String SUBJECT_SUCCESS;
	public String SUBJECT_FAILURE;
	public String TO;  
	public String CC; 
	public String SENDER; 
	public String SENDER_PASSWORD;
	public String ADMIN_USERNAME;
	public String ADMIN_PASSWORD;
	public long SUB_PROC_SLEEP_TIME;
	

	private static Configuration instance = new Configuration();

	
	private Configuration() {
			
		this.DB_MAX_CONNECTIONS =  10;
		this.DB_URL = "jdbc:derby://localhost:1527/CouponSystem;create=true";
		this.DRIVER = "oracle.jdbc.driver.OracleDriver";
		this.EMAIL_BODY_FAILURE = "An error accured during thread session" + "<br><br> Regards, <br>Coupon System auto email";
		this.SUBJECT_FAILURE = "Error on Old Coupons Delete Process"; 
		this.EMAIL_BODY_SUCCESS = "Old Coupons Delete Process Finished Successfully";
		this.EMAIL_BODY_CLOSE = "<br><br> Regards, <br>Coupon System auto email";		
		this.SUBJECT_SUCCESS = "Old Coupons Delete Process"; 				
		this.TO = "yehuda.hizmi@gmail.com";
		this.CC = "yehuda.hizmi@gmail.com";
		this.SENDER = "CouponSystemAuto@gmail.com";
		this.SENDER_PASSWORD = "CouponSystem123";
		this.ADMIN_USERNAME = "admin"; 
		this.ADMIN_PASSWORD = "1234"; 
		this.SUB_PROC_SLEEP_TIME = 1000 * 60 * 60 * 24; //Sleep for 24 hours
		
	}

	public static Configuration getInstance() {
		return instance;
	}
}
