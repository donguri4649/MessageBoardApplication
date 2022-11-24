package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.Chat;

public interface ChatService {

	//全権取得
	Iterable<Chat> selectAll();
	
	//1件を取得
	Optional<Chat> selectOneById(Integer id);
	
	//登録
	void insertChat(Chat chat);
	
	//更新
	void updateChat(Chat chat);
	
	//削除
	void deleteChatById(Integer id);
}
