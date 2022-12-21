package com.example.demo.login;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Chat;
import com.example.demo.entity.Logintb;
import com.example.demo.form.ChatForm;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ChatService;
import com.example.demo.service.ChatServiceImpl;
import com.example.demo.service.LoginServise;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LoginController {
	
	@Autowired
	ChatService service;
	
	@Autowired
	LoginServise userService;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	ChatServiceImpl ChatService;
	
	@Autowired
	UserRepository userRepository;
	
	//初期表示
	@RequestMapping("/")
    public String ChatShow (@ModelAttribute ChatForm form, Model model ,Logintb logintb) {

		// SecurityContextHolderからAuthenticationオブジェクトを取得
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		//System.out.println("test1:" + userService.getUserInfo(authentication.getName()).getUserid());
		String userName = authentication.getName();
		String userId = userService.getUserInfo(userName).getUserid();

		String today = today();
		form.setUserName(userName);
		form.setUserId(userId);
		
		model.addAttribute("creator",userName);
		model.addAttribute("today",today);
		model.addAttribute("userId",userId);
		
		information(model, form, null, userName, userId);
        return "crud";
    }
	
	//送信,更新処理,ページング,削除
	@PostMapping("/main")
    public String ChatShowSousin (@ModelAttribute ChatForm form, Model model,RedirectAttributes redirect) {
		
		if(("1").equals(form.getKirikaeFlag())) {
			model.addAttribute("kirikaeFlag", form.getKirikaeFlag());
			System.out.println("test1:" + userRepository.accountUserId(form.getUserName()).get(0).get("userid"));
			form.setUserId((String) userRepository.accountUserId(form.getUserName()).get(0).get("userid"));
			System.out.println("test2:" + form.getUserName());
		}
		
		String userId = form.getUserId();
		String userName = form.getUserName();
		String today = today();
		
		model.addAttribute("creator",userName);
		model.addAttribute("today",today);
		model.addAttribute("userId",userId);
		//System.out.println("test1:" + form.getAllSelect());
		model.addAttribute("allSelect", form.getAllSelect());
		
		//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
		String operCategory= form.getOperCategory();
		switch(operCategory){
		case "1":
			Chat chat = new Chat();
			chat.setAddress(form.getSelectUser());
			chat.setChat(form.getChat());
			chat.setUserId(userId);
			chat.setUserName(userName);
			chat.setUpdateTime("");
			chat.setCreateTime(today);
			service.insertChat(chat);
			redirect.addFlashAttribute("complete", "登録が完了しました");
			//model.addAttribute("complete","登録が完了しました");
			//information(model, form, redirect);
			return "redirect:/";
		case "3":
			String select = form.getSelectdeleData();
			System.out.println(select);
			String[] selects = select.split(",");
			for(int i = 0;i < selects.length;i++) {
				service.deleteChatById(Integer.parseInt(selects[i]));
			}
			model.addAttribute("complete","削除が完了しました");
			//information(model, form, redirect);
			//return "redirect:/";
			break;
		}
		
		information(model, form, redirect, userName, userId);
		return "crud";
    }
	
	@RequestMapping("/getAutoComplete")
	@ResponseBody
    public String getAutoComplete () {
		//宛先
		List<String> Userlist = new ArrayList<String>();
		for(int i = 0;i < userRepository.accountUsername().size();i++) {
			Userlist.add((String) userRepository.accountUsername().get(i).get("username"));
		}
		//System.out.println("test2:" + Userlist);
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(Userlist);
		} catch (JsonProcessingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		//System.out.println("test3:" + json);
		return json;
    }
	
	@GetMapping("/login")
    public String login () {
        return "login";
    }
	
	@GetMapping("/signup")
    public String newSignup(Logintb logintb) {
        return "signup";
    }
	
	@PostMapping("/signup")
    public String signup(Logintb logintb, Model model) {
		//System.out.println("test1:" + logintb.getUsername());
		//System.out.println("test2:" + passwordEncoder.encode(logintb.getPassword()));
		
		if(("").equals(logintb.getUsername()) && ("").equals(logintb.getPassword())) {
			model.addAttribute("signupError", "ユーザー名とpasswordを入力してください。");
            return "signup";
		}
		
		if(("").equals(logintb.getUsername())) {
			model.addAttribute("signupError", "ユーザー名を入力してください。");
            return "signup";
		}
		
		if(("").equals(logintb.getPassword())) {
			model.addAttribute("signupError", "passwordを入力してください。");
            return "signup";
		}
		
		if(logintb.getPassword().length() < 5) {
			model.addAttribute("signupError", "passwordは5文字以上、10以下で入力してください。");
            return "signup";
		}
		
		if(userService.isExistUser(logintb.getUsername())) {
			model.addAttribute("signupError", "ユーザー名 " + logintb.getUsername() + "は既に登録されています");
	        return "signup";
		}
		
        try {
        	userService.register(logintb.getUsername(), passwordEncoder.encode(logintb.getPassword()), today());
        } catch (DataAccessException e) {
            model.addAttribute("signupError", "ユーザー登録に失敗しました");
            return "signup";
        }
        return "redirect:/";
    }    
	
	//持ち回り情報
	private void information(Model model, ChatForm form, RedirectAttributes redirect, String janitorUserName ,String janitorUserId) {
		List<Map<String,Object>> list = null;
		
		if(("1").equals(form.getSearchStatus())) {
			list = ChatService.searchChat(form,model,"1");
			model.addAttribute("searchStatus","1");
			model.addAttribute("searchEntry",form.getSearchEntry());
			model.addAttribute("title", "検索した伝言");
		}else if(("2").equals(form.getAllSelect())) {
			list = ChatService.searchChat(form,model,"2");
			model.addAttribute("title", "私宛の伝言");
		}else if(("0").equals(form.getAllSelect())) {
			list = ChatService.searchChat(form,model,"3");
			model.addAttribute("title", "私の伝言");
		}else {
			list = ChatService.searchChat(form,model,"0");
			model.addAttribute("title", "すべての伝言");
		}
		model.addAttribute("createSort","0");
		model.addAttribute("updateSort","0");
		//System.out.println("test1" + form.getCreateSortTime());
		if(!("").equals(form.getCreateSortTime()) && form.getCreateSortTime() != null) {
			//作成日 "0":デフォルト　"1":降順  "2":昇順
			model.addAttribute("createSortTime",form.getCreateSortTime());
			model.addAttribute("createSort",form.getCreateSortTime());
		}
		if(!("").equals(form.getUpdateSortTime()) && form.getUpdateSortTime() != null) {
			//更新日　"0":デフォルト　"1":降順  "2":昇順
			model.addAttribute("updateSortTime",form.getUpdateSortTime());
			model.addAttribute("updateSort",form.getUpdateSortTime());
		}
		//全件数
		int totalCount = 0;
		if(!("").equals(list) && list != null) {
			totalCount = list.size();
		}
		
		//現在ページ 初期表示時は1
		String dispIndex = "1";
		if(form.getDispIndex() != null) {
			dispIndex = form.getDispIndex();
		}
		int intdispIndex = Integer.parseInt(dispIndex);
		int maxPage = (((totalCount - 1)/20) + 1);
		
		String pageLinkCategry = "";
		int keisan = 0;
		if(intdispIndex < maxPage) {
			pageLinkCategry = String.valueOf(((intdispIndex) - 1)*20 + 1) + " - " + String.valueOf(intdispIndex*20);
			keisan = intdispIndex*20;
		}else {
			pageLinkCategry = String.valueOf(((intdispIndex) - 1)*20 + 1) + " - " + String.valueOf(totalCount);
			keisan = totalCount;
		}
		
		ArrayList<Map<String,Object>> pageList = new ArrayList<Map<String,Object>>();
		if(maxPage >= 1) {
			for(int i = 0;i < maxPage; i++) {
				HashMap<String, Object> pageMap = new HashMap<String, Object>();
				pageMap.put("address_page",i + 1);
				pageList.add(pageMap);
			}
		}
		
		ArrayList<Map<String,Object>> tumekaelist = new ArrayList<Map<String,Object>>();
		if(list != null) {
			if(list.size() > 20) {
				for(int i = (((intdispIndex) - 1)*20);i < keisan;i++) {
					if((list.get(i).get("userId")).equals(form.getUserId()) &&
							(list.get(i).get("userName")).equals(form.getUserName())) {
						list.get(i).put("active","1");
					}else {
						list.get(i).put("active","0");
					}
					tumekaelist.add(list.get(i));
				}
				model.addAttribute("list", tumekaelist);
			}else {
				for(int i = 0;i < list.size();i++) {
					if((list.get(i).get("userId")).equals(form.getUserId()) &&
							(list.get(i).get("userName")).equals(form.getUserName())) {
						list.get(i).put("active","1");
					}else {
						list.get(i).put("active","0");
					}
				}
				model.addAttribute("list", list);
			}
		}
		
		//System.out.println(intdispIndex);
		model.addAttribute("pageLinkCategry",pageLinkCategry);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("pageList",pageList);
		model.addAttribute("dispIndex",intdispIndex);
		model.addAttribute("maxPage",maxPage);
		model.addAttribute("pages",intdispIndex);
		
		//宛先
		ArrayList<HashMap<String,Object>> Userlist = new ArrayList<HashMap<String,Object>>();
		for(int i = 0;i < userRepository.accountUsername().size();i++) {
			HashMap<String,Object> UserlistMap = new HashMap<String,Object>();
			//System.out.println(userRepository.accountUsername().get(i).get("username"));
			UserlistMap.put("UserlistMap",userRepository.accountUsername().get(i).get("username"));
			Userlist.add(UserlistMap);
		}
		model.addAttribute("Userlist",Userlist);
		//System.out.println(Userlist);
		
		model.addAttribute("janitorUserName",janitorUserName);
		model.addAttribute("janitorUserId",janitorUserId);
		//System.out.println(userRepository.janitorOfficeAccountSetting(form,"1").get(0).get("authority"));
		if(!("1").equals(form.getKirikaeFlag())) {
			model.addAttribute("janitorOfficeFlag",userRepository.janitorOfficeAccountSetting(form,"1").get(0).get("authority"));
		}else {
			model.addAttribute("janitorOfficeFlag","MANAGER");
		}
	}

  //今日の日付
  	private String today() {
  		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
  		LocalDateTime dt2 = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
          //System.out.println("yyyy/MM/dd HH:mm:ss-> "+dtf.format(LocalDateTime.now()));
  		return dt2.format(dtf);
  	}
  	
  	//List<Chat>のキャスト
  	private List<Map<String,Object>> itiranList(){
  		List<Chat> list = (List<Chat>) service.selectAll();
  		//System.out.println("test1:" + list);
  		ObjectMapper mapper = new ObjectMapper();
  		String json = null;
  		//Javaオブジェクト→json
  		try {
			json = mapper.writeValueAsString((List<Chat>)list);
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
  		
  		for(int i = 0;i < list.size();i++) {
  			if(list.get(i).getChat().length() > 50) {
  				ListMap.get(i).put("chatMold", list.get(i).getChat().substring(0, 50));
  			}
  			//ListMap.get(i).put("chatMold", list.get(i).getChat());
  		}
  		//System.out.println("test1:" + ListMap);
		return ListMap;
  		
  	}
}
