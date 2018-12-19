package core.jdbc;

public class DateAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DateAccessException() {
		super();
	}
	
	public DateAccessException(String message,Throwable cause,boolean enableSuppression,boolean writeableStackTrace) {
		super(message,cause,enableSuppression,writeableStackTrace);
	}
	
	public DateAccessException(String message,Throwable cause) {
		super(message,cause);
	}
	public DateAccessException(String message) {
		super(message);
	}
	public DateAccessException(Throwable cause) {
		super(cause);
	}
}
