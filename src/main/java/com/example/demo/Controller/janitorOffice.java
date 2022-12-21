package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Chat;
import com.example.demo.form.ChatForm;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ChatServiceImpl;
import com.example.demo.service.InquiryService;

@Controller
public class janitorOffice {
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	ChatServiceImpl ChatService;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	InquiryService inquiry;

	//初期表示
	@PostMapping("/janitorOffice")
	public String janitorOfficeMethod (@ModelAttribute ChatForm form, Model model) {

		//System.out.println("test1:" + userRepository.janitorOfficeAccountSetting(form, "0"));

		//System.out.println(inquiry.inquirySelect(form).get(0));
		model.addAttribute("accountList",userRepository.janitorOfficeAccountSetting(form, "0"));
		model.addAttribute("janitorList",inquiry.inquirySelect(form));
		model.addAttribute("janitorUserName",form.getUserName());
		model.addAttribute("janitorUserId",form.getUserId());

		return "sample2";
	}
	
	//管理人室ログインパスワード
	@PostMapping("/janitor")
	@ResponseBody
	public String passwordJanitor (@RequestParam String note) {
		//System.out.println("test1:" + note);
		String[] passwordStr = note.split(",");
		String password = null;
		
		if(("1").equals(passwordStr[1])) {
			password = "%u958B%u3051%u30B4%u30DE";
		}else if(("2").equals(passwordStr[1])) {
			password = "%u30E6%u30FC%u30B6%u30FC%u5207%u66FF";
		}
		return password;
	}

}
