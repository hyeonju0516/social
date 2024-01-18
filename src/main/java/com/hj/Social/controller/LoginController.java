package com.hj.Social.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hj.Social.service.KakaoAPI;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "/social")
@Controller
public class LoginController {

	private KakaoAPI kakao;

	@GetMapping("/loginPage")
	public void getLoginPage() {

	}

	@GetMapping("/login")
	public String login(@RequestParam("code") String code) {
		System.out.println(code);
		String access_token = kakao.getAccessToken(code);
		System.out.println("controller access_token : " + access_token);

		String userInfo = kakao.getUserInfo(access_token);

		if ("성공".equals(userInfo)) {
			return "redirect:/home";
		} else {
			return "redirect:/social/loginPage";
		}

	}

}