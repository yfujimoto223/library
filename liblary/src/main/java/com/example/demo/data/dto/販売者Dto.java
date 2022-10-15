package com.example.demo.data.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "販売者")
public class 販売者Dto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "販売者ID")
	private Long 販売者ID;
    @Column(name = "販売者番号")
	private Long 販売者番号;
    @Column(name = "名前")
	private String 名前;
    @Column(name = "ふりがな")
	private String ふりがな;
    @Column(name = "住所")
	private String 住所;
    @Column(name = "郵便番号")
	private String 郵便番号;
    @Column(name = "EMAIL")
	private String EMAIL;
    @Column(name = "パスワード")
	private String パスワード;
}
