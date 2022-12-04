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
public class Inquiry {

	@Id
	private Integer inquiryid;
	
	@Column(value = "inquiryname")
	private String inquiryname;
	@Column(value = "inquiryemail")
	private String inquiryemail;
	@Column(value = "inquiryphone")
	private String inquiryphone;
	@Column(value = "inquircontent")
	private String inquircontent;
	@Column(value = "userid")
	private String userId;
	@Column(value = "createtime")
	private String createTime;
}
