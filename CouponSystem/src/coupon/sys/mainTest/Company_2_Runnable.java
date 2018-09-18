package coupon.sys.mainTest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.CompanyFacade;

public class Company_2_Runnable implements Runnable {

	private CouponSystem couponSystem;
	private String userName;
	private String password;
	
	public Company_2_Runnable(String userName, String password) throws CouponSystemException {
		this.couponSystem = CouponSystem.getInstance();
		this.userName = userName;
		this.password = password;
	}
	
	@Override
	public void run() {
		try {
			CompanyFacade companyFacade = (CompanyFacade) couponSystem.login(this.userName, this.password, "Company");
			System.out.println(this.userName + " is logged in");
			Coupon coupon1 = new Coupon("Buy TV get Radio system", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.SEPTEMBER, 12).getTime(), 100,
					CouponType.ELECTRICITY, "For all users", 0, "No Image");
			Coupon coupon2 = new Coupon("25% off on all Ovens", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JULY, 31).getTime(), 150,
					CouponType.ELECTRICITY, "For all users", 0, "No Image");			
			Coupon coupon3 = new Coupon("10% off on all products", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JULY, 27).getTime(), 1000,
					CouponType.ELECTRICITY, "For all Empolyees", 200, "No Image");
//			Coupon coupon3_updated = new Coupon("10% off on all products in store", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2019, Calendar.JULY, 27).getTime(), 980,
//					CouponType.ELECTRICITY, "For all Empolyees", 150, "No Image");
			companyFacade.createCoupon(coupon1);
			System.out.println(this.userName + " has created a new coupon: " + coupon1.getTitle());
			companyFacade.createCoupon(coupon2);										
			System.out.println(this.userName + " has created a new coupon: " + coupon2.getTitle());
			companyFacade.createCoupon(coupon3);										
			System.out.println(this.userName + " has created a new coupon: " + coupon3.getTitle());
			System.out.println(this.userName + " created the following coupons: ");
			System.out.println(this.userName + " - " + companyFacade.getAllCoupons());
			Thread.sleep(10000);
//			System.out.println("Company " + this.userName + " Update coupon");
//			System.out.println("Before update");
//			System.out.println(companyFacade.getCoupon(coupon3_updated.getId()));
//			System.out.println("After update");
//			System.out.println(companyFacade.getCoupon(coupon3_updated.getId()));
			System.out.println(this.userName + " Finished");
		} catch (CouponSystemException | InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
