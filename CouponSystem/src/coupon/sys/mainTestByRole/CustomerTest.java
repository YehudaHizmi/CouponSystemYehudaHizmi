package coupon.sys.mainTestByRole;

import java.util.Calendar;
import java.util.GregorianCalendar;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;
import dbCreation.CleanDbTables;

public class CustomerTest {

	public static void main(String[] args) {
		
		CleanDbTables.cleanDbTable();
		try {
			CouponSystem couponSystem = CouponSystem.getInstance();
			System.out.println("Create Companies, Customers and Coupons before the Customer methods are checked");
			System.out.println("****************************************************************************");
			AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", "Admin");
			Company companyAdama = new Company( "ADAMA", "123456", "Adama@adama.com");
			Company companyEgged = new Company("Egged", "33454", "Egged@gamil.com");
			Company companySegev = new Company( "Segev", "56711", "Segev@gamil.com");
			adminFacade.createCompany(companyAdama);
			adminFacade.createCompany(companyEgged);
			adminFacade.createCompany(companySegev);
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
			Coupon couponAllProducts = new Coupon("10% off on all products", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2019, Calendar.JULY, 27).getTime(), 1000,
					CouponType.ELECTRICITY, "For all Empolyees", 200, "No Image");
			companyFacade.createCoupon(couponGreg);
			companyFacade.createCoupon(couponLandver1P1);
			companyFacade.createCoupon(couponLandver);
			companyFacade.createCoupon(couponCoffeeGoo);
			companyFacade.createCoupon(couponOvens);
			companyFacade.createCoupon(couponAllProducts);			
			Customer customerAssaf = new Customer("Assaf Grabli", "56331");
			Customer customerAdir = new Customer("Adir Miler", "adir113");
			Customer customerBen = new Customer("Ben Eliyo", "el114!");
			adminFacade.createCustomer(customerAssaf);
			adminFacade.createCustomer(customerAdir);
			adminFacade.createCustomer(customerBen);
			System.out.println("The following customers where created:\n" + adminFacade.getAllCustomers());
			System.out.println("The following companies were created:\n" + adminFacade.getAllCompanies());
			System.out.println("The following coupons were created:\n" + companyFacade.getAllCoupons());
			
			
			//Purchase Coupon
			//This row is running only for the purpose of getting the coupon object 
			CustomerFacade customerFacade = (CustomerFacade) couponSystem.login("Assaf Grabli", "56331", "Customer");
			Coupon couponTopurchase =  companyFacade.getCoupon(couponLandver1P1.getId());
			Coupon couponTopurchase2 =  companyFacade.getCoupon(couponAllProducts.getId());
			System.out.println("\nPurchase Coupon: (Methods: purchaseCoupon and getAllPurchasedCoupons");
			System.out.println("======================================================================");
			customerFacade.purchaseCoupon(couponTopurchase);
			customerFacade.purchaseCoupon(couponTopurchase2);
			System.out.println("\nThe coupons after being purchased");
			System.out.println("--------------------------------------");
			System.out.println(customerFacade.getAllPurchasedCoupons());	
			
			//Get all coupons by types:
			System.out.println("\nCoupons of ELECTRICITY type:");
			System.out.println(customerFacade.getAllPurchasedCouponsByType(CouponType.ELECTRICITY));
			System.out.println("\nCoupons of RESTURANT type:");
			System.out.println(customerFacade.getAllPurchasedCouponsByType(CouponType.RESTURANT));
			
			//Get all coupons by price:
			System.out.println("\nCoupons up to 300 Shekels:");
			System.out.println(customerFacade.getAllPurchasedCouponsByPrice(100));
			
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}	
		

	}

}
