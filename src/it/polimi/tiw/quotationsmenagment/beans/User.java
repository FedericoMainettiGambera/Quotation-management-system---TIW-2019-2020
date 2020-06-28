package it.polimi.tiw.quotationsmenagment.beans;

public class User {
	private String username;
	private String password;
	private int ID;
	private boolean isClient;
	
	public User() {
		
	}
	
	public User(String username) {
		this.username = username;
	}
	
	public User(String username, String password) {
		this.password = password;
		this.username = username;
	}
	
	public User(String username, String password, int ID) {
		this.password = password;
		this.username = username;
		this.ID = ID;
	}
	
	public String getUsername() {
		return this.username;
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

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public boolean isClient() {
		return isClient;
	}
	
	public boolean isEmployee() {
		return !isClient;
	}

	public void setIsClient(boolean isClient) {
		this.isClient = isClient;
	}
}
