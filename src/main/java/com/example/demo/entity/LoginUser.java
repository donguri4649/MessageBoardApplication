package com.example.demo.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {
	
	private final Logintb logintb;  //ログインに使用するユーザー情報をもたせる。

	public LoginUser(Logintb logintb, Collection<GrantedAuthority> authorityList) {
		super(logintb.getUsername(), logintb.getPassword(), authorityList);
		this.logintb = logintb;
	}
	
	public Logintb getLogintb() {
		return logintb;
	}

}
