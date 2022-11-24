package com.example.demo.form;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;

import com.example.demo.entity.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatForm {

	private Integer id;
	
	@NotBlank
	private String chat;
	
	private String userId;
	
	//宛先
	private String selectUser;
	
	private String userName;
	
	private String updateTime;
	
	private String createTime;
	
	//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
	private String operCategory;
	
	//現在ページ
	private String pageNo;
	
	//現在ページ
	private String dispIndex;
	
	//削除選択ID
	private String selectdeleData;
	
	//選択状態フラグ
	private String allSelect;
	
	//参照ID
	private String referenceSelect;
	
	//検索項目
	private String searchEntry;
	
	//検索状態
	private String searchStatus;
}
