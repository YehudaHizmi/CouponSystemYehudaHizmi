package coupon.sys.mainTest;

//import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;

public class CouponSystemMainRun {

	public static void main(String[] args) {		
		/*
		 * The test will be according to the following steps:
		 * 1. Create Companies,customers and Coupons in order to present a live working system
		 * 2. In parallel create 3 new Threads of Admin, Company and Customers in order to present parallel scenario
		 */
		try {
			//CouponSystem couponSystem = CouponSystem.getInstance();
			Thread infraThread = new Thread(new Infrastructure_Runnable(), "MyRunnableInfrastructure");
			infraThread.start();
			//Main will sleep for few seconds in order to make sure the Infrastructure thread finishes
			System.out.println("Main in a sleep for 5 seconds until Infrastructure finishes its run ");
			System.out.println("********************************************************************");
			Thread.sleep(5000);
			System.out.println("Main continue");
			System.out.println("********************************************************************");
			//login processes
			
			System.out.println("************************************************************************");
			System.out.println("*******************Regular operations of all parties********************");
			System.out.println("************************************************************************");
			
			//Admin
			Thread admin_Operations = new Thread(new Admin_Runnable("admin", "1234"), "Admin");
			admin_Operations.start();
			System.out.println(admin_Operations.getName() + " Thread has started");
			
			//first Company
			Thread company_1_Operations = new Thread(new Company_1_Runnable("Landver", "123456"), "Landver");
			company_1_Operations.start();
			System.out.println(company_1_Operations.getName() + " Thread has started");
			//couponSystem.shutdown();
			//second Company
			Thread company_2_Operations = new Thread(new Company_2_Runnable("ISCS", "123456"), "ISCS");
			company_2_Operations.start();
			System.out.println(company_2_Operations.getName() + " Thread has started");
			
			//Sleep for 5 second until the companies create coupons that the customers can purchase
			Thread.sleep(5000);
			
			//third Company
			Thread company_3_Operations = new Thread(new Company_3_Runnable("GymMaster", "123456"), "GymMaster");
			company_3_Operations.start();
			System.out.println(company_3_Operations.getName() + " Thread has started");
						
			//Sleep for 5 second until the companies created a coupons that the customer can purchase
			Thread.sleep(5000);

			//first customer
			Thread customer_1_Operations = new Thread(new Customer_1_Runnable("Yehuda Hizmi", "123456"), "Yehuda Himi");
			customer_1_Operations.start();
			System.out.println(customer_1_Operations.getName() + " Thread has started");
			
			//second customer
			Thread customer_2_Operations = new Thread(new Customer_2_Runnable("Moti Hrel", "123456"), "Moti Hrel");
			customer_2_Operations.start();
			System.out.println(customer_2_Operations.getName() + " Thread has started");
		
			System.out.println("Check Adir data befor delete");
			Thread.sleep(30000);
			//third customer
			Thread customer_3_Operations = new Thread(new Customer_2_Runnable("Adir Miler", "adir113"), "Adir Miler");
			customer_3_Operations.start();
			System.out.println(customer_3_Operations.getName() + " Thread has started");
			
			Thread.sleep(100000);
		} catch (CouponSystemException | InterruptedException e) {
			e.printStackTrace();
		} 
	}

}
