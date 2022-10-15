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
@Table(name = "注文")
public class 注文Dto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "注文ID")
	private Long 注文ID;
	@Column(name = "管理者ID")
	private Long 管理者ID;
	@Column(name = "注文日")
	private Date 注文日;
	@Column(name = "注文金額")
	private Long 注文金額;
}
