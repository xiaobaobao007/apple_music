package com.example.demo.reponsitory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FKUser;

public interface UsRepository extends JpaRepository<FKUser,Long>{

	FKUser findByLoginName(String LoginName);
	FKUser findByUsername(String username);
	void deleteById(int id);
	FKUser findById(int id);
	long count();
	Page<FKUser> findAll(Pageable page);
	Page<FKUser> findByLoginName(String LoginName,Pageable page);
}
