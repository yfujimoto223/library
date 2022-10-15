package com.example.demo.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.予約者Dto;
import com.example.demo.data.repository.予約者Repository;

@Service
@Transactional(rollbackFor = Exception.class)
public class 予約者DtoService {
	@Autowired
	private 予約者Repository 予約者Repository;

	/**予約者IDを検索キーとして予約者DBを検索**/
	public Optional<予約者Dto> findOne(Long 予約者ID) {
		return 予約者Repository.findById(予約者ID);

	}

	/**貸出券番号を検索キーとして予約者DBを検索**/
	public Optional<予約者Dto> findOneBy貸出券番号(Long 貸出券番号) {
		return 予約者Repository.findAll().stream().filter(a -> a.get貸出券番号().equals(貸出券番号)).findFirst();

	}

	/**予約者DTOを検索キーとして予約者DBを検索**/
	public Optional<予約者Dto> findOne(予約者Dto 予約者Dto) {
		return 予約者Repository.findById(予約者Dto.get予約者ID());

	}

	/**予約者DTOの全データを検索**/
	public List<予約者Dto> findAll() {
		return 予約者Repository.findAll();

	}

	/**貸出券番号、EMAIL、パスワードを検索キーとして予約者DBを検索**/
	public 予約者Dto findBy貸出券番号_EMAIL_パスワード(予約者Dto 予約者Dto) {
		予約者Dto result = 予約者Repository.findAll().stream().filter(a -> a.get貸出券番号().equals(予約者Dto.get貸出券番号())
				&& a.getEMAIL().equals(予約者Dto.getEMAIL())
				&& a.getパスワード().equals(予約者Dto.getパスワード())).findFirst().orElse(null);

		return result;

	}

	public 予約者Dto save(予約者Dto 予約者Dto) {
		return 予約者Repository.save(予約者Dto);
	}

	public 予約者Dto update(予約者Dto 予約者Dto) {
		return 予約者Repository.save(予約者Dto);
	}

}
