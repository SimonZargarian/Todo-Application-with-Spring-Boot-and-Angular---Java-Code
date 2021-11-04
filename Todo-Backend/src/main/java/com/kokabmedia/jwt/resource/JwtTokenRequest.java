package com.kokabmedia.jwt.resource;

import java.io.Serializable;


/* 
 * This class handles the request JWT token for Users.
 * 
* The Serializable interface serializes this object and converts its state to a byte
* stream so that the byte stream can be reverted back into a copy of the object.
*/
public class JwtTokenRequest implements Serializable {

	private static final long serialVersionUID = -5616176897013108345L;

	private String username;
	private String password;

	public JwtTokenRequest() {
		super();
	}

	public JwtTokenRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
