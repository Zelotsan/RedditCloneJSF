package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;


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

	public void register() throws UserException {
		System.out.println(username + " " + password);
		if (username == null && password == null) {
			throw new UserException("Username and/or password is not set!");
		}
		if (users.containsKey(username)) {
			throw new UserException("Username already exists. Choose a different one!");
		} 
		user.setUsername(this.username);
		user.setPassword(this.password);
		users.put(user.getUsername(), user);
		save();
		
	}

	public User login(String login, String password) throws UserException {
		if ((login == null && password == null)	|| (login == "" && password == "")) {
			return null;
		}
		User user = users.get(login);
		if (user != null && user.checkPassword(password)) {
			return user;
		}
		throw new UserException();
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
