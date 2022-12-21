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
	
	/***********管理室****************/

	//管理室フラグ
	private String officeFlag;
	
	//送信フラグ
	private String submitFlg;
	
	//切替flag
	private String kirikaeFlag;
	
	/**********問い合わせ************/
	
	//メールアドレス
	private String inquiryemail;
	
	//電話番号
	private String inquiryphone;
	
	//当い合わせないよう
	private String inquircontent;
	
	//更新ソート
	private String updateSortTime;
	//作成ソート
	private String createSortTime;
}
