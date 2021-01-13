package com.example.demo.exception;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.ResultJson;

@ControllerAdvice//表示当前类项目的统一异常处理
public class GlobalExceptionHandler {
	@ExceptionHandler(value=Exception.class)//异常的父类
	@ResponseBody//
	public Object error(HttpServletRequest request,Exception e) throws Exception
	{
//		Map<String,Object> map=new HashMap<>();
//		map.put("code",100);
//		map.put("message",e.getMessage());
//		map.put("url",request.getRequestURI().toString());
//		map.put("data","请求失败");
//		return map;
		ResultJson result=new ResultJson(100, e.getMessage(), "请求失败");
		return result;
	}
}