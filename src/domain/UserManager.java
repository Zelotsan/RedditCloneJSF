package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import domain.exception.UserException;
@ManagedBean
public class UserManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String INVALID_CREDENTIALS = "Username and/or password is invalid!";
	
	protected Hashtable<String, User> users = new Hashtable<String, User>();
	protected String userManagerFilename;
	protected File userManagerFile;
	private boolean loggedIn = false;
	protected boolean isRegistering = false;
	User user = new User();
	protected String username;
	protected String password;
	
	@SuppressWarnings("unchecked")
	public UserManager() {
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		userManagerFilename = prop.getProperty("user-manager-filename");
		userManagerFile = new File(userManagerFilename);
		if (userManagerFile.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream(userManagerFile);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				users = (Hashtable<String, User>) objectIn.readObject();
				objectIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
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
	
	public boolean getLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean isLogedIn) {
		this.loggedIn = isLogedIn;
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

	public void register() {
		if (validateCredentials()) {
			handleException(new UserException(INVALID_CREDENTIALS));
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
				handleException(new UserException(INVALID_CREDENTIALS));
				invalidateSession();
		} else {
			user = users.get(username);
			if (user != null && user.checkPassword(password)) {
				loggedIn = true;	
			} else {
					handleException(new UserException(INVALID_CREDENTIALS));
					invalidateSession();
			}
		}
	}
	public void logout() {
		loggedIn = false;
		invalidateSession();
	}

	private void invalidateSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	    session.invalidate();
	}
	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream(userManagerFile);
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