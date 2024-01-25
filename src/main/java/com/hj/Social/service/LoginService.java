package com.hj.Social.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hj.Social.domain.OauthId;
import com.hj.Social.entity.User;
import com.hj.Social.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	
	private final UserRepository repository;

	public String getAccessToken(String code, String gate) {

		try {
			
			String redirectURI="";
			String apiURL="";
			
			if("naver".equals(gate)) {
				redirectURI = URLEncoder.encode("http://localhost:8080/social/nlogin", "UTF-8");
				apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
				apiURL += "&client_id=" + "CbugVe_4UtXLAGCPR_KK";
				apiURL += "&client_secret=" + "x5yB3pEFQS";
				apiURL += "&redirect_uri=" + "http://localhost:8080/social/nlogin";
				apiURL += "&code=" + code;
				apiURL += "&gate=" + gate;
			}else if("kakao".equals(gate)) {
				redirectURI = URLEncoder.encode("http://localhost:8080/social/klogin", "UTF-8");
				apiURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code";
				apiURL += "&client_id=0091e3579906d8421181b9f2d8d7657e";
				apiURL += "&redirect_uri=http://localhost:8080/social/klogin";
				apiURL += "&client_secret=4HnX7dcLmeCLUddzAWlVKwcQu3jRYB3x";
				apiURL += "&code=" + code;
				apiURL += "&gate=" + gate;
			}

			System.out.println("apiURL=" + apiURL);
			
			
			
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			
			
			if (responseCode == 200) { // 정상 호출
			    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
			    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			    return null;
			}

			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}

			if (responseCode == 200) {
				System.out.println(res.toString());
			}
			
			String result = res.toString(); // 변경: 읽은 내용을 String으로 변환
			System.out.println("response body : " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			br.close();

			return element.getAsJsonObject().get("access_token").getAsString();
		} catch (Exception e) {
			System.out.println("getAccessToken Exception"+ e);
			
			return null;
		}

	}
	
	public User getUserInfo(String accessToken, String gate) throws IOException {

		// 네이버 로그인 접근 토큰;
		String apiURL="";
		
		if("naver".equals(gate)) {
			apiURL = "https://openapi.naver.com/v1/nid/me";
		}else if("kakao".equals(gate)) {
			apiURL = "https://kapi.kakao.com/v2/user/me";
		}
		String headerStr = "Bearer " + accessToken; // Bearer 다음에 공백 추가
		String result = requestToServer(apiURL, headerStr);
		System.out.println("사용자 정보 " + result);
		
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(result);
		
//		JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();
//		System.out.println("*****response: " + response);
		
		
		String token_id = "";
		String nickname = "";
		String email = "";
		
		if("naver".equals(gate)) {
			token_id = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
			nickname = element.getAsJsonObject().get("response").getAsJsonObject().get("name").getAsString();
			email = element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();
		}else if("kakao".equals(gate)) {
			token_id = element.getAsJsonObject().get("id").getAsString();
			nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
			email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
		}
		
		
		System.out.println("token_id" +  token_id);
		System.out.println("nickname" +  nickname);
		System.out.println("email" +  email);

		Optional<User> opt_user = repository.findById(new OauthId(gate, token_id));
		System.out.println("--------opt_user : " + opt_user);
		
		User user = new User();
		if (opt_user.isPresent()) {
			return opt_user.orElse(null);
		}else {
			user.setUseremail(email);
			user.setUsername(nickname);
			user.setOauthtype(gate);
			user.setOauthtoken(token_id);
			repository.save(user);
			
			return user;
		}

	}


	private String requestToServer(String apiURL, String headerStr) throws IOException {
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		System.out.println("header Str: " + headerStr);
		
		if (headerStr != null && !headerStr.equals("")) {
			con.setRequestProperty("Authorization", headerStr);
		}
		
		int responseCode = con.getResponseCode();
		
		BufferedReader br;
		System.out.println("responseCode=" + responseCode);
		
		if (responseCode == 200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else { // 에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		
		String inputLine;
		StringBuffer res = new StringBuffer();
		
		while ((inputLine = br.readLine()) != null) {
			res.append(inputLine);
		}
		br.close();
		if (responseCode == 200) {
			return res.toString();
		} else {
			return null;
		}
	}
}
