package dbCreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import coupon.sys.core.configuration.Configuration;

public class CreateDb {

	public static void main(String[] args) {

		// Define variable
		String sql;
		Statement statement = null;

		//getting the DB URL from the configuration class 
		try (Connection con = DriverManager.getConnection(Configuration.getInstance().DB_URL);) {
			System.out.println("Connection established");
			System.out.println("Starting to create tables");			
			
			//Creating the DB tables
			sql = 	"CREATE TABLE COMPANY"
					+ "(ID 				BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "COMP_NAME 		VARCHAR(255),"
					+ "PASSWORD			VARCHAR(255),"
					+ "EMAIL 			VARCHAR(255),"	
					+ "PRIMARY KEY (ID))";
			
			statement = con.createStatement();
			statement.executeUpdate(sql);
			
			sql = 	"CREATE TABLE CUSTOMER"
					+ "(ID 				BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "CUST_NAME 		VARCHAR(255),"
					+ "PASSWORD			VARCHAR(255),"	
					+ "PRIMARY KEY (ID))";
			
			statement = con.createStatement();
			statement.executeUpdate(sql);

			sql = 	"CREATE TABLE COUPON"
					+ "(ID 				BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
					+ "TITLE	 		VARCHAR(255),"
					+ "START_DATE		DATE,"	
					+ "END_DATE			DATE,"	
					+ "AMOUNT           INTEGER,"
					+ "TYPE           	VARCHAR(255),"
					+ "MESSAGE          VARCHAR(255),"
					+ "PRICE            DOUBLE,"
					+ "IMAGE        	VARCHAR(255),"
					+ "PRIMARY KEY (ID))";
			
			statement = con.createStatement();
			statement.executeUpdate(sql);
			
			sql = 	"CREATE TABLE CUSTOMER_COUPON"
					+ "(CUST_ID 		BIGINT,"
					+ "COUPON_ID	 	BIGINT,"
					+ "PRIMARY KEY (CUST_ID,COUPON_ID))";
			
			statement = con.createStatement();
			statement.executeUpdate(sql);
			
			sql = 	"CREATE TABLE COMPANY_COUPON"
					+ "(COMP_ID 		BIGINT,"
					+ "COUPON_ID	 	BIGINT,"
					+ "PRIMARY KEY (COMP_ID,COUPON_ID))";
			
			statement = con.createStatement();
			statement.executeUpdate(sql);
		/*	
			sql = 	"CREATE SEQUENCE COMPANY_SEQ AS BIGINT START WITH 1";
			
			statement = con.createStatement();
			statement.executeUpdate(sql);
			
			sql = 	"CREATE SEQUENCE CUSTOMER_SEQ AS BIGINT START WITH 1";
			
			statement = con.createStatement();
			statement.executeUpdate(sql);
			
			sql = 	"CREATE SEQUENCE COUPON_SEQ AS BIGINT START WITH 1";
			
			statement = con.createStatement();
			statement.executeUpdate(sql);
			*/
			System.out.println("Tables created");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
