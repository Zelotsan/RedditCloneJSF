package domain.exception;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class PostException extends Exception {
	private static final long serialVersionUID = 5220252763735012330L;

	public PostException(String message) {
		super(message);
	}
	
	public PostException() {
		super();
	}
	
	public void handleExcpetion() {
		FacesMessage facesMessage = new FacesMessage(getMessage());
		FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
	}
}
