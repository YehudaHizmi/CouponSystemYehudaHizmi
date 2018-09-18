package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.connection.ConnectionPool;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * This class implements all the methods in CompanyDao interface.
 * It has an empty constructor and it as an attribute of ConnectionPool type
 * @author Yehuda.Hizmi
 *
 */
public class CompanyDaoDB implements CompanyDao {

	private ConnectionPool connectionPool;

	public CompanyDaoDB() {
	}

	@Override
	public boolean login(String compName, String password) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectLoginSql = "SELECT * FROM COMPANY WHERE COMP_NAME = '" + compName + "' AND PASSWORD = '" + password + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectLoginSql);
			if (result.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to login [Company name: " + compName + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	@Override
	public void createCompany(Company company) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String createCompanySql = "INSERT INTO COMPANY (COMP_NAME,PASSWORD,EMAIL) VALUES(?,?,?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(createCompanySql, PreparedStatement.RETURN_GENERATED_KEYS);
			//pstmt.setLong(1, company.getId());
			pstmt.setString(1, company.getCompName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			if (pstmt.executeUpdate() == 0) {// Check the number of rows inserted - in case no row inserted rise an exception
				throw new CouponSystemException("Company [Company name: " + company.getCompName() + "] was not created");
			}
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			//Update the company object with the generated key (this is the compnay's id)
			company.setId(rs.getLong(1));
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to create a new company [Company name: " + company.getCompName() + " , Company id: " + company.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	/*
	 * When delete a company the delete process is as follow: 
	 * 1.Customer_Coupon 
	 * 2.Coupon 
	 * 3.Company_Coupon 
	 * 4.Company
	 */
	@Override
	public void removeCompany(Company company) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		long companyId = company.getId();// Since the id is used for several queries it been kept as a variable
		String deleteCustomerCouponSql = "DELETE FROM CUSTOMER_COUPON CC "  
										  +	"WHERE EXISTS " 
										  + "( "
										  + "SELECT 1 " 
										  + "FROM COMPANY C, COMPANY_COUPON CCO, COUPON CO "
										  + "WHERE C.ID = CCO.COMP_ID AND CCO.COUPON_ID = CO.ID AND CO.ID = CC.COUPON_ID AND C.ID = " + companyId 
										  + ")";
		String deleteCouponSql = "DELETE FROM COUPON CO " 
										  + "WHERE EXISTS "
										  + "(SELECT 1 " 
										  + "FROM COMPANY C, COMPANY_COUPON CC " 
										  + "WHERE C.ID = CC.COMP_ID AND CC.COUPON_ID = CO.ID AND C.ID = " + companyId 
										  + ")";
		String deleteCompanyCouponSql = "DELETE FROM COMPANY_COUPON CC WHERE CC.COMP_ID = " + companyId;
		String deleteCompanySql = "DELETE FROM COMPANY C WHERE C.ID = " + companyId;
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(deleteCustomerCouponSql);
			stmt.executeUpdate(deleteCouponSql);
			stmt.executeUpdate(deleteCompanyCouponSql);
			stmt.executeUpdate(deleteCompanySql);
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to delete company [Company name: " + company.getCompName() + " , Company id: " + company.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	/*
	 * According to the Facade all information can be updated besides the company name
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String updateCompanySql = "UPDATE COMPANY SET COMP_NAME = ?, PASSWORD = ?, EMAIL = ? WHERE ID = " + company.getId();
		try {
			PreparedStatement pstmt = connection.prepareStatement(updateCompanySql);
			pstmt.setString(1, company.getCompName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			if (pstmt.executeUpdate() == 0) { // Check the number of rows updated - in case no row updated rise an exception
				throw new CouponSystemException("Company [Company name: " + company.getCompName() + " , Company id: " + company.getId() + "] was not updated");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to update company's information [Company name: " + company.getCompName() + " , Company id: " + company.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Company getCompany(long id) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCompanySql = "SELECT * FROM COMPANY WHERE ID = " + id;
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCompanySql);
			if (!result.next()) {
				return null;
			}
			return new Company(result.getLong(1), result.getString(2), result.getString(3), result.getString(4));
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch company's information [Comapny id: " + id + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	@Override
	public Company getCompany(String name) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCompanySql = "SELECT * FROM COMPANY WHERE COMP_NAME = '" + name + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCompanySql);
			if (!result.next()) {
				return null;
			}
			return new Company(result.getLong(1), result.getString(2), result.getString(3), result.getString(4));
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch company's information [Comapny name: " + name + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCompanySql = "SELECT * FROM COMPANY";
		Collection<Company> companies = new HashSet<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCompanySql);
			if (!result.next()) {
				return null;
			} else {
				do {
					companies.add(new Company(result.getLong(1), result.getString(2), result.getString(3), result.getString(4)));
				} while (result.next());
			}
			return companies;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch all companies information or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Coupon> getCoupons(Company company) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		//String selectCouponSql = "SELECT CO.* FROM COMPANY C, COMPANY_COUPON CC, COUPON CO WHERE C.ID = CC.COMP_ID AND CC.COUPON_ID = CO.ID AND CC.COMP_ID = " + company.getId();
		String selectCouponSql = "SELECT CO.* FROM COMPANY_COUPON CC, COUPON CO WHERE CC.COUPON_ID = CO.ID AND CC.COMP_ID = " + company.getId();
		Collection<Coupon> couponsByCompany = new HashSet<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCouponSql);
			if (!result.next()) {
				return null;
			} else {
				do {
					couponsByCompany.add(new Coupon(result.getLong(1), result.getString(2), result.getDate(3), result.getDate(4), result.getInt(5), CouponType.valueOf(result.getString(6)),
							result.getString(7), result.getDouble(8), result.getString(9)));
				} while (result.next());
			}
			return couponsByCompany;
		} catch (SQLException e) {
			throw new CouponSystemException("Unbale to fetch company's coupons [Company name: " + company.getCompName() + " , Company id: " + company.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	/*********************************************************************************************************/
	/*************** The following methods were added in order to simplify the business logic ****************/
	/*********************************************************************************************************/
	// This method return true if the select return a record with the company name otherwise false
	public boolean checkDbExistence(Company company) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCompanySql = "SELECT * FROM COMPANY WHERE COMP_NAME = '" + company.getCompName() + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCompanySql);
			if (result.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to check company existence [Company name: " + company.getCompName() + " , Company id: " + company.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	// This method return true if there is a record with the company id and coupon id in company_coupon table
	public boolean isCopuonBelongsCompany(long comp_id, long coupon_id) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCompanySql = "SELECT * FROM COMPANY_COUPON WHERE COMP_ID = " + comp_id + " AND COUPON_ID = " + coupon_id;
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCompanySql);
			if (result.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to check company coupon linkage [Company id: " + comp_id + " , Coupon id: " + coupon_id + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	public void createCompanyCouponRecord(long comp_id, long coupon_id) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String createCompanyCouponSql = "INSERT INTO COMPANY_COUPON (COMP_ID,COUPON_ID) VALUES (" + comp_id + " , " + coupon_id + ")";
		try {
			PreparedStatement pstmt = connection.prepareStatement(createCompanyCouponSql);
			if (pstmt.executeUpdate() == 0) {// Check the number of rows inserted - in case no row inserted rise an exception
				throw new CouponSystemException("Company-Coupon [Company id: " + comp_id + " , Coupon id: " + coupon_id + "] was not created");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to add a new company-coupon record or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	
	public Collection<Coupon> getCouponsByType(Company company, CouponType couponType) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT CO.* FROM COMPANY_COUPON CC, COUPON CO WHERE CC.COUPON_ID = CO.ID AND CC.COMP_ID = " + company.getId() + " AND CO.TYPE = '" + couponType.name() + "'";
		Collection<Coupon> couponsByType = new HashSet<>();
		try {
			Statement stmt = connection.createStatement();
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
			throw new CouponSystemException("Unbale to fetch company's coupons by type [Company name: " + company.getCompName() + " , Company id: " + company.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	public Collection<Coupon> getCouponsUpToPrice(Company company, double upToPrice) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT CO.* FROM COMPANY_COUPON CC, COUPON CO WHERE CC.COUPON_ID = CO.ID AND CC.COMP_ID = " + company.getId() + " AND CO.PRICE <= " + upToPrice;
		Collection<Coupon> couponsByPrice = new HashSet<>();
		try {
			Statement stmt = connection.createStatement();
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
			throw new CouponSystemException("Unbale to fetch company's coupons by price [Company name: " + company.getCompName() + " , Company id: " + company.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	public Collection<Coupon> getCouponsUpToDate(Company company, java.util.Date upToDate) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT CO.* FROM COMPANY_COUPON CC, COUPON CO WHERE CC.COUPON_ID = CO.ID AND CC.COMP_ID = " + company.getId() + " AND END_DATE <= '" + (new java.sql.Date(upToDate.getTime())) + "'";
		Collection<Coupon> couponsByDate = new HashSet<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCouponSql);
			if (!result.next()) {
				return null;
			} else {
				do {
					couponsByDate.add(new Coupon(result.getLong(1), result.getString(2), result.getDate(3), result.getDate(4), result.getInt(5), CouponType.valueOf(result.getString(6)),
							result.getString(7), result.getDouble(8), result.getString(9)));
				} while (result.next());
			}
			return couponsByDate;
		} catch (SQLException e) {
			throw new CouponSystemException("Unbale to fetch company's coupons by date  [Company name: " + company.getCompName() + " , Company id: " + company.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public long getCompanyIdFromName(String name) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCompanySql = "SELECT * FROM COMPANY WHERE COMP_NAME = '" + name + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCompanySql);	
			if(!result.next()) {
				return -1;
			}
			return result.getLong(1);
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch company's id [Comapny name: " + name + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
}

