package coupon.sys.mainTest;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;

public class Admin_Runnable implements Runnable {

	private CouponSystem couponSystem;
	private String userName;
	private String password;
	
	public Admin_Runnable(String userName, String password) throws CouponSystemException {
		this.couponSystem = CouponSystem.getInstance();
		this.userName = userName;
		this.password = password;
	}
	
	@Override
	public void run() {
		try {
			AdminFacade adminFacade = (AdminFacade) couponSystem.login(this.userName, this.password, "Admin");
			System.out.println(this.userName + " is logged in");
			System.out.println("admin creates new Companies");
			Company company1 = new Company( "ADAMA", "123456", "Adama@adama.com");
			Company company2 = new Company("Egged", "33454", "Egged@gamil.com");
			Company company3 = new Company( "Segev", "56711", "Segev@gamil.com");
			Company company3_updated = new Company( "Segev", "5671188", "SegevExpress@gamil.com");
			adminFacade.createCompany(company1);
			System.out.println(this.userName + " created a company: " + company1.getCompName());
			Thread.sleep(5000);
			adminFacade.createCompany(company2);
			System.out.println(this.userName + " created a company: " + company2.getCompName());
			Thread.sleep(5000);
			adminFacade.createCompany(company3);
			System.out.println(this.userName + " created a company: " + company3.getCompName());
			Thread.sleep(5000);

			System.out.println("admin creates new Customers");
			Customer customer1 = new Customer("Assaf Grabli", "56331");
			Customer customer2 = new Customer("Adir Miler", "adir113");
			Customer customer3 = new Customer("Ben Eliyo", "el114!");
			Customer customer3_updated = new Customer("Ben Eliyo", "el11496!");		
			adminFacade.createCustomer(customer1);
			System.out.println(this.userName + " created a customer: " + customer1.getCustName());
			Thread.sleep(5000);
			adminFacade.createCustomer(customer2);
			System.out.println(this.userName + " created a customer: " + customer2.getCustName());
			Thread.sleep(5000);
			adminFacade.createCustomer(customer3);
			System.out.println(this.userName + " created a customer: " + customer3.getCustName());
			Thread.sleep(1000);
			//Update company
			System.out.println("All companies before update process");
			System.out.println(this.userName + " - " + adminFacade.getAllCompanies());
			System.out.println(this.userName + " update company: " + company3_updated.getCompName());
			adminFacade.updateCompany(company3_updated);
			System.out.println("All companies after update process");
			System.out.println(this.userName + " - " + adminFacade.getAllCompanies());
			
			//Update customer
			System.out.println("All customers before update process");
			System.out.println(this.userName + " - " + adminFacade.getAllCustomers());
			System.out.println(this.userName + " update customer: " + customer3_updated.getCustName());
			adminFacade.updateCustomer(customer3_updated);
			System.out.println("All customers after update process");
			System.out.println(this.userName + " - " + adminFacade.getAllCustomers());
			Thread.sleep(60000);
			System.out.println("All customers before delete process");
			System.out.println(this.userName + " - " + adminFacade.getAllCustomers());
			//Delete Customer
			System.out.println(this.userName + " delete customer: " + customer2.getCustName());
			adminFacade.removeCustomer(customer2);
			System.out.println("All customers after delete process");
			System.out.println(this.userName + " - " + adminFacade.getAllCustomers());			
			Thread.sleep(10000);
			
			System.out.println("All companies before delete process");
			System.out.println(this.userName + " - " + adminFacade.getAllCompanies());
			Company companyToDel = new Company( "Landver", "123456", "Landver@gamil.com");
			System.out.println(this.userName + " delete company: " + companyToDel.getCompName());
			adminFacade.removeCompany(companyToDel);
			System.out.println("All companies after delete process");
			System.out.println(this.userName + " - " + adminFacade.getAllCompanies());

			
			Thread.sleep(10000);
			System.out.println("Admin tries to create a company with the same name");
			Company newCompany = new Company("Egged", "112214", "EggedNew@gamil.com");
			adminFacade.createCompany(newCompany);
						
			System.out.println("admin Finished");
			
		} catch (CouponSystemException | InterruptedException e) {
			e.printStackTrace();
		}		
	}

}
