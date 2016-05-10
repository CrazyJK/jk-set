package jk.kamoru;


/**나의 어플리케이션에서 발생하는 에러
 * @author kamoru
 *
 */
public abstract class JKException extends RuntimeException {

	private static final long serialVersionUID = JK.SERIAL_VERSION_UID;

/*	sinse JDK 1.7 */
	public JKException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JKException(String message, Throwable cause) {
		super(message, cause);
	}

	public JKException(String message) {
		super(message);
	}

	public JKException(Throwable cause) {
		super(cause);
	}

	/**에러 종류
	 * @return
	 */
	public abstract String getKind();
}
