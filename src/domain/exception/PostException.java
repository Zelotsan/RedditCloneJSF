package domain.exception;

public class PostException extends Exception {
	private static final long serialVersionUID = 5220252763735012330L;

	public PostException(String message) {
		super(message);
	}
	
	public PostException() {
		super();
	}
}
