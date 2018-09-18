package coupon.sys.core.exceptions;

/**
 * This is a general CouponSystemException.
 * All the exceptions in the Coupons System Project are of this type and the actual exception is thrown with this exception
 * @author Yehuda.Hizmi
 *
 */
public class CouponSystemException extends Exception {

	private static final long serialVersionUID = 1L;

	public CouponSystemException() {
		super();
	}

	public CouponSystemException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public CouponSystemException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CouponSystemException(String arg0) {
		super(arg0);
	}

	public CouponSystemException(Throwable arg0) {
		super(arg0);
	}

}
