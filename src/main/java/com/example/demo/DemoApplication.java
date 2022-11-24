package com.example.demo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.GrantedAuthority;

import com.example.demo.entity.Chat;
import com.example.demo.entity.Logintb;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args)
		.getBean(DemoApplication.class).execute();
	}
	
	@Autowired
	ChatRepository repositort;
	
	//@Autowired
	//LogintbRepository loginrepository;
	
	//@Autowired
	//ChatService service;
	
	private void execute() {
		//executeInsert();
		executeSelect();
		//Select();
		//updateChat();
		//deletChat();
	}
	
	//登録
	private void executeInsert() {
		//Chat chat = new Chat(null,"あああああ","bbb","ccc","aaa","ああ");
		//service.insertChat(chat);		
		//chat = repositort.save(chat);
		Collection<GrantedAuthority> authorities = null;
		
		//Logintb logintb = new Logintb("donguri2","12345678","");
		//logintb = loginrepository.save(logintb);
	}
	
	//全件取得
	private void executeSelect() {
		//System.out.println(service.selectAll());
		//System.out.println(repositort.findAll());
		//Logintb user = loginrepository.findByUsername("donguri");
		//System.out.println(user);
		//Iterable<Logintb> menbers = loginrepository.findAll();
		//System.out.println(menbers);
		ObjectMapper mapper = new ObjectMapper();
  		String json = null;
		//Javaオブジェクト→json
  		/*try {
			//json = mapper.writeValueAsString((Iterable<Logintb>)menbers);
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
		//Iterable<Chat> menbers = service.selectAll();
		/*for(Chat chat : menbers) {
			System.out.println(chat);
		}*/
	}
	
	//１件取得
		private void Select() {
			//System.out.println(loginrepository.findByUsername("donguri"));
			
			/*Optional<Chat> chatOpt = service.selectOneById(1);
			
			if(chatOpt.isPresent()) {
				System.out.println(chatOpt.get());
			}else {
				System.out.println("データなし");
			}*/
		}
	
	//更新
	private void updateChat() {
		//Chat chat = new Chat(1,"aaa111","bbb","ccc");
		//service.updateChat(chat);
		//chat = repositort.save(chat);
	}
	
	//削除
	private void deletChat() {
		//service.deleteChatById(4);
		//service.deleteChatById(5);
		
		/*for(int i = 5; i > 17;i++) {
			repositort.deleteById(i);
		}*/
		//repositort.deleteById(17);
		//repositort.deleteById(4);
		
		//loginrepository.deleteById(4);
		//loginrepository.deleteById(7);
	}

}
