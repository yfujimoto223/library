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
@Table(name = "注文明細")
public class 注文明細Dto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "注文明細ID")
	private Long 注文明細ID;
	@Column(name = "注文ID")
	private Long 注文ID;
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
}
