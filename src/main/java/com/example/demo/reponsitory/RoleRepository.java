package com.example.demo.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FKRole;
import com.example.demo.entity.FKUser;

public interface RoleRepository extends JpaRepository<FKRole,Long>{

	FKRole findByAuthority(String authority);

}
