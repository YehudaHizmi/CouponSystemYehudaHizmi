package coupon.sys.core.sub.processes;

//import java.util.Calendar;
import java.util.Collection;
//import java.util.Date;
//import java.util.HashSet;
import javax.mail.MessagingException;
import coupon.sys.core.dao.db.utils.TableBuilder;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.dao.db.utils.EmailingProcess;
import coupon.sys.core.dao.db.utils.GeneralMethods;
import coupon.sys.core.configuration.Configuration;

/**
 * This class implements Runnable and it responsible to delete all the expired coupons
 * @author Yehuda.Hizmi
 *
 */
public class DailyCouponExpirationTask implements Runnable {

	private CouponDaoDb couponDaoDb;
	private boolean quit;
	private Configuration config = Configuration.getInstance();
	private Collection<Coupon> couponsToDelete; 
	//private Collection<Coupon> coupons;

	public DailyCouponExpirationTask() throws CouponSystemException {
		this.quit = false;
	}
	
	/**
	 * In order to delete all the old coupons: 
	 * 1.1 get all the coupons from the DB as a collection 
	 * 1.2 check whether the end date of the coupon is less then current date in case yes 
	 * 1.3 activate the remove coupon method in the CouponDaoDb class
	 */
	@Override
	public void run() {
		try {
			couponDaoDb = new CouponDaoDb();
			//Endless loop
			while(!this.quit) {
				this.couponsToDelete = couponDaoDb.getCouponsUpToDate(GeneralMethods.getCurrentDate());
				if(this.couponsToDelete != null) {
					//Run over all coupons and check which coupon as expired and delete it from DB.
					for (Coupon coupon : couponsToDelete) {
							this.couponDaoDb.removeCoupon(coupon);
					}						
				} 
				EmailingProcess.generateAndSendEmail(config.SUBJECT_SUCCESS,TableBuilder.buildTable(this.couponsToDelete),config.TO,config.CC);
				System.out.println("An Email was successfully sent.");
				if(this.couponsToDelete != null) this.couponsToDelete.clear();
				//Sleep for 24 hours
				Thread.sleep(config.SUB_PROC_SLEEP_TIME);
			}
		} catch (CouponSystemException | InterruptedException e) {
			try {
				EmailingProcess.generateAndSendEmail(config.SUBJECT_FAILURE,config.EMAIL_BODY_FAILURE,config.TO,config.CC);
			//In case the failure Mailing Process failed
			} catch (MessagingException e1) {		
				e1.printStackTrace();		
				System.out.println("An Email was successfully sent.");
				e.printStackTrace();
			}
		//In case the success Mailing Process failed 	
		}catch (MessagingException e2) {
			System.out.println("An Email was successfully sent.");
			e2.printStackTrace();			
		}
	}

	public void stopTask() {
		this.quit = true;
	}

}
