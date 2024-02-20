<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="/resources/js/board.js"></script>
</head>
<body>
	<div id="wrap">
		<div>
			<table border="1px">
				<tr>
					<th>번호</th>
					<td>${requestScope.boardDetail.board_id}</td>
					<th>작성자</th>
					<td>${requestScope.boardDetail.useremail}</td>
					<th>등록일</th>
					<td>${requestScope.boardDetail.board_regdate}</td>
				</tr>
				<tr>
					<th>글제목</th>
					<td colspan="3">${requestScope.boardDetail.board_title}</td>
					<th>조회수</th>
					<td>${requestScope.boardDetail.board_views}</td>
				</tr>
				<tr>
					<td colspan="6">${requestScope.boardDetail.board_content}</td>
				</tr>
			</table>
		</div>
		<c:if test="${not empty sessionScope.loginUser.useremail}">
			<div id="likes">
				<button onclick="boardLikes(${requestScope.boardDetail.board_id})">❤️</button>
				<span id="likeCount">${requestScope.boardDetail.board_likes}</span>
				<span id="likeStatus">좋아요</span>
			</div>
		</c:if>
		<div>
			<c:if test="${requestScope.boardDetail.useremail == sessionScope.loginUser.useremail }">
				<a href="/board/${requestScope.boardDetail.board_id}?jCode=U">수정하기</a>
				<button onclick="boardDelete(${requestScope.boardDetail.board_id})">삭제하기</button>
			</c:if>
		</div>
		<hr>
		<a href="/board/">전체목록</a>
	</div>
</body>
</html>