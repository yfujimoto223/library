package com.example.demo.data.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.注文Dto;
import com.example.demo.data.repository.注文Repository;

@Service
@Transactional(rollbackFor = Exception.class)
public class 注文DtoService {
	@Autowired
	private 注文Repository 注文Repository;

	/** 注文IDを検索キーとして注文DBを検索**/
	public Optional<注文Dto> findOne(Long 注文ID) {
		return 注文Repository.findById(注文ID);
	}

	/** 管理者IDを検索キーとして注文DBを検索**/
	public List<注文Dto> findOneBy管理者Id(Long 管理者ID) {
		return 注文Repository.findAll().stream().filter(a -> a.get管理者ID().equals(管理者ID)).collect(Collectors.toList());
	}

	/**全データを抽出**/
	public List<注文Dto> findAll() {
		return 注文Repository.findAll();

	}

	/**DBへ保存**/
	public 注文Dto save(注文Dto 注文Dto) {
		return 注文Repository.save(注文Dto);
	}

	/**DBを更新**/
	public 注文Dto update(注文Dto 注文Dto) {
		return 注文Repository.save(注文Dto);
	}

	/**DBを削除**/
	public void delete(注文Dto 注文Dto) {
		注文Repository.delete(注文Dto);
	}

}
