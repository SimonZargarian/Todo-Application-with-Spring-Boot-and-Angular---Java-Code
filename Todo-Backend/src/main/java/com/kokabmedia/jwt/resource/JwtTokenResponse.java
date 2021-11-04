package com.kokabmedia.jwt.resource;

import java.io.Serializable;

/* 
* This class handles the response JWT token.
* 
* The Serializable interface serializes this object and converts its state to a byte
* stream so that the byte stream can be reverted back into a copy of the object.
*/
public class JwtTokenResponse implements Serializable {

	private static final long serialVersionUID = 8317676219297719109L;

	private final String token;

	public JwtTokenResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}