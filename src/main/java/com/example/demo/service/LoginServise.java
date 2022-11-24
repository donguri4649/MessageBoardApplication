package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Logintb;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoginServise implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Logintb logintb = userRepository.findByUsername(username);
		
		if(logintb == null) {
			throw new UsernameNotFoundException("そのEmaiは登録されていません");
		}
		//System.out.println("test3:" + logintb);
		
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new LoginUser(logintb,authorityList);
	}
	
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final RowMapper<Logintb> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(Logintb.class);

	//ログイン処理
	public Logintb getUserInfo(String username){
		
		if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT userid,username,creationdate,authority from logintb WHERE username=:username;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("username", username);
		List<Logintb> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			throw new UsernameNotFoundException("Username is empty");
		}
		return userList.get(0);
	}
	    
	//アカウント登録
    @Transactional
    public void register(String username, String password, String date) {
    	StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO "
				+ "logintb(username,password,"
				+ "creationdate,authority) "
				+ "VALUES(:username,:password,"
				+ ":creationdate,:authority)");
		
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("username", username)
				.addValue("password", password)
				.addValue("creationdate", date)
				.addValue("authority", "GUESTS");
		
		template.update(sql.toString(), param);
    }
    
    //登録済みアカウントかの判定
    public boolean isExistUser(String username) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("SELECT userid,username,creationdate,authority from logintb WHERE username=:username;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("username", username);
		List<Logintb> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return false;
		}
        return true;
    }

}
