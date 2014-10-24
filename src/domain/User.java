package domain;

import java.io.Serializable;

import domain.exception.UserException;


public class User implements Serializable {
	private static final long serialVersionUID = -6114001634652383731L;
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean checkPassword(String password) {
		if (this.password.equals(password)) {
			return true;
		}
		return false;
	}

	public boolean checkEntries() throws UserException {
		if (username == null || username.equals(""))
			throw new UserException("Please provide a username");
		if (password == null || password.equals(""))
			throw new UserException("Please provide a password");
		return true;
	}
}
