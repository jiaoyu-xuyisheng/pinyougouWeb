package com.pinyougou.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping("/name")
	public Map name() {
		//在SecurityContext控制器中得到name
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Map<String, String> map=new HashMap();
		map.put("loginName", name);
		return map;	
	}

}
