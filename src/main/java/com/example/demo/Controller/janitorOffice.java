package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Chat;
import com.example.demo.form.ChatForm;

@Controller
public class janitorOffice {
	
		//初期表示
		@PostMapping("/janitorOffice")
	    public String janitorOffice (@ModelAttribute ChatForm form, Model model) {
			
			System.out.println("test1:" + form.getUserName());
			System.out.println("test2:" + form.getUserId());
			model.addAttribute("janitorUserName",form.getUserName());
			model.addAttribute("janitorUserId",form.getUserId());
			
	        return "sample2";
	    }

}
