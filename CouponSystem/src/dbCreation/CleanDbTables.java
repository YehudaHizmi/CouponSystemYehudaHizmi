package dbCreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import coupon.sys.core.configuration.Configuration;

public class CleanDbTables {
	
	private static Statement statement;
	
	public static void cleanDbTable() {	
		try (Connection con = DriverManager.getConnection(Configuration.getInstance().DB_URL);) {			
			statement = con.createStatement();
			statement.executeUpdate("DELETE FROM COMPANY");
			statement.executeUpdate("DELETE FROM CUSTOMER");
			statement.executeUpdate("DELETE FROM COUPON");
			statement.executeUpdate("DELETE FROM COMPANY_COUPON");
			statement.executeUpdate("DELETE FROM CUSTOMER_COUPON");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}	
}
