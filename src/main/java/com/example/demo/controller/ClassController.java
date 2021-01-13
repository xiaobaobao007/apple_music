package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.entity.Classs;
import com.example.demo.service.ClassService;

@Controller
public class ClassController {

	@Resource
	private ClassService classService;
	
	@GetMapping("/findAllClass")
	public String findAllclasss(WebRequest webRequest,Model model) {
		
		List<Classs> classs=classService.findAll();
		webRequest.setAttribute("classs",classs,RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success","欢迎！！欢迎！！");
		return "admin/ClassInfo";
	}
	
	@PostMapping("/saveClass")
	public String saveClass(@ModelAttribute Classs cl,WebRequest webRequest,Model model){
		
		if(classService.findByClassname(cl.getClassname())!=null){
			model.addAttribute("faild", "所上传的分类名称已存在！！！，请重新输入");
			return "admin/ClassInfo";
		}
		int id=classService.nullfirst();
		cl.setCode(id);
		classService.save(cl);
		List<Classs> classs=classService.findAll();
		webRequest.setAttribute("classs",classs,RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success", "上传成功");
		return "admin/ClassInfo";
	}
	
	@PostMapping("/updateClass")
	public String updateClass(@ModelAttribute Classs cl,WebRequest webRequest,Model model){
		
		classService.updateClass(cl);
		List<Classs> classs=classService.findAll();
		webRequest.setAttribute("classs",classs,RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success", "更新成功");
		return "admin/ClassInfo";
	}

	@GetMapping("/deleteClass")
	public String deleteByCode(int code,WebRequest webRequest,Model model){
		
		classService.deleteByCode(code);
		List<Classs> classs=classService.findAll();
		webRequest.setAttribute("classs",classs,RequestAttributes.SCOPE_SESSION);
		model.addAttribute("success","您选择编号："+code+",已经被成功删除。");
		return "admin/ClassInfo";
	}
}