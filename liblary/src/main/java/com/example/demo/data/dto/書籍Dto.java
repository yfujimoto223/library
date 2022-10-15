package com.example.demo.data.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "書籍")
public class 書籍Dto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "書籍ID")
	private Long 書籍ID;
	@Column(name = "書名")
	private String 書名;
	@Column(name = "著者")
	private String 著者;
	@Column(name = "価格")
	private Long 価格;
	@Column(name = "出版社ID ")
	private Long 出版社ID;
	@Column(name = "出版年")
	private Date 出版年;
	@Column(name = "登録日時")
	@LastModifiedDate
	private Date 登録日時;

}
