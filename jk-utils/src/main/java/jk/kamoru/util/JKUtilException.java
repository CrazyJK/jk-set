package jk.kamoru.util;

import jk.kamoru.JK;
import jk.kamoru.JKException;

public class JKUtilException extends JKException {

	private static final long serialVersionUID = JK.SERIAL_VERSION_UID;

	public JKUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public JKUtilException(String message) {
		super(message);
	}

	public JKUtilException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getKind() {
		return "Util";
	}
	
}
