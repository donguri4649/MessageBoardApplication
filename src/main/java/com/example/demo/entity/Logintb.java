package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logintb {
	
	private String username;
	private String password;
	private String authority;
	private String userid;
	private String creationdate;
	
}
