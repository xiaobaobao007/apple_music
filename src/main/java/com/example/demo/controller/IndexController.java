package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

	@RequestMapping("/login")
	public String login() {
//		  BCryptPasswordEncoder b=new BCryptPasswordEncoder();
//		  System.out.println(b.encode("0"));
//		 
		return "login";
	}

	@RequestMapping("/regist")
	public String regist() {

		return "regist";
	}

	@RequestMapping(value = "/admin")
	public String adminPage() {
		return "redirect:/getStusByPage?pageIndex=1&pageSize=5";
	}

	@RequestMapping(value = "/super")
	public String superPage() {
		return "redirect:/getStusByPage?pageIndex=1&pageSize=5";
	}

	@RequestMapping(value = "/vip")
	public String vipPage() {
		return "redirect:/getStusByPage?pageIndex=1&pageSize=5";
	}

	@RequestMapping(value = "/other")
	public String otherPage() {
		return "redirect:/getStusByPage?pageIndex=1&pageSize=5";
	}


	@RequestMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		// Authentication是一个接口，表示用户认证信息
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// 如果用户认知信息不为空，注销
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		// 重定向到login页面
		return "redirect:/login";
	}
}
