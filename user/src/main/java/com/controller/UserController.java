package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.User;
import com.user.UserManager;

@RestController
public class UserController {
	
	@GetMapping("/getuser")
	public User GetUser(@RequestParam(value = "username") String userName) {
		try {
			UserManager userManager = new UserManager();
			User user = userManager.FindUserByUserName(userName);
		
			return user;
		}
		catch(Exception ex) {
			return new User();
		}
	}
	
	@PostMapping(path= "/createuser", consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> CreateUser(@RequestBody User user ) {	
		try {		
			UserManager userManager = new UserManager();
		
			User foundUser = userManager.FindUserByUserName(user.getUserName());
		
			if(foundUser.getUserName() == null) {
				userManager.CreateUser(user);
				return new ResponseEntity<User>(HttpStatus.OK);
			}
			return new ResponseEntity<User>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		catch(Exception ex) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteuser")
	public ResponseEntity<User> DeleteUser(@RequestParam(value = "username") String userName) {
		try {
			UserManager userManager = new UserManager();
			User user = userManager.FindUserByUserName(userName);
		
			if(user.getUserName() != null) {
				userManager.DeleteUser(user);
				return new ResponseEntity<User>(HttpStatus.OK);
			}
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		catch(Exception ex) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path= "/updateuser", consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> UpdateUser(@RequestBody User user) {
		try {
			UserManager userManager = new UserManager();
			User foundUser = userManager.FindUserByUserName(user.getUserName());
		
			if(foundUser.getUserName() != null) {
				userManager.Update(user);
				return new ResponseEntity<User>(HttpStatus.OK);
			}
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		catch(Exception ex) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}