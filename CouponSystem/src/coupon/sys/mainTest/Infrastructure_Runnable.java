package coupon.sys.mainTest;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.dao.db.CompanyDaoDB;
import coupon.sys.core.dao.db.CustomerDaoDB;
import coupon.sys.core.exceptions.CouponSystemException;

public class Infrastructure_Runnable implements Runnable {

	public Infrastructure_Runnable() {
	}

	@Override
	public void run() {
		CompanyDao companyDaoDB = new CompanyDaoDB(); 
		CustomerDao customerDaoDB = new CustomerDaoDB(); 		
		try {
			//Create Companies and Customers
			System.out.println("Create Companies");
			Company company1 = new Company( "ISCS", "123456", "Iscs@gamil.com");
			Company company2 = new Company("EWAVE", "123456", "Ewave@gamil.com");
			Company company3 = new Company( "Requshet", "123456", "Requshet@gamil.com");
			Company company4 = new Company( "Elbit", "123456", "Elbit@gamil.com");
			Company company5 = new Company( "Landver", "123456", "Landver@gamil.com");
			Company company6 = new Company( "Holmes Place", "123456", "HolmesPlace@gamil.com");
			Company company7 = new Company( "GymMaster", "123456", "GymMaster@gamil.com");
			companyDaoDB.createCompany(company1);
			companyDaoDB.createCompany(company2);
			companyDaoDB.createCompany(company3);
			companyDaoDB.createCompany(company4);
			companyDaoDB.createCompany(company5);
			companyDaoDB.createCompany(company6);
			companyDaoDB.createCompany(company7);
			
			System.out.println("Create Customers");
			Customer customer1 = new Customer("Yehuda Hizmi", "123456");
			Customer customer2 = new Customer("Orit Benita", "123456");
			Customer customer3 = new Customer("Moti Hrel", "123456");
			Customer customer4 = new Customer("Eldad Shuster", "123456");
			Customer customer5 = new Customer("Neria Hill", "123456");
			Customer customer6 = new Customer("Oren Dahan", "123456");
			Customer customer7 = new Customer("Liron Levi", "123456");
			customerDaoDB.createCustomer(customer1);				
			customerDaoDB.createCustomer(customer2);				
			customerDaoDB.createCustomer(customer3);				
			customerDaoDB.createCustomer(customer4);				
			customerDaoDB.createCustomer(customer5);				
			customerDaoDB.createCustomer(customer6);							
			customerDaoDB.createCustomer(customer7);				
					
//			System.out.println("Create Coupons");
//			Coupon coupon1 = new Coupon("Greg 30% off", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JUNE, 1).getTime(), 75,
//					CouponType.RESTURANT, "For all users", 25, "No Image");
//			Coupon coupon2 = new Coupon("Requshet 10% off", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.DECEMBER, 1).getTime(), 150,
//					CouponType.CAMPING, "For all users", 10, "No Image");
//			Coupon coupon3 = new Coupon("Alam Duty Free - Cmputer mouse", new GregorianCalendar(2018, Calendar.OCTOBER, 25).getTime(), new GregorianCalendar(2018, Calendar.NOVEMBER, 1).getTime(), 56,
//					CouponType.ELECTRICITY, "For all users", 130, "No Image");
//			Coupon coupon4 = new Coupon("Holms Place 30% off", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2021, Calendar.JUNE, 1).getTime(), 360,
//					CouponType.HEALTH, "For all users", 50, "No Image");
//			Coupon coupon5 = new Coupon("Landver 1+1", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2018, Calendar.JUNE, 1).getTime(), 150,
//					CouponType.RESTURANT, "For all users", 70, "No Image");
//			Coupon coupon6 = new Coupon("Milk 1+1", new GregorianCalendar(2018, Calendar.APRIL, 25).getTime(), new GregorianCalendar(2019, Calendar.JUNE, 1).getTime(), 4,
//					CouponType.FOOD, "For all users", 500, "No Image");
//			if(cuoponDaoDB.checkDbExistence(coupon1)) {
//				cuoponDaoDB.removeCoupon(cuoponDaoDB.getCoupon(coupon1.getTitle()));
//			}
//			cuoponDaoDB.createCoupon(coupon1);				
//			if(cuoponDaoDB.checkDbExistence(coupon2)) {
//				cuoponDaoDB.removeCoupon(cuoponDaoDB.getCoupon(coupon2.getTitle()));
//			}
//			cuoponDaoDB.createCoupon(coupon2);				
//			if(cuoponDaoDB.checkDbExistence(coupon3)) {
//				cuoponDaoDB.removeCoupon(cuoponDaoDB.getCoupon(coupon3.getTitle()));
//			}
//			cuoponDaoDB.createCoupon(coupon3);				
//			if(cuoponDaoDB.checkDbExistence(coupon4)) {
//				cuoponDaoDB.removeCoupon(cuoponDaoDB.getCoupon(coupon4.getTitle()));
//			}
//			cuoponDaoDB.createCoupon(coupon4);				
//			if(cuoponDaoDB.checkDbExistence(coupon5)) {
//				cuoponDaoDB.removeCoupon(cuoponDaoDB.getCoupon(coupon5.getTitle()));
//			}
//			cuoponDaoDB.createCoupon(coupon5);				
//			if(cuoponDaoDB.checkDbExistence(coupon6)) {
//				cuoponDaoDB.removeCoupon(cuoponDaoDB.getCoupon(coupon6.getTitle()));
//			}		
//			cuoponDaoDB.createCoupon(coupon6);				
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
}
