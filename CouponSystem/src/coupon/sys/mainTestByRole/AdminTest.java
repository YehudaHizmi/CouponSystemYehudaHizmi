package coupon.sys.mainTestByRole;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.coupon.system.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import dbCreation.CleanDbTables;

public class AdminTest {

	public static void main(String[] args) {
		
		CleanDbTables.cleanDbTable();
		try {
			CouponSystem couponSystem = CouponSystem.getInstance();
			
			System.out.println("***************************Test Admin Methods*****************************");
			System.out.println("****************************************************************************");
			AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", "Admin");
			Company companyAdama = new Company( "ADAMA", "123456", "Adama@adama.com");
			Company companyEgged = new Company("Egged", "33454", "Egged@gamil.com");
			Company companySegev = new Company( "Segev", "56711", "Segev@gamil.com");
			
			//Create Companies
			System.out.println("Creating companies: (Methods: createCompany and getAllCompanies");
			System.out.println("===============================================================");
			adminFacade.createCompany(companyAdama);
			adminFacade.createCompany(companyEgged);
			adminFacade.createCompany(companySegev);
			System.out.println("The following compnaies were created:\n" + adminFacade.getAllCompanies());
			
			//Update Company
			Company companySegev_updated = new Company( "Segev", "5671188", "SegevExpress@gamil.com");
			System.out.println("\nUpadte companies: (Methods: updateCompany and getCompany");
			System.out.println("===============================================================");
			System.out.println("The company before update process");
			System.out.println("--------------------------------------");
			System.out.println(adminFacade.getCompany(companySegev.getId()));
			adminFacade.updateCompany(companySegev_updated);
			System.out.println("\nThe company after update process");
			System.out.println("--------------------------------------");
			System.out.println(adminFacade.getCompany(companySegev.getId()));
			
			//Remove Company
			System.out.println("\nRemove companies: (Methods: removeCompany and getAllCompanies");
			System.out.println("===============================================================");
			System.out.println("The companies before delete process");
			System.out.println("--------------------------------------");
			System.out.println(adminFacade.getAllCompanies());
			adminFacade.removeCompany(companySegev);
			System.out.println("\nThe companies after delete process");
			System.out.println("--------------------------------------");
			System.out.println(adminFacade.getAllCompanies());
			
			
			System.out.println("***************************Test Customer Methods*****************************");
			System.out.println("****************************************************************************");			
			
			Customer customerAssaf = new Customer("Assaf Grabli", "56331");
			Customer customerAdir = new Customer("Adir Miler", "adir113");
			Customer customerBen = new Customer("Ben Eliyo", "el114!");
			//Customer customer3_updated = new Customer("Ben Eliyo", "el11496!");	
			
			//Create Customers
			System.out.println("Creating Customers: (Methods: createCustomer and getAllCustomers");
			System.out.println("===============================================================");
			adminFacade.createCustomer(customerAssaf);
			adminFacade.createCustomer(customerAdir);
			adminFacade.createCustomer(customerBen);
			System.out.println("The following customers where created:\n" + adminFacade.getAllCustomers());
			
			//Update Customer
			Customer customerBen_updated = new Customer("Ben Eliyo", "el11496!");
			System.out.println("\nUpadte customers: (Methods: updateCustomer and getCustomer");
			System.out.println("===============================================================");
			System.out.println("The customer before update process");
			System.out.println("--------------------------------------");
			System.out.println(adminFacade.getCustomer(customerBen.getId()));
			adminFacade.updateCustomer(customerBen_updated);
			System.out.println("\nThe customer after update process");
			System.out.println("--------------------------------------");
			System.out.println(adminFacade.getCustomer(customerBen.getId()));
			
			//Remove Company
			System.out.println("\nRemove customer: (Methods: removeCustomer and getAllCustomers");
			System.out.println("===============================================================");
			System.out.println("The customers before delete process");
			System.out.println("--------------------------------------");
			System.out.println(adminFacade.getAllCustomers());
			adminFacade.removeCustomer(customerBen);
			System.out.println("\nThe customers after delete process");
			System.out.println("--------------------------------------");
			System.out.println(adminFacade.getAllCustomers());			
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}	

	}

}
