package coupon.sys.core.facade;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * This class implements all the business logic associated with the administrator
 * It has two attributes: one of type CompanyDao and the other of type customerDaoDB. 
 * @author Yehuda.Hizmi
 *
 */
public class AdminFacade implements ClientFacade {

	private CompanyDao companyDaoDB;
	private CustomerDao customerDaoDB;
//	private ClientFacade clientFacade;

	/**
	 * The constructor initialize the companyDaoDB and customerDaoDB attributes
	 * @param companyDaoDB - Object of type CompanyDao
	 * @param customerDaoDB - Object of type customerDaoDB
	 */
	public AdminFacade(CompanyDao companyDaoDB, CustomerDao customerDaoDB) {
		this.companyDaoDB = companyDaoDB;
		this.customerDaoDB = customerDaoDB;
	}

	/*
	@Override
	public ClientFacade login(String name, String password, String clientType) throws CouponSystemException {
			try {
				if(clientType.equals("Admin") && name.equals("admin") && password.equals("1234")){			
					clientFacade = new AdminFacade(this.companyDaoDB, this.customerDaoDB);
				} else {				
					throw new CouponSystemException("Wrong user name or password please try again");
				}
			} catch (CouponSystemException e) {
				throw new CouponSystemException("Wrong user name or password please try again",e);
			}			
		return clientFacade;
	}
	*/
	
	/**
	 * This method activate the createCompany at the companyDaoDB class.
	 * It gets a company object to create as a parameter.
	 * Since it is not possible to create two companies with the same name.
	 * The method: 
	 * 1.check if a company with the same name already exists in the system in case there is raise exception otherwise insert it
	 * @param company - the company to create 
	 * @throws CouponSystemException -  throws a general exception with a message "Company name: " + company.getCompName() + ", already exists in the system"
	 */
	public void createCompany(Company company) throws CouponSystemException {
		//Check if the company name already exists in the DB
		if (!companyDaoDB.checkDbExistence(company)) {
			//If the name doesn't exist insert it
			companyDaoDB.createCompany(company);
		} else {
			//If the name exist throws exception
			throw new CouponSystemException("Company name: " + company.getCompName() + ", already exists in the system");
		}
	}

	/**
	 * This method activate the removerCompany at the companyDaoDB class.
	 * It gets a company object to delete as a parameter.
	 * @param company - The company to delete 
	 * @throws CouponSystemException - throws a general exception with a message "Company [Company name: " + company.getCompName() + "] doesn't exists in the system"
	 */
	public void removeCompany(Company company) throws CouponSystemException {
		//Get the company from the DB  
		Company dbCompany = companyDaoDB.getCompany(company.getCompName());
		if (dbCompany != null) {			
			companyDaoDB.removeCompany(dbCompany);
		} else {
			//If no company returned raise an exception
			throw new CouponSystemException("Company [Company name: " + company.getCompName() + "] doesn't exists in the system");
		}
	}

	/**
	 * This method activate the updateCompany method at the companyDaoDB class.
	 * According to the business logic only the password and the email can be updated.
	 * @param company - The company to be updated
	 * @throws CouponSystemException - throws a general exception with a message "Company [Company name: " + company.getCompName() + "] doesn't exists in the system"
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		//Company updatedCompany = companyDaoDB.getCompany (company.getId());
		/*
		 * This method updates only 2 parameters (the password and the email) 
		 * 1. First we retrieve the company from DB 
		 * 2. Update the retrieved instance - only the relevant parameters 
		 * 3. Activate updateCompany method with the updatedCompany as parameter
		 */
		Company updatedCompany = companyDaoDB.getCompany(company.getCompName());
		if (updatedCompany != null) {
			updatedCompany.setPassword(company.getPassword());
			updatedCompany.setEmail(company.getEmail());
			companyDaoDB.updateCompany(updatedCompany);
		} else {
			throw new CouponSystemException("Company [Company name: " + company.getCompName() + "] doesn't exists in the system");
		}
	}

	/**
	 * This method activate the getCompany method at companyDaoDB class.
	 * It return a company object based on the id it gets as a parameter  
	 * @param comp_id - The company id to return 
	 * @return - Return a company object 
	 * @throws CouponSystemException - throws a general exception with a message "Company [Company id: " + comp_id + "] doesn't exists in the system"
	 */
	public Company getCompany(long comp_id) throws CouponSystemException {
		//Get the company from the DB  
		Company company = companyDaoDB.getCompany(comp_id);
		//If a company returned raise return it
		if (company != null) {
			return company;
		} else {
			//If no company returned raise an exception
			throw new CouponSystemException("Company [Company id: " + comp_id + "] doesn't exists in the system");
		}
	}

	/**
	 * This method activate the getAllCompanies method at companyDaoDB class.
	 * @return - Return a collection of companies
	 * @throws CouponSystemException - throws a general exception with a message "There aren't any listed companies in the system"
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		//Retrieve all companies from the DB as a collection  
		Collection<Company> companies = companyDaoDB.getAllCompanies();
		//In case collection was return return it
		if (companies != null) {
			return companies;
		} else {
			//If no company returned raise an exception
			throw new CouponSystemException("There aren't any listed companies in the system");
		}
	}

	
	/**
	 * This method activates the getCompanyIfFromName method at the companyDaoDB class.  
	 * @param CompanyName = The company's name it id we asks
	 * @return - The company's id
	 * @throws CouponSystemException - throws a general exception with a message "Unable to retrieve company's id from company name [Company name: " + CompanyName +"]"
	 */
	public long getCompanyIfFromName(String CompanyName) throws CouponSystemException  {
		long companyId = companyDaoDB.getCompanyIdFromName(CompanyName);
		if(Long.valueOf(companyId) != null) {
			return companyId;
		} else {
			throw new CouponSystemException("Unable to retrieve company's id from company name [Company name: " + CompanyName +"]");
		}
	}
	
	/**
	 * This method activate the createCustomer at the customerDaoDB class.
	 * It gets a customer object to create as a parameter.
	 * Since it is not possible to create two customers with the same name.
	 * The method: 
	 * 1.check if a customer with the same name already exists in the system in case there is raise exception otherwise insert it
	 * @param customer - the customer to create
	 * @throws CouponSystemException - throws a general exception with a message "Customer [Customer name: " + customer.getCustName() + "] already exists in the system"
	 */
	public void createCustomer(Customer customer) throws CouponSystemException {
		//Check if the customer name already exists in the DB
		if (!customerDaoDB.checkDbExistence(customer)) {
			//If the name doesn't exist insert it
			customerDaoDB.createCustomer(customer);
		} else {
			//If the name exist throws exception
			throw new CouponSystemException("Customer [Customer name: " + customer.getCustName() + "] already exists in the system");
		}
	}


	/**
	 * This method activate the removerCustomer at the customerDaoDB class.
	 * It gets a customer object to delete as a parameter.
	 * @param customer - The customer to delete
	 * @throws CouponSystemException - throws a general exception with a message "Customer [Customer name: " + customer.getCustName() + "] doesn't exists in the system"
	 */
	// When deleting a customer the method delete all the relevant tables (Customer_Coupon, Customer)
	public void removeCustomer(Customer customer) throws CouponSystemException {
		//Get the company from the DB  
		Customer dbCustomer = customerDaoDB.getCustomer(customer.getCustName());
		if (dbCustomer != null) {
			customerDaoDB.removeCustomer(dbCustomer);
		} else {
			//If no customer returned raise an exception
			throw new CouponSystemException("Customer [Customer name: " + customer.getCustName() + "] doesn't exists in the system");
		}
	}


	/**
	 * This method activate the updateCustomer method at the customerDaoDB class.
	 * According to the business logic only the password can be updated.
	 * @param customer - The customer to be updated
	 * @throws CouponSystemException - throws a general exception with a message "Customer [Customer name: " + customer.getCustName() + "] doesn't exists in the system"
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		//Customer updatedCustomer = customerDaoDB.getCustomer(customer.getId());
		/*
		 * This method updates all parameters except the customer name 
		 * 1. First we retrieve the customer from DB 
		 * 2. Update the retrieved instance - only the relevant parameters 
		 * 3. Activate updateCustomer method with the updatedCustomer as parameter
		 */
		Customer updatedCustomer = customerDaoDB.getCustomer(customer.getCustName());
		if(updatedCustomer != null) {			
			updatedCustomer.setPassword(customer.getPassword());
			customerDaoDB.updateCustomer(updatedCustomer);
		} else {
			throw new CouponSystemException("Customer [Customer name: " + customer.getCustName() + "] doesn't exists in the system");
		}
	}


	/**
	 * This method activate the getCustomer method at customerDaoDB class.
	 * It return a customer object based on the id it gets as a parameter  
	 * @param cust_id - The customer id to return 
	 * @return - Return a customer object 
	 * @throws CouponSystemException - throws a general exception with a message "Customer [Customer id: " + cust_id + "] doesn't exists in the system"
	 */
	public Customer getCustomer(long cust_id) throws CouponSystemException {
		//Retrieve the customers from the DB
		Customer customer = customerDaoDB.getCustomer(cust_id);
		if (customer != null) {
			return customer;
		} else {
			//in case it null throws exception
			throw new CouponSystemException("Customer [Customer id: " + cust_id + "] doesn't exists in the system");
		}
	}


	/**
	 * This method activate the getAllCustomers method at customerDaoDB class.
	 * @return - Return a collection of customers
	 * @throws CouponSystemException - throws a general exception with a message "There aren't any listed customers in the system"
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		//Retrieve the customers from the DB as a collection
		Collection<Customer> customers = customerDaoDB.getAllCustomers();
		if (customers != null) {
			return customers;
		} else {
			//in case it null throws exception
			throw new CouponSystemException("There aren't any listed customers in the system");
		}
	}

}
