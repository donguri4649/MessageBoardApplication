package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.entity.Chat;
import com.example.demo.entity.Inquiry;
import com.example.demo.form.ChatForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InquiryService {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final RowMapper<Inquiry> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(Inquiry.class);

	public List<Inquiry> inquiryInsert(ChatForm form,String today) {

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO inquiry(inquiryname,inquiryemail,"
				+ "inquiryphone,inquircontent,userid,createtime) VALUES"
				+ " (:inquiryname,"
				+ ":inquiryemail,"
				+ ":inquiryphone,"
				+ ":inquircontent,"
				+ ":userid,"
				+ ":createtime)");
		MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("inquiryname", form.getUserName())
                .addValue("inquiryemail", form.getInquiryemail())
                .addValue("inquiryphone", form.getInquiryphone())
                .addValue("inquircontent", form.getInquircontent())
                .addValue("userid", Integer.parseInt(form.getUserId()))
                .addValue("createtime", today);
		
		//template.query(sql.toString(), param, USER_ROW_MAPPER);
		template.update(sql.toString(), param);
		
		return null;
	}
	
	public List<Map<String, Object>> inquirySelect(ChatForm form) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * from inquiry");
		MapSqlParameterSource param = new MapSqlParameterSource();
		
		//template.query(sql.toString(), param, USER_ROW_MAPPER);
		//template.update(sql.toString(), param);
		
		return itiranList(template.query(sql.toString(), param, USER_ROW_MAPPER));
	}
	
	//List<Chat>のキャスト
  	private List<Map<String,Object>> itiranList(List<Inquiry> inquiryList){
  		//System.out.println("test1:" + list);
  		ObjectMapper mapper = new ObjectMapper();
  		String json = null;
  		//Javaオブジェクト→json
  		try {
			json = mapper.writeValueAsString((List<Inquiry>)inquiryList);
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
		return ListMap;
  	}

}
