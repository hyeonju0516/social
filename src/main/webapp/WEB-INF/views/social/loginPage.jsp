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
   <a href="https://kauth.kakao.com/oauth/authorize?client_id=0091e3579906d8421181b9f2d8d7657e&redirect_uri=http://localhost:8080/social/klogin&response_type=code">
      <img src="/resources/images/kakao.png" alt="kakaoLogin IMG" />
   </a>
   <a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=CbugVe_4UtXLAGCPR_KK&redirect_uri=http://localhost:8080/social/nlogin">
   	  <img src="/resources/images/naver.png" alt="naverLogin IMG" />
   </a>
   <a href="">google 로그인</a>
</div>
</body>
</html>