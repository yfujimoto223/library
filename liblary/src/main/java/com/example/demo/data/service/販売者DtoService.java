package com.example.demo.data.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.販売者Dto;
import com.example.demo.data.repository.販売者Repository;

@Service
@Transactional(rollbackFor = Exception.class)
public class 販売者DtoService {
	@Autowired
	private 販売者Repository 販売者Repository;

	/**販売者IDを検索キーとして販売者DBを検索**/
	public Optional<販売者Dto> findOne(Long 販売者ID) {
		return 販売者Repository.findById(販売者ID);

	}

	/**販売者番号を検索キーとして販売者DBを検索**/
	public Optional<販売者Dto> findOneBy販売者番号(Long 販売者番号) {
		return 販売者Repository.findAll().stream().filter(a -> a.get販売者番号().equals(販売者番号)).findFirst();

	}

	/**販売者DTOを検索キーとして販売者DBを検索**/
	public Optional<販売者Dto> findOne(販売者Dto 販売者Dto) {
		return 販売者Repository.findById(販売者Dto.get販売者ID());

	}

	/**販売者番号、EMAIL、パスワードを検索キーとして販売者DBを検索**/
	public 販売者Dto findBy販売者番号_EMAIL_パスワード(販売者Dto 販売者Dto) {
		販売者Dto result = 販売者Repository.findAll().stream().filter(a -> a.get販売者番号().equals(販売者Dto.get販売者番号())
				&& a.getEMAIL().equals(販売者Dto.getEMAIL())
				&& a.getパスワード().equals(販売者Dto.getパスワード())).findFirst().orElse(null);

		return result;

	}

	public 販売者Dto save(販売者Dto 販売者Dto) {
		return 販売者Repository.save(販売者Dto);
	}

	public 販売者Dto update(販売者Dto 販売者Dto) {
		return 販売者Repository.save(販売者Dto);
	}

}
