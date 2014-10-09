package domain;

public class UserException extends Exception {
	private static final long serialVersionUID = 6363870950825598528L;

	public UserException(String message) {
		super(message);
	}
	
	public UserException() {
		super();
	}
}