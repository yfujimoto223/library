package com.example.demo.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.注文明細Dto;
import com.example.demo.data.repository.注文明細Repository;

@Service
@Transactional(rollbackFor = Exception.class)
public class 注文明細DtoService {
	@Autowired
	private 注文明細Repository 注文明細Repository;

	/** 注文明細IDを検索キーとして注文明細DBを検索**/
	public Optional<注文明細Dto> findOne(Long 注文明細ID) {
		return 注文明細Repository.findById(注文明細ID);
	}

	/** ISBNを検索キーとして注文明細DBを検索**/
	public Optional<注文明細Dto> findOneByIsbn(Long ISBN) {
		return 注文明細Repository.findAll().stream().filter(a -> a.getISBN().equals(ISBN)).findFirst();
	}

	/**全データを抽出**/
	public List<注文明細Dto> findAll() {
		return 注文明細Repository.findAll();

	}

	/**DBへ保存**/
	public 注文明細Dto save(注文明細Dto 注文明細Dto) {
		return 注文明細Repository.save(注文明細Dto);
	}

	/**DBを更新**/
	public 注文明細Dto update(注文明細Dto 注文明細Dto) {
		return 注文明細Repository.save(注文明細Dto);
	}

	/**DBを削除**/
	public void delete(注文明細Dto 注文明細Dto) {
		注文明細Repository.delete(注文明細Dto);
	}

}
