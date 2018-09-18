package coupon.sys.core.dao.db.utils;

import java.util.Collection;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.configuration.Configuration;

/**
 * This class responsible on functions that support the Emailing process.
 * @author Yehuda.Hizmi
 *
 */
public class TableBuilder {

	private static Configuration config = Configuration.getInstance();
	
	/**
	 * This method build a table with a list of all the deleted coupons and return it as a string
	 * @param coupons - A collection of all the deleted coupons
	 * @return - Return a string of a table with all the deleted coupons 
	 */
	public static String buildTable(Collection<Coupon> coupons ) {
		if(coupons == null) {
			return config.EMAIL_BODY_SUCCESS + config.EMAIL_BODY_CLOSE;
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(config.EMAIL_BODY_SUCCESS + "</br>"); 
		stringBuilder.append
		( 	  "<html>" 
			+ "<body>"
			+ "<br>The following coupons were deleted<br>" 
			+ "<table border=\"1\">" 
			+ "<tr>" 
			+ "<th>Id</th>" 
			+ "<th>Title</th>" 
			+ "<th>Form Date</th>" 
		    + "</tr>"
		);
		for (Coupon coupon: coupons) {
			stringBuilder.append("<tr><td>")
				.append(coupon.getId())
				.append("</td><td>")
				.append(coupon.getTitle())
				.append("</td><td>")
				.append(coupon.getEndDate())
				.append("</td></tr>");
		}
		stringBuilder.append("</table></body></html>");
		stringBuilder.append(config.EMAIL_BODY_CLOSE);
		String html = stringBuilder.toString();	
		return html;
	}
	
}
