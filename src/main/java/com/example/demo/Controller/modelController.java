package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Chat;
import com.example.demo.form.ChatForm;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ChatService;

@Controller
public class modelController {
	
	@Autowired
	ChatService service;
	
	@Autowired
	UserRepository userRepository;
	
		//初期表示
		@PostMapping("/modal")
	    public String ChatShow (@ModelAttribute ChatForm form, Model model) {
		
			//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)
			String operCategory= form.getOperCategory();
			//参照、更新ID
			String referenceSelect = form.getReferenceSelect();
			//選択したリスト
			Chat reference = service.selectOneById(Integer.parseInt(referenceSelect)).get();
			//id
			Integer id = reference.getId();
			//address
			String address = reference.getAddress();
			//chat
			String chat = reference.getChat();
			//userId
			String userId = reference.getUserId();
			//userName
			String userName = reference.getUserName();
			//updateTime
			String updateTime = reference.getUpdateTime();
			//createTime
			String createTime = reference.getCreateTime();
			
			//宛先
			ArrayList<HashMap<String,Object>> Userlist = new ArrayList<HashMap<String,Object>>();
			for(int i = 0;i < userRepository.accountUsername().size();i++) {
				HashMap<String,Object> UserlistMap = new HashMap<String,Object>();
				//System.out.println(userRepository.accountUsername().get(i).get("username"));
				UserlistMap.put("UserlistMap",userRepository.accountUsername().get(i).get("username"));
				Userlist.add(UserlistMap);
			}
			model.addAttribute("Userlist",Userlist);
			
			//現在日時
			String today = today();
			
			if(("4").equals(operCategory)) {
				System.out.println(reference);
				model.addAttribute("title","参照モード");
				model.addAttribute("chat",chat);
				model.addAttribute("today",createTime);
				model.addAttribute("creator",userName);
				model.addAttribute("dispUser",address);
				model.addAttribute("operCategory","4");
				
			}else if(("5").equals(operCategory)) {
				model.addAttribute("title","編集モード");
				model.addAttribute("chat",chat);
				model.addAttribute("today",createTime);
				model.addAttribute("creator",userName);
				model.addAttribute("dispUser",address);
				model.addAttribute("id",id);
				model.addAttribute("userId",userId);
				model.addAttribute("operCategory","5");
			}else if(("55").equals(operCategory)) {
				Chat chatupdate = new Chat();
				System.out.println(form.getChat());
				chatupdate.setId(id);
				chatupdate.setChat(form.getChat());
				chatupdate.setUserId(userId);
				chatupdate.setAddress(form.getSelectUser());
				chatupdate.setUserName(userName);
				chatupdate.setUpdateTime(today);
				chatupdate.setCreateTime(createTime);
				System.out.println(chatupdate);
				service.updateChat(chatupdate);
				model.addAttribute("updateFlg","1");
			}
			
	        return "sample1";
	    }

		//今日の日付
	  	private String today() {
	  		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	          //System.out.println("yyyy/MM/dd HH:mm:ss-> "+dtf.format(LocalDateTime.now()));
	  		return dtf.format(LocalDateTime.now());
	  	}

}
