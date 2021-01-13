package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.Utils.FileUtil;
import com.example.demo.entity.FKRole;
import com.example.demo.entity.FKUser;
import com.example.demo.reponsitory.RoleRepository;
import com.example.demo.reponsitory.UsRepository;
import com.example.demo.service.UsService;

import javax.annotation.Resource;

@Controller
public class UserController {

	//	@Resource
//	private UserService userService;
	@Resource
	private FileUtil fileUtil;

	@Resource
	private UsRepository usRepository;
	@Resource
	private UsService userService;
	@Resource
	private RoleRepository roleRepository;


	@PostMapping("/uploadUs")
	// @ModelAttribute 表单提交的信息与类进行匹配
	public String uploadUser(@ModelAttribute FKUser user) throws Exception {

		if (usRepository.findByLoginName(user.getLoginName()) != null) {
			return "redirect:/regist?error";
		} else {
			List<FKRole> roles = new ArrayList<FKRole>();
			FKRole role = roleRepository.findByAuthority("ROLE_VIP");
			roles.add(role);
			user.setRoles(roles);
			user.setDownloadnums(10);
			BCryptPasswordEncoder b = new BCryptPasswordEncoder();
			user.setPassword(b.encode(user.getPassword()));
			userService.save(user);
			return "login";
		}
	}

	@PostMapping("/updateUs")
	// @ModelAttribute 表单提交的信息与类进行匹配
	public String updateUser(@ModelAttribute FKUser user, @ModelAttribute FKRole r, WebRequest webRequest, Model model) throws Exception {

		List<FKRole> roles = new ArrayList<FKRole>();
		FKRole role = roleRepository.findByAuthority(r.getAuthority());
		roles.add(role);
		user.setRoles(roles);
		if (user.getPassword().length() <= 50) {
			BCryptPasswordEncoder password = new BCryptPasswordEncoder();
			user.setPassword(password.encode(user.getPassword()));
		}
		usRepository.save(user);

		Page<FKUser> users = userService.findAll(0, 5);
		webRequest.setAttribute("Objects", users, RequestAttributes.SCOPE_SESSION);
		roles = roleRepository.findAll();
		webRequest.setAttribute("roles", roles, RequestAttributes.SCOPE_SESSION);

		model.addAttribute("success", "更新成功");
		return "admin/UserInfo";
	}


	@GetMapping("/findAlluserInfo")
	public String userinfo(WebRequest webRequest, Model model, int pageIndex, int pageSize) {
//		List<FKUser> users=userService.findAll();
		Page<FKUser> users = userService.findAll(pageIndex - 1, pageSize);
		webRequest.setAttribute("Objects", users, RequestAttributes.SCOPE_SESSION);
		List<FKRole> roles = roleRepository.findAll();
		webRequest.setAttribute("roles", roles, RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success", "欢迎！！欢迎！！");
		return "admin/UserInfo";
	}

	@GetMapping("/deleteUserById")
	public String deleteUserById(int id, WebRequest webRequest, Model model, int pageIndex, int pageSize) {
		userService.deleteById(id);

		Page<FKUser> users = userService.findAll(pageIndex - 1, pageSize);
		webRequest.setAttribute("Objects", users, RequestAttributes.SCOPE_SESSION);
		List<FKRole> roles = roleRepository.findAll();
		webRequest.setAttribute("roles", roles, RequestAttributes.SCOPE_SESSION);

		model.addAttribute("success", "删除成功");
		return "admin/UserInfo";
	}

	@GetMapping("/finduserInfobyname")
	public String userinfo2(WebRequest webRequest, Model model, String name, int pageIndex, int pageSize) {
		Page<FKUser> users = userService.findByLoginname(name, pageIndex - 1, pageSize);
		webRequest.setAttribute("Objects", users, RequestAttributes.SCOPE_SESSION);
		List<FKRole> roles = roleRepository.findAll();
		webRequest.setAttribute("roles", roles, RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success", "欢迎！！欢迎！！");
		return "admin/UserInfo";
	}
}