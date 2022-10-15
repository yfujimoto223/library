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
@Table(name = "管理者から利用者へのメッセージ")
public class 管理者から利用者へのメッセージDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "メッセージID")
	private Long メッセージID;
	@Column(name = "管理者ID")
	private Long 管理者ID;
	@Column(name = "予約者ID")
	private Long 予約者ID;
	@Column(name = "内容")
	private String 内容;
	@Column(name = "登録日時")
	@LastModifiedDate
	private Date 登録日時;
}
