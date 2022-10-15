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
@Table(name = "出版社")
public class 出版社Dto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "出版社ID")
	private Long 出版社ID;
	@Column(name = "出版社名")
	private String 出版社名;

}
