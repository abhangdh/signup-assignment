package com.example.Signup.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Signup.service.UserService;
import com.example.Signup.view.UserView;

@RestController
public class UserController {
	
	@Autowired
	public UserService userservice;
	
	@PostMapping(path = "/login" )
	public ResponseEntity<?> login(@RequestBody UserView userview) throws NoSuchAlgorithmException {
		return userservice.login(userview);
	}
	
	@PostMapping(path = "/signup")
	public  ResponseEntity<?> signup(@RequestBody UserView userview) throws NoSuchAlgorithmException {
		return userservice.register(userview);
	}

}
