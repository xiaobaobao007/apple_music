package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FKRole;
import com.example.demo.entity.FKUser;
import com.example.demo.reponsitory.UsRepository;


/**
 * 需要实现UserDetailsService接口
 * 因为在Spring Security中配置的相关参数需要是UserDetailsService类型的数据
 * */
@Service
public class UsService implements UserDetailsService{

	// 注入持久层接口UserRepository
	@Autowired
    UsRepository userRepository;
	
	/*
	 *  重写UserDetailsService接口中的loadUserByUsername方法，通过该方法查询到对应的用户 
	 *  返回对象UserDetails是Spring Security中一个核心的接口。
	 *  其中定义了一些可以获取用户名、密码、权限等与认证相关的信息的方法。
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 调用持久层接口findByLoginName方法查找用户，此处的传进来的参数实际是loginName
		List<GrantedAuthority> authorities = new ArrayList<>();
		
//		if(username=="") {
//			authorities.add(new SimpleGrantedAuthority("ROLE_OTHER"));
//			return new User("4","0",authorities);
//		}
		FKUser fkUser = userRepository.findByLoginName(username);
        if (fkUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 创建List集合，用来保存用户权限，GrantedAuthority对象代表赋予给当前用户的权限
        // 获得当前用户角色集合
        List<FKRole> roles = fkUser.getRoles();
        // 获得当前用户权限集合
        System.out.println(roles.get(0).getAuthority());
        for (FKRole role : roles) {
        	// 将关联对象Role的authority属性保存为用户的认证权限
        	authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        // 此处返回的是org.springframework.security.core.userdetails.User类，该类是Spring Security内部的实现
        return new User(fkUser.getUsername(), fkUser.getPassword(), authorities);
	}
	
	@Transactional
	public List<FKUser> findAll() {

		return userRepository.findAll();
	}
	
	public Page<FKUser> findAll(int pageIndex , int pageSize) {

		Sort sort = new Sort(Sort.Direction.ASC, "id");
		Pageable page = PageRequest.of(pageIndex, pageSize, sort);
		return userRepository.findAll(page);
	}
	public Page<FKUser> findByLoginname(String LoginName,int pageIndex , int pageSize) {

		Sort sort = new Sort(Sort.Direction.ASC, "id");
		Pageable page = PageRequest.of(pageIndex, pageSize, sort);
		return userRepository.findByLoginName(LoginName, page);
	}
	@Transactional
	public void deleteById(int id) {

		userRepository.deleteById(id);
	}
	@Transactional
	public void save(FKUser user) {

		user.setId(nulfirstnum());
		userRepository.save(user);
	}
	@Transactional
	public void updateOne(FKUser user) {
		
		FKUser u=userRepository.findById(user.getId());
		u.setUsername(user.getUsername());
		u.setLoginName(user.getLoginName());
		u.setPassword(user.getPassword());
		u.setRoles(user.getRoles());
		userRepository.saveAndFlush(u);
	}
	
	public int nulfirstnum(){
		long nums = userRepository.count();
		for (int i = 1; i <= (int) nums; i++) {
			if (userRepository.findById(i)==null) {
				return i;
			}
		}
		return (int) (nums + 1);
	}
	

}
