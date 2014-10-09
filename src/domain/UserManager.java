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
	private static final long serialVersionUID = 709188204922858134L;
	protected Hashtable<String, User> users = new Hashtable<String, User>();
	protected String userFilename = "/Volumes/HDD/dmh/Downloads/feed.ser";
	protected File userFile;
	protected boolean loggedIn = false;
	protected String username, password;
	
	User user = new User();
	
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

	public String register() {
		System.out.println(username + " " + password);
		if (username == null && password == null) {
			try {
				throw new UserException("Username and/or password is not set!");
			} catch (UserException e) {
				handleException(e);
			}
		} else { return "register.xhtml"; }
		if (users.containsKey(username)) {
			try {
				throw new UserException("Username already exists. Choose a different one!");
			} catch (UserException e) {
				handleException(e);
			}
		} else {
			user.setUsername(this.username);
			user.setPassword(this.password);
			users.put(user.getUsername(), user);
			save();
			login();
			return "index.xhtml";
		}
		return "register.xhtml";
	}

	private void handleException(UserException e) {
		String message="";
		if (e instanceof UserException){
			message = e.getMessage();
		}
		else
		message = "An unexpected error occured !";
		FacesMessage facesMessage = new FacesMessage(message);
		FacesContext.getCurrentInstance().addMessage(null,  facesMessage);
		
	}
	public void login() {
		if ((username == null && password == null)	|| (username == "" && password == "")) {
			try {
				throw new UserException("Username and/or password invalid!");
			} catch (UserException e) {
				handleException(e);
			}
		} else {
			User user = users.get(username);
			if (user != null && user.checkPassword(password)) {
				loggedIn = true;	
			} else {
				try {
					throw new UserException("Username and/or Password is invalid!");
				} catch (UserException e) {
					handleException(e);
				}
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
