package coupon.sys.mainTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.CompanyFacade;

public class Company_3_Runnable implements Runnable {

	private CouponSystem couponSystem;
	private String userName;
	private String password;
	
	public Company_3_Runnable(String userName, String password) throws CouponSystemException {
		this.couponSystem = CouponSystem.getInstance();
		this.userName = userName;
		this.password = password;
	}
	
	@Override
	public void run() {
		try {
			CompanyFacade companyFacade = (CompanyFacade) couponSystem.login(this.userName, this.password, "Company");
			System.out.println(this.userName + " is logged in");
			Coupon coupon1 = new Coupon("Holmes Place - 30% off for 10 entrances", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 25,
										CouponType.HEALTH, "For all users", 50, "No Image");
			Coupon coupon2 = new Coupon("Gym Ninga - 10% off", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JULY ,22).getTime(), 100,
										CouponType.HEALTH, "For all users", 50, "No Image");
			Coupon coupon3 = new Coupon("Gym Ninga - 1 + 1 lessons", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.AUGUST, 1).getTime(), 50,
										CouponType.HEALTH, "For all users", 50, "No Image");
			companyFacade.createCoupon(coupon1);
			System.out.println(this.userName + " has created a new coupon: " + coupon1.getTitle());
			companyFacade.createCoupon(coupon2);										
			System.out.println(this.userName + " has created a new coupon: " + coupon2.getTitle());
			companyFacade.createCoupon(coupon3);										
			System.out.println(this.userName + " has created a new coupon: " + coupon3.getTitle());
			System.out.println(this.userName + " - " + companyFacade.getAllCoupons());
			Thread.sleep(20000);
			System.out.println(this.userName + " Finished");
		} catch (CouponSystemException | InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
