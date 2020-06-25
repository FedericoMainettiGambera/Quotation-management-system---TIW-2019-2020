package it.polimi.tiw.quotationsmenagment.beans;

public class Client {
	private String username;
	
	public Client() {
		//set attributes with setters
	}
	
	public Client(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
