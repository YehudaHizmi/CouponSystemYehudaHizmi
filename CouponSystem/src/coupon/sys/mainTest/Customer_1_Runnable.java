package coupon.sys.mainTest;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.CustomerFacade;

public class Customer_1_Runnable implements Runnable {

	private CouponSystem couponSystem;
	private String userName;
	private String password;
	/*
	 * Adding a direct access to the couponDb in order to retrieve coupons and simulating the processes of customers purchasing coupons
	 * the coupons will be initialized in the constructor
	 */
	CouponDao couponDaoDb = new CouponDaoDb();
	Coupon coupon1;
	Coupon coupon2;
	Coupon coupon3;
	Coupon coupon4;
	Coupon coupon5;
	
	public Customer_1_Runnable(String userName, String password) throws CouponSystemException {
		this.couponSystem = CouponSystem.getInstance();
		this.userName = userName;
		this.password = password;
		coupon1 = couponDaoDb.getCoupon("Holmes Place - 30% off for 10 entrances");
		coupon2 = couponDaoDb.getCoupon("Gym Ninga - 10% off");
		coupon3 = couponDaoDb.getCoupon("Buy TV get Radio system");
		coupon4 = couponDaoDb.getCoupon("Landver 1+1");
		coupon5 = couponDaoDb.getCoupon("Landver 24% off on breakfast");
	}
	
	@Override
	public void run() {
		try {
			CustomerFacade customerFacade = (CustomerFacade) couponSystem.login(this.userName, this.password, "Customer");
			System.out.println(this.userName + " is logged in");	
			customerFacade.purchaseCoupon(coupon1);
			System.out.println(this.userName + " has purchased " + coupon1.getTitle());
			Thread.sleep(7000);
			customerFacade.purchaseCoupon(coupon2);
			System.out.println(this.userName + " has purchased " + coupon2.getTitle());
			Thread.sleep(3000);
			customerFacade.purchaseCoupon(coupon3);
			System.out.println(this.userName + " has purchased " + coupon3.getTitle());
			Thread.sleep(6000);
			customerFacade.purchaseCoupon(coupon4);
			System.out.println(this.userName + " has purchased " + coupon4.getTitle());
			Thread.sleep(3000);
			System.out.println(this.userName + " has the following coupons: ");
			System.out.println(this.userName + " - " + customerFacade.getAllPurchasedCoupons());
			System.out.println(this.userName + " has Coupon of " + coupon1.getType() + " type:");
			System.out.println(this.userName + " - " + customerFacade.getAllPurchasedCouponsByType(CouponType.HEALTH));
			Thread.sleep(20000);
			//Customer tries to purchase an expired coupon
			System.out.println(this.userName + " tries to purchae an expired coupon:");
			customerFacade.purchaseCoupon(coupon5);
			System.out.println(this.userName + " has purchased " + coupon5.getTitle());
			Thread.sleep(3000);
			System.out.println(this.userName + " Finished");
		} catch (CouponSystemException | InterruptedException e) {
			e.printStackTrace();
		}		
	}

}
