package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Chat;

public interface ChatRepository extends CrudRepository<Chat, Integer> {

}
