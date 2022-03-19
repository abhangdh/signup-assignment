package com.example.Signup.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Signup.model.User;
import com.example.Signup.resource.UserDetailsResource;
import com.example.Signup.view.UserView;

@Service
public class UserService {

	@Autowired
	public UserDetailsResource userDetailsResource;

	public ResponseEntity<?> login(UserView userview) throws NoSuchAlgorithmException {
		String emailId = userview.getEmailId();
		String password = userview.getPassword();
		if (userview.getEmailId() == null || userview.getPassword() == null || userview.getEmailId().trim().isEmpty()
				|| userview.getPassword().isEmpty()) {
			return new ResponseEntity<>("incorrect username or password", HttpStatus.BAD_REQUEST);
		}
		else {
			User user = userDetailsResource.findByEmailIdAndPassword(emailId,getHash(password));
			if(user==null) {
				return new ResponseEntity<>("incorrect username or password", HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<>("Success", HttpStatus.OK);
			}
		}
	}

	public ResponseEntity<?> register(UserView userview) throws NoSuchAlgorithmException {
		if (userview.getEmailId() == null || userview.getPassword() == null || userview.getEmailId().trim().isEmpty()
				|| userview.getPassword().isEmpty()) {
			return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
		}
		final String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userview.getEmailId());
		if(!matcher.matches()) {
			return new ResponseEntity<>("invalid Email", HttpStatus.BAD_REQUEST);
		}
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String currentDate = dateFormat.format(date);
		User user = new User();
		user.setEmailId(userview.getEmailId());
		user.setPassword(getHash(userview.getPassword()));
		user.setLastUpdated(currentDate);
		try {
			userDetailsResource.save(user);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Failed duplicate email", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	public String getHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest(password.getBytes());
		BigInteger no = new BigInteger(1, messageDigest);
		String hashtext = no.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}
}
