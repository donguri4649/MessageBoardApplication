package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Chat;
import com.example.demo.entity.Logintb;
import com.example.demo.form.ChatForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class UserRepository{
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final RowMapper<Logintb> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(Logintb.class);

	
	public Logintb findByUsername(String username){
		
		if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT username,password,authority from logintb WHERE username=:username;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("username", username);
		List<Logintb> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			throw new UsernameNotFoundException("Username is empty");
		}
		return userList.get(0);
	}

	public List<Map<String,Object>> accountUsername(){

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT username,authority from logintb");
		SqlParameterSource param = new MapSqlParameterSource();
		List<Logintb> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);

		return itiranList(userList);
	}
	
	public List<Map<String,Object>> janitorOfficeAccountSetting(ChatForm form, String userFlag){

		StringBuilder sql = new StringBuilder();
		MapSqlParameterSource param = new MapSqlParameterSource();
		List<Logintb> userList = null;
		if(("1").equals(userFlag)) {
			sql.append("SELECT userid,username,creationdate,authority from logintb WHERE username=:username;");
			param.addValue("username", form.getUserName());
			userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		}else {
			sql.append("SELECT userid,username,creationdate,authority from logintb");
			userList = template.query(sql.toString(), param, USER_ROW_MAPPER);

		}
		
		return itiranList(userList);
	}
	
	//List<Chat>のキャスト
  	private List<Map<String,Object>> itiranList(List<Logintb> userList){
  		//System.out.println("test1:" + list);
  		ObjectMapper mapper = new ObjectMapper();
  		String json = null;
  		//Javaオブジェクト→json
  		try {
			json = mapper.writeValueAsString((List<Logintb>)userList);
			//System.out.println("test2:" + json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
  		//json→Javaオブジェクト
  		List<Map<String,Object>> ListMap = null;
  		try {
			ListMap = (List<Map<String,Object>>)mapper.readValue(json, new TypeReference<List<Map<String,Object>>>(){});
			//System.out.println("test3:" + ListMap);
  		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
  		
  		for(int i = 0;i < userList.size();i++) {
  			if(userList.get(i).getUsername().length() > 50) {
  				ListMap.get(i).put("chatMold", userList.get(i).getUsername().substring(0, 50));
  			}
  		}
  		//System.out.println("test1:" + ListMap);
		return ListMap;
  		
  	}


	
	
}
