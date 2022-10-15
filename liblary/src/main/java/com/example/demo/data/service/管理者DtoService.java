package com.example.demo.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.管理者Dto;
import com.example.demo.data.repository.管理者Repository;

@Service
@Transactional(rollbackFor = Exception.class)
public class 管理者DtoService {
	@Autowired
	private 管理者Repository 管理者Repository;

	/**管理者IDを検索キーとして管理者DBを検索**/
	public Optional<管理者Dto> findOne(Long 管理者ID) {
		return 管理者Repository.findById(管理者ID);

	}

	/**管理者DBの全データを取得
	 * @return **/
	public List<管理者Dto> findAll() {
		return 管理者Repository.findAll();

	}

	/**管理者番号を検索キーとして管理者DBを検索**/
	public Optional<管理者Dto> findOneBy管理者番号(Long 管理者番号) {
		return 管理者Repository.findAll().stream().filter(a -> a.get管理者番号().equals(管理者番号)).findFirst();

	}

	/**管理者DTOを検索キーとして管理者DBを検索**/
	public Optional<管理者Dto> findOne(管理者Dto 管理者Dto) {
		return 管理者Repository.findById(管理者Dto.get管理者ID());

	}

	/**管理者番号、EMAIL、パスワードを検索キーとして管理者DBを検索**/
	public 管理者Dto findBy管理者番号_EMAIL_パスワード(管理者Dto 管理者Dto) {
		管理者Dto result = 管理者Repository.findAll().stream().filter(a -> a.get管理者番号().equals(管理者Dto.get管理者番号())
				&& a.getEMAIL().equals(管理者Dto.getEMAIL())
				&& a.getパスワード().equals(管理者Dto.getパスワード())).findFirst().orElse(null);

		return result;

	}

	public 管理者Dto save(管理者Dto 管理者Dto) {
		return 管理者Repository.save(管理者Dto);
	}

	public 管理者Dto update(管理者Dto 管理者Dto) {
		return 管理者Repository.save(管理者Dto);
	}

}
