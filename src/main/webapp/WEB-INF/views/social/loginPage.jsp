<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<link rel="stylesheet" href="/resources/css/login.css">
</head>
<body>
<div id="wrap">
   <h1>소셜 로그인</h1>
   <div class="socialBox">
	   <a href="https://kauth.kakao.com/oauth/authorize?client_id=0091e3579906d8421181b9f2d8d7657e&redirect_uri=http://localhost:8080/social/klogin&response_type=code">
	      <img src="/resources/images/kakao.png" alt="kakaoLogin IMG" />
	   </a>
	   
	   <a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=CbugVe_4UtXLAGCPR_KK&redirect_uri=http://localhost:8080/social/nlogin">
	   	  <img src="/resources/images/naver.png" alt="naverLogin IMG" />
	   </a>
	   
	   <a href="https://accounts.google.com/o/oauth2/auth?client_id=995527437477-r10jn77e4tp2u0nhp7moaiusg4oo30gc.apps.googleusercontent.com&redirect_uri=http://localhost:8080/social/glogin&response_type=code&scope=email%20profile%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile%20openid&authuser=0&prompt=consent">
	   		<img src="/resources/images/google.png" alt="googleLogin IMG" />
	   </a>
   </div>
</div>
</body>
</html>