package com.kokabmedia.jwt.resource;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kokabmedia.jwt.JwtTokenUtil;
import com.kokabmedia.jwt.JwtUserDetails;

/*
* The purpose of this class is to act as an controller class for JWT  
* authentication.
* 
* The dispatcher servlet is the Front Controller for the Spring MVC framework
* handles all the requests of the root (/) of the web application.
* 
* Dispatcher servlet knows all the HTTP request methods GET, POST, PUT AND
* DELETE and what java methods they are mapped to with annotations. Dispatcher
* servlet will delegate which controller should handle a specific request.
* Dispatcher servlet looks at the URL and the request method.
* 
* The @RestController annotation will register this class as a rest controller
* it will be able to receive HTTP request when they are sent and match the URL
* path. With this annotation the class can now handle REST requests.
* 
* @Response body annotation which is part of the @RestController annotation is
* responsible for sending information back from the application to another
* application.
* 
* When we put @ResponseBody on a controller, the response from that will be
* mapped by a http message converter(Jackson) into another format, for example
* a java object to JSON, XML or HTML. Response body converts the java object
* and sends the response back.
*/
@RestController
/*
 * @CrossOrigin annotation makes it possible to Allow request from a specific URL.
 * 
 * It is now possible to allow requests from  other domains for example of a Angular 
 * front-end service.
 */
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationRestController {

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	/*
	 * The @Autowired annotation tells the Spring framework that the AuthenticationManager bean 
	 * and its implementation is an dependency of JwtAuthenticationRestController class. 
	 * It is a mechanism for implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling a interface or the implementation 
	 * of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the AuthenticationManager or its implementation 
	 * and inject (autowires) that instance into the JwtAuthenticationRestController object when it is 
	 * instantiated as a autowired dependency.
	 * 
	 * The AuthenticationManager and its implementation is now a dependency of the 
	 * JwtAuthenticationRestController class.
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
			throws AuthenticationException {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtTokenResponse(token));
	}

	@RequestMapping(value = "${jwt.refresh.token.uri}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUserDetails user = (JwtUserDetails) jwtInMemoryUserDetailsService.loadUserByUsername(username);

		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("INVALID_CREDENTIALS", e);
		}
	}
}
