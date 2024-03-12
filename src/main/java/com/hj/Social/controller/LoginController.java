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
	

	@GetMapping("/klogin")
	public String klogin(@RequestParam("code") String code, HttpSession session) {
		
		String access_token = socialService.getAccessToken(code, "kakao");

		try {
			User userInfo = socialService.getUserInfo(access_token,"kakao");
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
	
	@GetMapping("/nlogin")
	public String nlogin(@RequestParam("code") String code, Model model, HttpSession session) {
		System.out.println(code);

		String access_token = socialService.getAccessToken(code, "naver");

		try {
			User userInfo = socialService.getUserInfo(access_token,"naver");
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
	
	@GetMapping("/glogin")
	public String glogin(@RequestParam("code") String code, Model model, HttpSession session) {
		System.out.println("코드정보"+code);

		String access_token = socialService.getAccessToken(code, "google");

		try {
			User userInfo = socialService.getUserInfo(access_token,"google");
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