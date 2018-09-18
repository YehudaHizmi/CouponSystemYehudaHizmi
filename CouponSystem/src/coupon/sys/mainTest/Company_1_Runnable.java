package coupon.sys.mainTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.CompanyFacade;

public class Company_1_Runnable implements Runnable {

	private CouponSystem couponSystem;
	private String userName;
	private String password;
	
	public Company_1_Runnable(String userName, String password) throws CouponSystemException {
		this.couponSystem = CouponSystem.getInstance();
		this.userName = userName;
		this.password = password;
	}
	
	@Override
	public void run() {
		try {
			CompanyFacade companyFacade = (CompanyFacade) couponSystem.login(this.userName, this.password, "Company");
			System.out.println(this.userName + " is logged in");
			Coupon coupon1 = new Coupon("Greg 30% off", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.AUGUST, 1).getTime(), 75,
					CouponType.RESTURANT, "For all users", 25, "No Image");
			Coupon coupon2 = new Coupon("Landver 1+1", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.SEPTEMBER, 30).getTime(), 150,
					CouponType.RESTURANT, "For all users", 70, "No Image");
			Coupon coupon3 = new Coupon("Landver 24% off on breakfast", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JUNE, 5).getTime(), 150,
					CouponType.RESTURANT, "For all users", 70, "No Image");
			Coupon coupon4 = new Coupon("Coffe Goo - Free 1 + 1", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JUNE, 8).getTime(), 150,
					CouponType.RESTURANT, "For all users", 70, "No Image");
			//Create a coupon object without creating an instance in the DB in order to check the delete method that gets a coupon object but retrieve the 
			//original DB object
			Coupon toDeleteCoupon = new Coupon("Greg 30% off", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.AUGUST, 1).getTime(), 75,
					CouponType.RESTURANT, "For all users", 25, "No Image");	
			Coupon coupon5 = new Coupon("25% off on all Ovens", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JULY, 31).getTime(), 150,
					CouponType.ELECTRICITY, "For all users", 0, "No Image");
			companyFacade.createCoupon(coupon1);	
			System.out.println(this.userName + " has created a new coupon: " + coupon1.getTitle());
			companyFacade.createCoupon(coupon2);										
			System.out.println(this.userName + " has created a new coupon: " + coupon2.getTitle());
			companyFacade.createCoupon(coupon3);										
			System.out.println(this.userName + " has created a new coupon: " + coupon3.getTitle());
			companyFacade.createCoupon(coupon4);										
			System.out.println(this.userName + " has created a new coupon: " + coupon4.getTitle());
			System.out.println(this.userName + " created the following coupons: ");
			System.out.println(this.userName + " - " + companyFacade.getAllCoupons());
			Thread.sleep(6000);
			System.out.println(this.userName + " delete a coupon " + toDeleteCoupon.getTitle() );
			companyFacade.removeCoupon(toDeleteCoupon);
			System.out.println(this.userName + " - " + companyFacade.getAllCoupons());
			System.out.println(this.userName + " tries to update a coupon of different company");
			companyFacade.updateCoupon(coupon5);			
			System.out.println(this.userName + " Finished");
		} catch (CouponSystemException | InterruptedException e) {
			e.printStackTrace();
		}		
	}

}
