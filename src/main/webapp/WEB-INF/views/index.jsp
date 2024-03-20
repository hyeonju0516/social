<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<link rel="stylesheet" href="/resources/css/login.css">
</head>
<body>
   <div id="wrap">
	   <!-- <h1>메인 페이지</h1> -->
	   
	   <div class="mainImg"><img src="/resources/images/main.png" alt="main"></div>
		<div>
		   <c:if test="${not empty sessionScope.loginUser}">
		   	<p> ${sessionScope.loginUser.username}님 안녕하세요!<br/>
		   		<span>${sessionScope.loginUser.oauthtype}</span>로 로그인하셨습니다.
		    </p>
		    <hr/>
		   	<a href="board/">게시판가기</a> | 
		   	<a href="social/logout">로그아웃</a>
		   </c:if>
		   
		   <c:if test="${empty sessionScope.loginUser}">
		   	<a href="social/loginPage">로그인</a>
		   </c:if>
		   
		   <a href="social/loginMaster">임시로그인</a>
	   </div>
   </div>
   
</body>
</html>