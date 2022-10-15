package com.example.demo.data.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "リクエスト書籍")
public class リクエスト書籍Dto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "リクエストID")
	private Long リクエストID;
	@Column(name = "予約者ID")
	private Long 予約者ID;
	@Column(name = "ISBN", unique = true)
	private Long ISBN;
	@Column(name = "書名")
	private String 書名;
	@Column(name = "著者")
	private String 著者;
	@Column(name = "価格")
	private Long 価格;
	@Column(name = "出版社名")
	private String 出版社名;
	@Column(name = "出版年")
	private Date 出版年;
	@Column(name = "リクエスト日")
	private Date リクエスト日;
}
