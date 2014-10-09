package domain;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class User implements Serializable{
	
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
			throw new UserException();
		if (password == null || password.equals(""))
			throw new UserException();
		return true;
	}
}
