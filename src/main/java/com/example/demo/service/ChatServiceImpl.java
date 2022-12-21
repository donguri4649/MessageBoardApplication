package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.example.demo.entity.Chat;
import com.example.demo.entity.Logintb;
import com.example.demo.form.ChatForm;
import com.example.demo.repository.ChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
//@Transactional
public class ChatServiceImpl implements ChatService {

	@Autowired
	ChatRepository chatrepository;
	
	//全権取得
	@Override
	public Iterable<Chat> selectAll() {
		return chatrepository.findAll();
	}

	//一件取得
	@Override
	public Optional<Chat> selectOneById(Integer id) {
		return chatrepository.findById(id);
	}

	@Override
	public void insertChat(Chat chat) {
		chatrepository.save(chat);
	}

	@Override
	public void updateChat(Chat chat) {
		chatrepository.save(chat);
	}

	@Override
	public void deleteChatById(Integer id) {
		chatrepository.deleteById(id);
	}
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final RowMapper<Chat> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(Chat.class);

	public List<Map<String,Object>> searchChat(ChatForm form, Model model,String serchDivision) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * from chat ");
		MapSqlParameterSource param = new MapSqlParameterSource();
		List<Chat> userList = null;
		int count = 0;
		String[] SearchItems = null;
		if(!("0").equals(serchDivision)) {
			SearchItems = form.getSearchEntry().split(",");
			count = SearchItems.length;
			System.out.println("count:" + count);
		}
		
		if(0 != count) {
			sql.append("WHERE ");
			if(("3").equals(serchDivision)) {
				sql.append("userName=:userName");
				param.addValue("userName", form.getUserName());
			} else if(("2").equals(serchDivision)) { 
				sql.append("address=:address");
				param.addValue("address", form.getUserName());
			} else {
				if(!("").equals(SearchItems[0])) {
					System.out.println("address:" + SearchItems[0]);
					sql.append("address=:address");
					param.addValue("address", SearchItems[0]);
					model.addAttribute("dispUserSerch",SearchItems[0]);
				}
				if(count > 1) {
					if(!("").equals(SearchItems[1])) {
						System.out.println("chat:" + SearchItems[1]);
						if(!("").equals(SearchItems[0])) {
							sql.append(" and chat like :chat");
						}else {
							sql.append("　chat like :chat");
						}
						param.addValue("chat", "%" + SearchItems[1] + "%");
						model.addAttribute("serchChat",SearchItems[1]);
					}
				}
				if(count > 2) {
					if(!("").equals(SearchItems[2])) {
						System.out.println("userName:" + SearchItems[2]);
						if(!("").equals(SearchItems[0]) || !("").equals(SearchItems[1])) {
							sql.append(" and userName=:userName");
						}else {
							sql.append("　userName=:userName");
						}
						param.addValue("userName", SearchItems[2]);
						model.addAttribute("serchCreator",SearchItems[2]);
					}
				}
				if(count > 3) {
					if(!("").equals(SearchItems[3])) {
						SearchItems[3] = SearchItems[3].replace("-", "/");
						System.out.println("createTime:" + SearchItems[3]);
						if(!("").equals(SearchItems[0]) || !("").equals(SearchItems[1]) || !("").equals(SearchItems[2])) {
							sql.append(" and createTime like :createTime");
						}else {
							sql.append("　createTime like :createTime");
						}
						param.addValue("createTime", SearchItems[3] + "%");
						model.addAttribute("serchCreateTime",SearchItems[3]);
					}
				}
				if(count > 4) {
					if(!("").equals(SearchItems[4])) {
						SearchItems[4] = SearchItems[4].replace("-", "/");
						System.out.println("updateTime:" + SearchItems[4]);
						if(!("").equals(SearchItems[0]) || !("").equals(SearchItems[1]) || !("").equals(SearchItems[2]) || !("").equals(SearchItems[3])) {
							sql.append(" and updateTime like :updateTime");
						}else {
							sql.append("　updateTime like :updateTime");
						}
						param.addValue("updateTime", SearchItems[4] + "%");
						model.addAttribute("serchUpdateTime",SearchItems[4]);
					}
				}
			}
		}
		//"1":降順  "2":昇順
		if(("2").equals(form.getCreateSortTime())) {
			sql.append(" ORDER BY createtime");
		}else if(("1").equals(form.getCreateSortTime())){
			sql.append(" ORDER BY createtime DESC");
		}
		//System.out.println("test1:" + form.getCreateSortTime());
		if(("2").equals(form.getUpdateSortTime())) {
			if(("2").equals(form.getCreateSortTime()) || ("1").equals(form.getCreateSortTime())) {
				sql.append(", updatetime");
			}else {
				sql.append(" ORDER BY updatetime");
			}
		}else if(("1").equals(form.getUpdateSortTime())){
			if(("2").equals(form.getCreateSortTime()) || ("1").equals(form.getCreateSortTime())) {
				sql.append(", updateTime DESC");
			}else {
				sql.append(" ORDER BY updateTime DESC");
			}
		}
		//System.out.println("test1:" + form.getUpdateSortTime());
		userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		//System.out.println("userList:" + userList);
		return itiranList(userList);
	}
	
	//List<Chat>のキャスト
  	private List<Map<String,Object>> itiranList(List<Chat> userList){
  		//System.out.println("test1:" + list);
  		ObjectMapper mapper = new ObjectMapper();
  		String json = null;
  		//Javaオブジェクト→json
  		try {
			json = mapper.writeValueAsString((List<Chat>)userList);
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
  			if(userList.get(i).getChat().length() > 50) {
  				ListMap.get(i).put("chatMold", userList.get(i).getChat().substring(0, 50));
  			}
  		}
  		//System.out.println("test1:" + ListMap);
		return ListMap;
  	}

}
