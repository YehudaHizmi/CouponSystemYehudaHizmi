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
import coupon.sys.core.connection.ConnectionPool;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * This class implements all the methods in CouponDao interface.
 * It has an empty constructor and it as an attribute of ConnectionPool type
 * @author Yehuda.Hizmi
 *
 */
public class CouponDaoDb implements CouponDao {

	private ConnectionPool connectionPool;

	public CouponDaoDb() {
	}

	@Override
	public void createCoupon(Coupon coupon) throws CouponSystemException {
		String createCouponSql = "INSERT INTO COUPON (TITLE,START_DATE,END_DATE,AMOUNT,TYPE,MESSAGE,PRICE,IMAGE) VALUES(?,?,?,?,?,?,?,?)";
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(createCouponSql,PreparedStatement.RETURN_GENERATED_KEYS);
			//pstmt.setLong(1, coupon.getId());
			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, new java.sql.Date(coupon.getStartDate().getTime()));
			pstmt.setDate(3, new java.sql.Date(coupon.getEndDate().getTime()));
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getType().name());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());				
			if (pstmt.executeUpdate() == 0) {// Check the number of rows inserted - in case no row inserted rise an exception
				throw new CouponSystemException("Coupon [Coupon title: " + coupon.getTitle() + "] was not created");
			}
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			//Update the coupon object with the generated key (this is the coupon's id)
			coupon.setId(rs.getLong(1));
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to create a new coupon [Coupon title: " + coupon.getTitle() + " , Coupon id: " + coupon.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	/*
	 * When delete a coupon the delete process is as follow: 1.Customer_Coupon 2.Company_Coupon 3.Coupon
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		long couponId = coupon.getId();// Since the id is used for several queries it been kept as a variable
		String deleteCustomerCouponSql = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID = " + couponId;
		String deleteCompanyCouponSql = "DELETE FROM COMPANY_COUPON WHERE COUPON_ID = " + couponId;
		String deleteCouponSql = "DELETE FROM COUPON WHERE ID = " + couponId;
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(deleteCustomerCouponSql);
			stmt.executeUpdate(deleteCompanyCouponSql);
			stmt.executeUpdate(deleteCouponSql);
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to delete coupon [Coupon title: " + coupon.getTitle() + " , Coupon id: " + coupon.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String updateCouponSql = "UPDATE COUPON SET TITLE = ? , START_DATE = ? , END_DATE = ? , AMOUNT = ? , TYPE = ? , MESSAGE = ? , PRICE = ? , IMAGE = ? WHERE ID = " + coupon.getId();
		try {
			PreparedStatement pstmt = connection.prepareStatement(updateCouponSql);
			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, new java.sql.Date(coupon.getStartDate().getTime()));
			pstmt.setDate(3, new java.sql.Date(coupon.getEndDate().getTime()));
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getType().name());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());
			if (pstmt.executeUpdate() == 0) {// Check the number of rows updated - in case no row updated rise an exception
				throw new CouponSystemException("Coupon [Coupon title: " + coupon.getTitle() + " , Coupon id: " + coupon.getId() + "] was not updated");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to update coupon's information [Coupon title: " + coupon.getTitle() + " , Coupon id: " + coupon.getId() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Coupon getCoupon(long id) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT * FROM COUPON WHERE ID = " + id;
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCouponSql);
			if (!result.next()) {
				return null;
			}
			return new Coupon(result.getLong(1), result.getString(2), result.getDate(3), result.getDate(4), result.getInt(5), CouponType.valueOf(result.getString(6)), result.getString(7),
					result.getDouble(8), result.getString(9));
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch coupon's information [Coupon id: " + id + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	/*
	 * This method was added since the the is is unknown-
	 * When creating a coupon the id is automatically given and it is not known and the method "getCoupon(long id)
	 * can not be activated
	 * 
	 */
	@Override
	public Coupon getCoupon(String title) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT * FROM COUPON WHERE TITLE = '" + title + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCouponSql);
			if (!result.next()) {
				return null;
			}
			return new Coupon(result.getLong(1), result.getString(2), result.getDate(3), result.getDate(4), result.getInt(5), CouponType.valueOf(result.getString(6)), result.getString(7),
					result.getDouble(8), result.getString(9));
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch coupon [Coupon title: " + title + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT * FROM COUPON";
		Collection<Coupon> coupons = new HashSet<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCouponSql);
			if (!result.next()) {
				return null;
			} else {
				do {
					coupons.add(new Coupon(result.getLong(1), result.getString(2), result.getDate(3), result.getDate(4), result.getInt(5), CouponType.valueOf(result.getString(6)), result.getString(7),
							result.getDouble(8), result.getString(9)));
				} while (result.next());
			}
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch all coupons information or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Coupon> getAllCouponsByType(CouponType couponType) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT * FROM COUPON WHERE TYPE = '" + couponType.name() + "'";
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
			throw new CouponSystemException("Unable to fetch all coupons by type information [Coupon Type: " + couponType.name() + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	/*********************************************************************************************************/
	/*************** The following methods were added in order to simplify the business logic ****************/
	/*********************************************************************************************************/

	// This method return true if the select return a record with the customer name otherwise false
	public boolean checkDbExistence(Coupon coupon) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT * FROM COUPON WHERE TITLE = '" + coupon.getTitle() + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCouponSql);
			if (result.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to check coupon existence or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}
	
	
	
	public Collection<Coupon> getCouponsUpToDate(java.util.Date upToDate) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCouponSql = "SELECT * FROM COUPON CO WHERE END_DATE < '" + (new java.sql.Date(upToDate.getTime())) + "'";
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
			throw new CouponSystemException("Unbale to fetch coupons by date or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public long getCouponIdFromName(String title) throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		String selectCompanySql = "SELECT * FROM COUPON WHERE TITLE = '" + title + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectCompanySql);	
			if(!result.next()) {
				return -1;
			}
			return result.getLong(1);
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to fetch coupon's id [Coupon title: " + title + "] or get a new DB connection", e);
		} finally {
			connectionPool.returnConnection(connection);
		}
	}

}
