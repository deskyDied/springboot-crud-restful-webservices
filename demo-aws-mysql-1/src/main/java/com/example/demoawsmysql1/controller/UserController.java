package com.example.demoawsmysql1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoawsmysql1.entity.User;
import com.example.demoawsmysql1.exception.ResourceNotFoundException;
import com.example.demoawsmysql1.repository.UserRepository;

@RestController //this is a combo of @ResponseBody and @Controller
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	// get all users API
	@GetMapping("/all")
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
	
	// get user by Id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value = "id") int userId){
		//.get() returns User from the Optional wrapper class
		//.orElseThrow() does the same but it will throw an exception if it doesn't find anything, and the 
		//User object if it does.
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " was not found"));
	}
	
	
	// create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	// update user
	@PostMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable(value = "id") int userId) {
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " was not found"));
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		return userRepository.save(existingUser);
	}	
	
	// delete user by Id
	@DeleteMapping("/{id}") //This can be the same as the others, because it's a DELETE http request, so it won't collide
	public ResponseEntity<User> deleteUser(@PathVariable(value = "id") int userId){
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " was not found"));
		userRepository.delete(existingUser);
		return ResponseEntity.ok().build();
	}
}
