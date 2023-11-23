package com.shopecommerce;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePassword {
	public  static String GenerPass(String passwd) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
		passwd = encoder.encode(passwd);
		System.out.println(passwd);
		return passwd;
	}
}
