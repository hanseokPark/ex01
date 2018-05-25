package com.dgit.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;
import com.dgit.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService service;
	
	@RequestMapping(value="/login", method= RequestMethod.GET)
	public void loginGet(){
		logger.info("login GET.................................");
		
		
		
	}// /user/login
	
	@RequestMapping(value="/loginPost", method= RequestMethod.POST)
	public void loginPost(UserVO vo, Model model) throws Exception{
		logger.info("login GET.................................");
		logger.info(vo.toString());
		
		vo = service.login(vo);		
		if(vo == null){
			return;
		}
		
		LoginDTO loginDto = new LoginDTO();
		loginDto.setUid(vo.getUid());
		loginDto.setUname(vo.getUname());
		
		model.addAttribute("loginDTO", loginDto);
		
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session){
		logger.info("logout ..........................");
		
		
		session.invalidate(); //session bye
		
		return "redirect:/";
	}
	
	
}

























