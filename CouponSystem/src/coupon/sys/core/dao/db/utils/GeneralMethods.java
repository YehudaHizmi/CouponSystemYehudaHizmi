package coupon.sys.core.dao.db.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * This is a general class that implements some general functions.
 * @author Yehuda.Hizmi
 *
 */
public class GeneralMethods {
	
	/**
	 *This method return the current date without the time stamp 
	 * @return - date without time
	 */
	public static Date getCurrentDate() {
		//Define a calendar to get current date without time
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();		
	}

}
