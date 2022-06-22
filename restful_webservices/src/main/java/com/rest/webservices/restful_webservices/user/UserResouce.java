package com.rest.webservices.restful_webservices.user;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
public class UserResouce {

	@Autowired
	private UserDao service;
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path = "/message")
	public String international(@RequestHeader(name = "Accept-Language",required = false) Locale locale){
		return messageSource.getMessage("good.mornning.message", null, locale);
	}
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user=service.findOne(id);
		if(user==null)
			 throw new UserNotFoundException("id-"+id+"-not exist");
		
		Link linkk= linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("All-user");
		EntityModel<User> ress= EntityModel.of( user,linkk);
		return ress;
	}
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user=service.deleteOne(id);
		if(user==null)
			 throw new UserNotFoundException("id-"+id+"-not exist");
		
	}
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User userToSeved=service.save(user);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest()
				     .path("{/id}").buildAndExpand(userToSeved.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
