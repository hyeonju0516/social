package com.hj.Social.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hj.Social.entity.User;
import com.hj.Social.service.SocialService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "/social")
@Controller
public class LoginController {

	private SocialService socialService;
	

	@GetMapping("/loginPage")
	public void getLoginPage() {

	}
	
	@GetMapping("/loginMaster")
	public String loginMaster(HttpSession session) {
		User loginUser = new User("마스터","","마스터", "master@email.com");
		session.setAttribute("loginUser", loginUser);
		return "redirect:/";
	}
	

	@GetMapping("/login")
	public String login(@RequestParam("code") String code, @RequestParam("type") String type, HttpSession session) {
		
		String access_token = socialService.getAccessToken(code, type);

		try {
			User userInfo = socialService.getUserInfo(access_token,type);
			
			System.out.println("*****************"+userInfo);
			if (userInfo != null) {
				session.setAttribute("loginUser", userInfo);
				return "redirect:/index";
			} else {
				return "redirect:/social/loginPage";
			}
			
		} catch (Exception e) {
			return "redirect:/social/loginPage";
		}

	}
	
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/index";
	}

}