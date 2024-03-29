<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 글 등록</title>
<link rel="stylesheet" href="/resources/css/board.css">
</head>
<body>
	<div id="wrap">
		<div class="board_save board_insert">
			<form action="boardInsert" method="post">
				<table>
					<tr>
						<th>글쓴이</th>
						<td>${sessionScope.loginUser.useremail}
							<input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail}">
						</td>
					</tr>
					<tr>
						<th>글제목</th>
						<td><input type="text" id="board_title" name="board_title" maxlength="30" required></td>
					</tr>
					<tr>
						<th>글 내용</th>
						<td><textarea id="board_content" name="board_content" maxlength="1000" required></textarea></td>
					</tr>
				</table>
				<div>
					<a href="javascript:history.back()">이전페이지</a>
					<div>
						<button type="reset">초기화</button>
						<button type="submit">등록</button>
					</div>
				</div>
			</form>
		</div>
		<div>
			<c:if test="${not empty requestScope.message}">			
				<p>${requestScope.message}</p>
			</c:if>
		</div>
	</div>
</body>
</html>