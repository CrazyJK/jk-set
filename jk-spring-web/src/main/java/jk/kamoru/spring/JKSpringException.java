package jk.kamoru.spring;

import jk.kamoru.JKException;

public class JKSpringException extends JKException {

	private static final long serialVersionUID = SPRING.SERIAL_VERSION_UID;

	public JKSpringException(String message, Throwable cause) {
		super(message, cause);
	}

	public JKSpringException(String message) {
		super(message);
	}

	public JKSpringException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getKind() {
		return "Spring";
	}
	
}
