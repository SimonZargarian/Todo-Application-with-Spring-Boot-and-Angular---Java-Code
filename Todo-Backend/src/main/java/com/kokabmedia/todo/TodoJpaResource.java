package com.kokabmedia.todo;

import java.net.URI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kokabmedia.todo.Todo;

/*
 * @CrossOrigin annotation makes it possible to allow request from a specific URL.
 * 
 * It is now possible to allow requests from  other domains.
 */
@CrossOrigin(origins = "http://localhost:4200")
/*
 * This class can also be named TodoJPAController and its function is to handle
 * HTTP requests, responses and expose recourses to other applications,
 * functioning as a servlet and a as class that communicates with the repository
 * class and the embed database retrieving, deleting, updating and sending data,
 * this class will also convert a JSON payload to a java object of the entity
 * class that is mapped to a database table.
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
public class TodoJpaResource {

	/*
	 * The @Autowired annotation tells the Spring framework that the
	 * todoJpaRepository bean and its implementation is an dependency of
	 * TodoJpaResource class. It is a mechanism for implementing Spring dependency
	 * injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to
	 * avoid tight coupling and enable loose coupling by calling a interface or the
	 * implementation of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the UserRepository or its
	 * implementation and inject (autowires) that instance into the TodoJpaResource
	 * object when it is instantiated as a autowired dependency.
	 * 
	 * The TodoJpaRepository and its implementation is now a dependency of the
	 * TodoJpaResource class.
	 */
	@Autowired
	private TodoJpaRepository todoJpaRepository;

	/*
	 * The @Autowired annotation tells the Spring framework that todoService
	 * instance (bean) is an dependency of TodoResource class, it is a mechanism for
	 * implementing Spring dependency injection.
	 * 
	 * The TodoHardcodedService bean is now a dependency of the TodoJpaResource
	 * class.
	 * 
	 * The Spring framework creates an instance (bean) of the TodoHardcodedService
	 * and autowires as a dependency to the TodoJpaResource class object when it is
	 * instantiated.
	 */
	@Autowired
	private TodoHardcodedService todoService;

	/*
	 * This method will return a collection of todos for a specific user from the 
	 * database using JPA.
	 * 
	 * When HTTP request is sent to a certain URL and that URL contains a path which
	 * is declared on the @GetMapping annotation, in this case the appended 
	 * "jpa/users{username}/todos" this method  will be called.
	 * 
	 * The ("/jpa/users/{username}") parameter allows the method to read the
	 * appended string after the URL http://localhost:4200/jpa/users/
	 * as a path variable that is attached, so when a String is appended after
	 * http://localhost:4200/jpa/users/ with a GET HTTP request the
	 * getAllTodos(PathVariable String username) method is called.
	 * 
	 * The @GetMapping annotation will bind and make getAllTodos() method respond to 
	 * a HTTP GET request.
	 */
	@GetMapping("/jpa/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username) {
		return todoJpaRepository.findByUsername(username);
	}
	
	
	/*
	 * This method returns an user with a specific id from the database using JPA.
	 * 
	 * When a GET HTTP request is sent to a certain URL and that URL contains a path
	 * which is declared on the @GetMapping annotation, in this case the appended
	 * "jpa/users/{username}/todos", this method will be called.
	 * 
	 * The @GetMapping annotation will bind and make getTodo() method respond to
	 * a HTTP GET request.
	 * 
	 * The ("/jpa/users/{username}") parameter allows the method to read the
	 * appended string after the URL http://localhost:4200/jpa/users/
	 * as a path variable that is attached, so when a String is appended after
	 * http://localhost:4200/jpa/users/ with a GET HTTP request the
	 * getTodo(PathVariable String username) method is called.
	 * 
	 * The name of the "/{username}" parameter must match the @PathVariable
	 * annotation argument String username.
	 */
	@GetMapping("/jpa/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id) {
		return todoJpaRepository.findById(id).get();
		// return todoService.findById(id);
	}


	/*
	 * This method will delete a user with a specific id from the database using
	 * jPA.
	 * 
	 * When a HTTP DELETE request is sent to a certain URL and that URL contains a
	 * path which is declared on the @DeleteMapping annotation, in this case the
	 * appended "/jpa/users/{username}/todos/{id}", this method will be called.
	 * 
	 * The @DeleteMapping annotation will bind and make deleteTodo() method respond
	 * to a HTTP DELETE request.
	 * 
	 * The ("/jpa/users/{username}") parameter allows the method to read the
	 * appended string after the URL http://localhost:4200/jpa/users/
	 * as a path  variable that is attached, so when a id is appended after
	 * http://localhost:4200/jpa/users/ with a DELETE HTTP request the
	 * deleteTodo(@PathVariable String username, @PathVariable long id) method is
	 * called. The name of the "/{username}" parameter must match the @PathVariable
	 * annotation argument String username.
	 */
	@DeleteMapping("/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {

		todoJpaRepository.deleteById(id);

		// Return HTTP status No content
		return ResponseEntity.noContent().build();
		// return ResponseEntity.notFound().build();
	}

	/*
	 * This method updates a todo with HTTP PUT request containing a JSON body and
	 * stores it in the database using JPA. We get the content of the user coming is
	 * as part of the request body.
	 * 
	 * The updateTodo() method will be a web service endpoint that converts JSON
	 * paylod into a java object.
	 * 
	 * When HTTP PUT request is sent to a certain URL and that URL contains a path
	 * which is declared on the @PutMapping annotation then this method will be
	 * called, in this case the appended "jpa/users".
	 * 
	 * The @PutMapping annotation will bind and make updateTodo() method respond to
	 * a HTTP PUT request. The HTTP PUT request body will be passed to
	 * the @RequestBody in this case Todo todo.
	 * 
	 * The ("/jpa/users/{username}") parameter allows the method to read the
	 * appended string after the URL http://localhost:4200/jpa/users/ as a path
	 * variable that is attached, so when a id is appended after
	 * http://localhost:4200/jpa/users/ with a PUT HTTP request the
	 * updateTodo(@PathVariable String username, @RequestBody Todo todo) method is
	 * called. The name of the "/{username}" parameter must match the @PathVariable
	 * annotation argument String username.
	 */
	@PutMapping("/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id,
			@RequestBody Todo todo) {

		// Todo todoUpdated = todoService.save(todo);
		Todo todoUpdated = todoJpaRepository.save(todo);

		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}

	/*
	 * This method creates a new user with HTTP POST request containing a JSON body
	 * and stores it in the database using JPA. We get the content of the user
	 * coming is as part of the request body.
	 * 
	 * The createTodo() method will be a web service endpoint that converts JSON
	 * paylod into a java object.
	 * 
	 * When HTTP POST request is sent to a certain URL and that URL contains a path
	 * which is declared on the @PostMapping annotation then this method will be
	 * called, in this case the appended "jpa/users".
	 * 
	 * The @PostMapping annotation will bind and make createTodo() method respond to
	 * a HTTP POST request. The HTTP POST request body will be passed to
	 * the @RequestBody in this case Todo todo.
	 * 
	 * The ("/jpa/users/{username}") parameter allows the method to read the
	 * appended int after the URL http://localhost:4200/jpa/users/todos
	 * as a path variable that is attached, so when a id is appended after
	 * http://localhost:4200/jpa/users/ with a POST HTTP request the
	 * createTodo(@PathVariable String username, @RequestBody Todo todo) method is
	 * called. The name of the "/{username}" parameter must match the @PathVariable
	 * annotation argument String username.
	 */
	@PostMapping("/jpa/users/{username}/todos")
	public ResponseEntity<Void> createTodo(@PathVariable String username, @RequestBody Todo todo) {

		// Todo createdTodo = todoService.save(todo);
		todo.setUsername(username);
		Todo createdTodo = todoJpaRepository.save(todo);

		/*
		 * ServletUriComponentsBuilder creates a URI for the location of a new created
		 * Todo resource.
		 * 
		 * It shows the the URI of the newly created User resource the Location section
		 * of the HTTP response header.
		 * 
		 * The path() method appends the id to the @PostMapping("/id") URI, this way we
		 * do not have to hard code the URI.
		 * 
		 * buildAndExpand passes the value that we want replace in the path("/{id}"
		 * method.
		 * 
		 * Return current request URI.
		 */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId())
				.toUri();

		/*
		 * Return HTTP status code 201 CREATED, as per HTTP best practices.
		 * 
		 * Pass in the URI location of the newly created Todo resource, so it can be
		 * returned as a HTTP response.
		 * 
		 * If the consumer wants to know where the resource was created they see in the
		 * Location from the header of the response.
		 */
		return ResponseEntity.created(uri).build();
	}

}
