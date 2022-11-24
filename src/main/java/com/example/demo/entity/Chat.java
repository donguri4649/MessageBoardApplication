package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chat")
public class Chat {
	
	@Id
	private Integer id;
	
	@Column(value = "address")
	private String address;
	@Column(value = "chat")
	private String chat;
	@Column(value = "userid")
	private String userId;
	@Column(value = "username")
	private String userName;
	@Column(value = "updatetime")
	private String updateTime;
	@Column(value = "createtime")
	private String createTime;
	
}
