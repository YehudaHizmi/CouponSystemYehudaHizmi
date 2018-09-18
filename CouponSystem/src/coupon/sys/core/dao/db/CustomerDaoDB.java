package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.connection.ConnectionPool;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * This class implements all the methods in CustomerDao interface.
 * It has an empty constructor and it as an attribute of ConnectionPool type
 * @author Yehuda.Hizmi
 *
 */
public class CustomerDaoDB implements CustomerDao {

	private ConnectionPool connectionPool;

	public CustomerDaoDB() {
	}

	@Override
	public boolean login(String custName, String password) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectLoginSql = "SELECT * FROM CUSTOMER WHERE CUST_NAME = '" + custName + "' AND PASSWORD = '" + password + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectLoginSql);
			if (result.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to login [Customer name: " + custName + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void createCustomer(Customer customer) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String createCustomerSql = "INSERT INTO CUSTOMER (CUST_NAME,PASSWORD) VALUES(?,?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(createCustomerSql,PreparedStatement.RETURN_GENERATED_KEYS);
			//pstmt.setLong(1, customer.getId());
			pstmt.setString(1, customer.getCustName());
			pstmt.setString(2, customer.getPassword());
			if (pstmt.executeUpdate() == 0) {// Check the number of rows inserted - in case no row inserted rise an exception
				throw new CouponSystemException("Customer [Customer name: " + customer.getCustName() +  "] was not created");
			}
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			//Update the customer object with the generated key (this is the customer's id)
			customer.setId(rs.getLong(1));
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to create a new customer [Customer name: " + customer.getCustName() + " , Customer id: " + customer.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	/*
	 * When delete a customer the delete process is as follow: 1.Customer_Coupon 2.Customer
	 */
	@Override
	public void removeCustomer(Customer customer) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		long customerId = customer.getId();// Since the id is used for several queries it been kept as a variable
		String deleteCustomerCouponSql = "DELETE FROM CUSTOMER_COUPON CC WHERE CC.CUST_ID = " + customerId;
		String deleteCustomerSql = "DELETE FROM CUSTOMER C WHERE C.ID = " + customerId;
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(deleteCustomerCouponSql);
			stmt.executeUpdate(deleteCustomerSql);
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to delete customer [Customer name: " + customer.getCustName() + " , Customer id: " + customer.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String updateCustomerSql = "UPDATE CUSTOMER SET CUST_NAME = ?, PASSWORD = ? WHERE ID = " + customer.getId();
		try {
			PreparedStatement pstmt = connection.prepareStatement(updateCustomerSql);
			pstmt.setString(1, customer.getCustName());
			pstmt.setString(2, customer.getPassword());
			if (pstmt.executeUpdate() == 0) {// Check the number of rows updated - in case no row updated rise an exception
				throw new CouponSystemException("Customer [Customer name: " + customer.getCustName() + " , Customer id: " + customer.getId() + "] was not updated");
			}
		} catch (SQLException e) {
			throw new CouponSystemException(
					"Unable to update customer's information [Customer name: " + customer.getCustName() + " , Customer id: " + customer.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Customer getCustomer(long id) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCustomerSql = "SELECT * FROM CUSTOMER WHERE ID = " + id;
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCustomerSql);
			if (!result.next()) {
				return null;
			}
			return new Customer(result.getLong(1), result.getString(2), result.getString(3));
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch customer's information [Customer id: " + id + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	@Override
	public Customer getCustomer(String name) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCustomerSql = "SELECT * FROM CUSTOMER WHERE CUST_NAME = '" + name + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCustomerSql);
			if (!result.next()) {
				return null;
			}
			return new Customer(result.getLong(1), result.getString(2), result.getString(3));
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch customer's information [Customer name: " + name + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCustomerSql = "SELECT * FROM CUSTOMER";
		Collection<Customer> customers = new HashSet<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCustomerSql);
			if (!result.next()) {
				return null;
			} else {
				do {
					customers.add(new Customer(result.getLong(1), result.getString(2), result.getString(3)));
				} while (result.next());
			}
			return customers;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch all companies information or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT CO.* FROM CUSTOMER C, CUSTOMER_COUPON CC, COUPON CO WHERE C.ID = CC.CUST_ID AND CC.COUPON_ID = CO.ID AND CC.CUST_ID = " + customer.getId();
		Collection<Coupon> couponsByCustomer = new HashSet<>();
		try {
			Statement stmt = connection.createStatement();
			// pstmt.setLong(1, customer.getId());
			ResultSet result = stmt.executeQuery(selectCouponSql);
			if (!result.next()) {
				return null;
			} else {
				do {
					couponsByCustomer.add(new Coupon(result.getLong(1), result.getString(2), result.getDate(3), result.getDate(4), result.getInt(5), CouponType.valueOf(result.getString(6)),
							result.getString(7), result.getDouble(8), result.getString(9)));
				} while (result.next());
			}
			return couponsByCustomer;
		} catch (SQLException e) {
			throw new CouponSystemException("Unbale to fetch customer's coupons [Customer name: " + customer.getCustName() + " , Customer id: " + customer.getId() + "]", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	/*********************************************************************************************************/
	/*************** The following methods were added in order to simplify the business logic ****************/
	/*********************************************************************************************************/
	// This method return true if the select return a record with the customer name otherwise false
	public boolean checkDbExistence(Customer customer) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCustomerSql = "SELECT * FROM CUSTOMER WHERE CUST_NAME = '" + customer.getCustName() + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCustomerSql);
			if (result.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to check customer existence or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	// This method return true id the customer already purchased the coupon
	public boolean hasAlreadyPurchased(long cust_id, long coupon_id) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCustomerSql = "SELECT * FROM CUSTOMER_COUPON WHERE CUST_ID = " + cust_id + " AND COUPON_ID = " + coupon_id ;
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCustomerSql);
			if (result.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to check customer-coupon existence or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	// This method insert a new records into the Customer Coupon table
		public void createCustomerCouponRecord(long cust_id, long coupon_id) throws CouponSystemException {
			connectionPool = ConnectionPool.getInstance();
			Connection connection = connectionPool.getConnection();
			String createCustomerCouponSql = "INSERT INTO CUSTOMER_COUPON (CUST_ID,COUPON_ID) VALUES (" + cust_id + " , " + coupon_id + ")";
			try {
				PreparedStatement pstmt = connection.prepareStatement(createCustomerCouponSql);
				if (pstmt.executeUpdate() == 0) {// Check the number of rows inserted - in case no row inserted rise an exception
					throw new CouponSystemException("Customer-Coupon [Customer id: " + cust_id + " , Coupon id: " + coupon_id + "] was not created");
				}
			} catch (SQLException e) {
				throw new CouponSystemException("Unable to add a new customer-coupon record or get a new DB connection", e);
			} finally {
				connectionPool.returnConnection(connection);
			}
		}
		
		public Collection<Coupon> getCouponsByType(Customer customer, CouponType couponType) throws CouponSystemException {
			connectionPool = ConnectionPool.getInstance();
			Connection connection = connectionPool.getConnection();
			String selectCouponSql = "SELECT CO.* FROM CUSTOMER_COUPON CC, COUPON CO WHERE CC.COUPON_ID = CO.ID AND CC.CUST_ID = " + customer.getId() + " AND CO.TYPE = '" + couponType.name() + "'";
			Collection<Coupon> couponsByType = new HashSet<>();
			try {
				Statement stmt = connection.createStatement();
				// pstmt.setLong(1, customer.getId());
				ResultSet result = stmt.executeQuery(selectCouponSql);
				if (!result.next()) {
					return null;
				} else {
					do {
						couponsByType.add(new Coupon(result.getLong(1), result.getString(2), result.getDate(3), result.getDate(4), result.getInt(5), CouponType.valueOf(result.getString(6)),
								result.getString(7), result.getDouble(8), result.getString(9)));
					} while (result.next());
				}
				return couponsByType;
			} catch (SQLException e) {
				throw new CouponSystemException("Unbale to fetch customer's coupons by type [Customer name: " + customer.getCustName() + " , Customer id: " + customer.getId() + "]", e);
			} finally {
				connectionPool.returnConnection(connection);
			}
		}
		
		
		public Collection<Coupon> getCouponsUpToPrice(Customer customer, double upToPrice) throws CouponSystemException {
			connectionPool = ConnectionPool.getInstance();
			Connection connection = connectionPool.getConnection();
			String selectCouponSql = "SELECT CO.* FROM CUSTOMER_COUPON CC, COUPON CO WHERE CC.COUPON_ID = CO.ID AND CC.CUST_ID = " + customer.getId() + " AND CO.PRICE <= " + upToPrice;
			Collection<Coupon> couponsByPrice = new HashSet<>();
			try {
				Statement stmt = connection.createStatement();
				// pstmt.setLong(1, customer.getId());
				ResultSet result = stmt.executeQuery(selectCouponSql);
				if (!result.next()) {
					return null;
				} else {
					do {
						couponsByPrice.add(new Coupon(result.getLong(1), result.getString(2), result.getDate(3), result.getDate(4), result.getInt(5), CouponType.valueOf(result.getString(6)),
								result.getString(7), result.getDouble(8), result.getString(9)));
					} while (result.next());
				}
				return couponsByPrice;
			} catch (SQLException e) {
				throw new CouponSystemException("Unbale to fetch customer's coupons by by price [Customer name: " + customer.getCustName() + " , Customer id: " + customer.getId() + "]", e);
			} finally {
				connectionPool.returnConnection(connection);
			}
		}
}
