package coupon.sys.core.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import coupon.sys.core.configuration.Configuration;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * 
 * @author Yehuda.Hizmi
 * This class has the responsibility to manage the connections.
 * It creates 10 connections, release a connection once its available and make sure to add it back to the pool once it returned.
 * In addition it closes all the connection only when the connection had returned to the pool.
 *
 */
public class ConnectionPool {

	private Set<Connection> connections;
	Configuration config;
	private boolean ReleaseConnection = true;
	private static ConnectionPool instance;

	/**
	 * The constructor do 3 things:
	 * 1. Initialize the connection Hash table and <br/> 
	 * 2. Creates a new instance of Configuration object and <br/>
	 * 3. Calls initializeConnectionPool() method - which populate the hash table with open connections <br/>
	 * @throws CouponSystemException - This is a general exception that is thrown form other methods. 
	 * 
	 */
	private ConnectionPool() throws CouponSystemException {
		connections = new HashSet<>();
		config = Configuration.getInstance();
		initializeConnectionPool();
	}

	/**
	 * 
	 * This method returns an instance of the connection pool.
	 * @return - an instance of the connection pool
	 * @throws CouponSystemException - This is a general exception that is thrown form other methods.
	 */
	public synchronized static ConnectionPool getInstance() throws CouponSystemException {
		if(instance == null){
			instance = new ConnectionPool();
		}
		return instance;
	}

	/**
	 * Initialize the collection with the maximum number of connections
	 * @throws CouponSystemException - throws a general CouponSystemException object with "Can't establish a new connections" message and the relevant object
	 * 
	 */
	private void initializeConnectionPool() throws CouponSystemException {
		while (this.connections.size() < config.DB_MAX_CONNECTIONS) {
			try {
				connections.add(DriverManager.getConnection(config.DB_URL));
			} catch (SQLException e) {
				throw new CouponSystemException("Can't establish a new connections", e);
			}
		}
		return;
	}

	/**
	 * 
	 * @return Return a connection to the requester only if there are available connections in the connection pool.
	 * in case there aren't it wait until a new connection is available
	 * @throws CouponSystemException - throws a general CouponSystemException object with "After wait exception" message and the relevant object
	 */
	public synchronized Connection getConnection() throws CouponSystemException {
		if(ReleaseConnection) {
			if (this.connections.size() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new CouponSystemException("After wait exception", e);
				}
			}
			// Define an Iterator in order to return one connection from the pool
			Iterator<Connection> iterator = this.connections.iterator();
			Connection connection = iterator.next();
			iterator.remove();
			connections.remove(connection);
			return connection;
		}
		return null;
	}

	
	/**
	 * 
	 * This method gets a returned connection as add it back to the connection pool and notify that a connection is available 
	 * @param connection - The returned connection to be added to back to the connection pool 
	 */
	//this connection has been released and need to be added back to the connection pool
	public synchronized void returnConnection(Connection connection) {
		if (connection instanceof Connection) {
			this.connections.add(connection);
			notifyAll();
		}
		return;
	}


	/**
	 * This method closes all connections in the connection pool.
	 * All the connections will be closed only after they are added back to the connection pool.
	 * @throws CouponSystemException - a general exception with the message "Unable to close connection" and the object causes that.  
	 * @throws InterruptedException - an interrupted exception.
	 */
	//The connection won't be closed until all the connections are back on the pool	
	public void closeAllConnections() throws CouponSystemException, InterruptedException {
		try {
			//Update the ReleaseConnection attribute as false in order to make sure to prevent new connections from being released to users  
			this.ReleaseConnection = false;
			//System.out.println(this.connections.size());
			//in order no the do many runs - put the loop to run every 3 seconds
			while(this.connections.size() < config.DB_MAX_CONNECTIONS) Thread.sleep(3000);
			for (Connection connection : this.connections) {
				connection.close();
				//System.out.println("Connection Closed");
			}
			//Remove all the closed connections from the connections collection
			this.connections.clear();
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to close connection", e);
		}
		return;
	}

}
