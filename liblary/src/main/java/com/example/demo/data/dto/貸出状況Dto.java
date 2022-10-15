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
@Table(name = "貸出状況")
public class 貸出状況Dto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "予約ID")
	private Long 予約ID;
	@Column(name = "予約者ID")
	private Long 予約者ID;
	@Column(name = "書籍ID")
	private Long 書籍ID;
	@Column(name = "申込日")
	private Date 申込日;
	@Column(name = "返却予定日")
	private Date 返却予定日;
	@Column(name = "貸出ステータス")
	private Long 貸出ステータス;

}
