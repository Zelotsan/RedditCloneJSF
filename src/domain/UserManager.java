package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
public class UserManager implements Serializable {
	private Hashtable<String, User> users = new Hashtable<String, User>();
	private String userFilename = "/Users/marco/Documents/Temp/users.ser";
	private File userFile;
	private boolean isLogedIn = false;
	private boolean isRegistering = false;
	private String username, password;
	private String wrongCredentials = "Username and/or password is invalid!";
	
	User user = new User();
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	
	public boolean getIsLogedIn() {
		return isLogedIn;
	}
	public void setIsLogedIn(boolean isLogedIn) {
		this.isLogedIn = isLogedIn;
	}
	
	public boolean getIsRegistering() {
		return isRegistering;
	}
	public void setIsRegistering(boolean isRegistering) {
		this.isRegistering = isRegistering;
	}
	public void enableRegisterView() {
		setIsRegistering(true);
	}
	public void disableRegisterView() {
		setIsRegistering(false);
	}
	
	@SuppressWarnings("unchecked")
	public UserManager() {
		userFile = new File(userFilename);
		if (userFile.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream(userFile);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				users = (Hashtable<String, User>) objectIn.readObject();
				objectIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void register() {
		if (validateCredentials()) {
			handleException(new UserException(wrongCredentials));
		} else if (users.containsKey(username)) {
				handleException(new UserException("Username already exists. Choose a different one!"));
		} else {
			user.setUsername(this.username);
			user.setPassword(this.password);
			users.put(user.getUsername(), user);
			save();
			login();
			disableRegisterView();
		}
	}
	private boolean validateCredentials() {
		return username == null || password == null || username == "" || password == "";
	}

	private void handleException(UserException e) {
		String message="";
		if (e instanceof UserException){
		message = e.getMessage();
		} else {
			message = "An unexpected error occured !";
		}
		FacesMessage facesMessage = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
	}
	public void login() {
		if (validateCredentials()) {
				handleException(new UserException(wrongCredentials));
				invalidateSession();
		} else {
			user = users.get(username);
			if (user != null && user.checkPassword(password)) {
				isLogedIn = true;	
			} else {
					handleException(new UserException(wrongCredentials));
					invalidateSession();
			}
		}
	}
	public void logout() {
		isLogedIn = false;
		invalidateSession();
	}

	private void invalidateSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	    session.invalidate();
	}
	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream(userFile);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(users);
			objectOut.flush();
			objectOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isLoginReserved(String login) {
		return users.containsKey(login);
	}
}
