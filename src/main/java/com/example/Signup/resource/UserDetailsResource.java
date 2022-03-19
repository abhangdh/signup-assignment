package com.example.Signup.resource;

import javax.annotation.Resource;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Signup.model.User;

@Resource
public interface UserDetailsResource extends JpaRepository <User,Integer>{
	public User findByEmailIdAndPassword(String email, String password);

}
