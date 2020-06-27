package it.polimi.tiw.quotationsmenagment.beans;

public class Client {
	private String username;
	private String password;
	
	public Client() {
		
	}
	
	public Client(String username) {
		this.username = username;
	}
	
	public Client(String username, String password) {
		this.password = password;
		this.username = username;
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
}
