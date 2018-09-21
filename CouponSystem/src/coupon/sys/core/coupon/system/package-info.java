/**
 * This package handle one singleton class which provides one instance of the system.
 * The package and it class are responsible for:
 * 1.Load all the DAOs classes
 * 2.To start the Daily Coupon Expiration Task - a thread that runs every 24 hours and deletes all the expired coupons
 * 3.Get an instance of the connection pool  
 */
package coupon.sys.core.coupon.system;