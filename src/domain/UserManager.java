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


public class UserManager implements Serializable {
	protected Hashtable<String, User> users = new Hashtable<String, User>();
	protected String userFilename = "/Users/marco/Documents/Temp/users.ser";
	protected File userFile;
	
	User user = new User();
	String username, password;
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
		System.out.println(username + " " + password);
		if (username == null && password == null) {
			try {
				throw new UserException("Username and/or password is not set!");
			} catch (UserException e) {
				handleException(e);
			}
		}
		if (users.containsKey(username)) {
			try {
				throw new UserException("Username already exists. Choose a different one!");
			} catch (UserException e) {
				handleException(e);
			}
		} 
		user.setUsername(this.username);
		user.setPassword(this.password);
		users.put(user.getUsername(), user);
		save();
		
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
	public String login() {
		if ((username == null && password == null)	|| (username == "" && password == "")) {
			try {
				throw new UserException("Username and/or password invalid!");
			} catch (UserException e) {
				handleException(e);
			}
		}
		User user = users.get(username);
		if (user != null && user.checkPassword(password)) {
			return "logedIn.xhtml";
		}
		try {
			throw new UserException("Login failed");
		} catch (UserException e) {
			handleException(e);
		}
		return null;
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
