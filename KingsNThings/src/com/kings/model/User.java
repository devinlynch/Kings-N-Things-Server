package com.kings.model;

public class User {
	private String id;
	private String username;
	private String password;
	private int port;
	private String hostName;

	public User() {

	}

	public User(String id) {
		this.setId(id);
	}

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

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User))
			return false;

		User comparingUser = (User) o;

		String thisId = this.getId();
		String thatId = comparingUser.getId();

		if (thisId == null && thatId == null)
			return false;

		return thisId != null && thisId.equals(thatId);
	}

}
