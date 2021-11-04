package com.kokabmedia.basic.aut;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/*
 * The purpose of this class is to verify Spring Security Basic Authentication.
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
@CrossOrigin(origins="http://localhost:4200") 
public class BasicAuthenticationController {

	/* 
	 * When HTTP request is sent to a certain URL and that URL contains a path which
	 * is declared on the @GetMapping annotation, in this case the appended "/basicauth" this method 
	 * will be called.
	 * 
	 * The @GetMapping annotation will bind and make authenticationBean method respond to a HTTP GET
	 * request.
	 * 
	 * If we have two or more URL's mapped with same "/basicauth" resource the Tomcat server will crash, 
	 * this means that we cannot have any GET, POST, PUT AND DELETE HTTP request controller methods 
	 * in this
	 */
	@GetMapping(path = "/basicauth")
	public AuthenticationBean authenticationBean() {
		//throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
		return new AuthenticationBean("You are authenticated");
	}	
}
