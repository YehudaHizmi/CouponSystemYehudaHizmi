package coupon.sys.mainTest;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.CustomerFacade;

public class Customer_3_Runnable implements Runnable {

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
	
	public Customer_3_Runnable(String userName, String password) throws CouponSystemException {
		this.couponSystem = CouponSystem.getInstance();
		this.userName = userName;
		this.password = password;
		coupon1 = couponDaoDb.getCoupon("Buy TV get Radio system");
		coupon2 = couponDaoDb.getCoupon("Landver 1+1");
		coupon3 = couponDaoDb.getCoupon("10% off on all products");
	}
	
	@Override
	public void run() {
		try {
			CustomerFacade customerFacade = (CustomerFacade) couponSystem.login(this.userName, this.password, "Customer");
			System.out.println(this.userName + " is logged in");
			customerFacade.purchaseCoupon(coupon1);
			System.out.println(this.userName + " has purchased " + coupon1.getTitle());
			Thread.sleep(1);
			customerFacade.purchaseCoupon(coupon2);
			System.out.println(this.userName + " has purchased " + coupon2.getTitle());
			Thread.sleep(1);
			customerFacade.purchaseCoupon(coupon3);
			System.out.println(this.userName + " has purchased " + coupon3.getTitle());
			Thread.sleep(1);
			System.out.println(this.userName + " has the following coupons: ");
			System.out.println(customerFacade.getAllPurchasedCoupons());
			System.out.println(this.userName + " has Coupon of " + coupon1.getType() + " type:");
			System.out.println(customerFacade.getAllPurchasedCouponsByType(CouponType.ELECTRICITY));
			System.out.println(this.userName + " has Coupon of " + coupon2.getType() + " type:");
			System.out.println(customerFacade.getAllPurchasedCouponsByType(CouponType.RESTURANT));
			//Sleep until a new coupon will be created by another company Thread
			Thread.sleep(20000);
			System.out.println(this.userName + " Finished");
		} catch (CouponSystemException | InterruptedException e) {
			e.printStackTrace();
		}		
	}

}
