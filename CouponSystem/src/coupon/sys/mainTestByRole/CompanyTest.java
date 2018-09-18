package coupon.sys.mainTestByRole;

import java.util.Calendar;
import java.util.GregorianCalendar;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.CompanyFacade;
import dbCreation.CleanDbTables;

public class CompanyTest {

	public static void main(String[] args) {
		
		CleanDbTables.cleanDbTable();
		
		try {
			CouponSystem couponSystem = CouponSystem.getInstance();
		System.out.println("Create Companies by the Admin before the Company methods are checked");
		System.out.println("****************************************************************************");
		AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", "Admin");
		Company companyAdama = new Company( "ADAMA", "123456", "Adama@adama.com");
		Company companyEgged = new Company("Egged", "33454", "Egged@gamil.com");
		Company companySegev = new Company( "Segev", "56711", "Segev@gamil.com");
		adminFacade.createCompany(companyAdama);
		adminFacade.createCompany(companyEgged);
		adminFacade.createCompany(companySegev);
		System.out.println("The following compnaies were created:\n" + adminFacade.getAllCompanies());
		
		
		System.out.println("\n***************************Test Company Methods*****************************");
		System.out.println("****************************************************************************\n");
		//Create Companies
		System.out.println("Creating Coupons: (Methods: createCoupon and getCoupons");
		System.out.println("===============================================================");
		CompanyFacade companyFacade = (CompanyFacade) couponSystem.login("ADAMA", "123456", "Company");
		Coupon couponGreg = new Coupon("Greg 30% off", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.AUGUST, 1).getTime(), 75,
				CouponType.RESTURANT, "For all users", 25, "No Image");
		Coupon couponLandver1P1 = new Coupon("Landver 1+1", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.SEPTEMBER, 30).getTime(), 150,
				CouponType.RESTURANT, "For all users", 70, "No Image");
		Coupon couponLandver = new Coupon("Landver 24% off on breakfast", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JUNE, 5).getTime(), 150,
				CouponType.RESTURANT, "For all users", 70, "No Image");
		Coupon couponCoffeeGoo = new Coupon("Coffe Goo - Free 1 + 1", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JUNE, 8).getTime(), 150,
				CouponType.RESTURANT, "For all users", 70, "No Image");
		Coupon couponOvens = new Coupon("25% off on all Ovens", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JULY, 31).getTime(), 150,
				CouponType.ELECTRICITY, "For all users", 0, "No Image");			
		Coupon couponAllProducts = new Coupon("10% off on all products", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JULY, 27).getTime(), 1000,
				CouponType.ELECTRICITY, "For all Empolyees", 200, "No Image");
		companyFacade.createCoupon(couponGreg);
		companyFacade.createCoupon(couponLandver1P1);
		companyFacade.createCoupon(couponLandver);
		companyFacade.createCoupon(couponCoffeeGoo);
		companyFacade.createCoupon(couponOvens);
		companyFacade.createCoupon(couponAllProducts);
		System.out.println("The following coupons were created:\n" + companyFacade.getAllCoupons());
		
		
		System.out.println("\nget Coupons: (Methods: getCouponsByType and getCoupon");
		System.out.println("===============================================================");
		System.out.println(companyFacade.getCouponByType(CouponType.ELECTRICITY));
		System.out.println(companyFacade.getCouponByType(CouponType.RESTURANT));
		
		//Update Coupons
		Coupon couponCoffeeGoo_Updated = new Coupon("Coffe Goo - Free 1 + 1", new GregorianCalendar(2018, Calendar.SEPTEMBER, 25).getTime(), new GregorianCalendar(2018, Calendar.DECEMBER, 8).getTime(), 70,
				CouponType.RESTURANT, "For all users", 60, "No Image");
		System.out.println("\nUpadte Coupon: (Methods: updateCoupon and getCoupon");
		System.out.println("===============================================================");
		System.out.println("The coupon before update process");
		System.out.println("--------------------------------------");
		System.out.println(companyFacade.getCoupon(couponCoffeeGoo.getId()));
		companyFacade.updateCoupon(couponCoffeeGoo_Updated);
		System.out.println("\nThe coupon after update process");
		System.out.println("--------------------------------------");
		System.out.println(companyFacade.getCoupon(couponCoffeeGoo.getId()));
		
		
		//Remove coupon
		System.out.println("\nRemove coupon: (Methods: removeCoupon and getAllCoupons");
		System.out.println("===============================================================");
		System.out.println("The coupons before delete process");
		System.out.println("--------------------------------------");
		System.out.println(companyFacade.getAllCoupons());
		companyFacade.removeCoupon(couponLandver);
		System.out.println("\nThe copuns after delete process");
		System.out.println("--------------------------------------");
		System.out.println(companyFacade.getAllCoupons());	
		
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}	

	}

}
