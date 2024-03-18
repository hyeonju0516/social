<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<link rel="stylesheet" href="/resources/css/board.css">
</head>
<body>
	<div id="wrap">
		<div class="board_save board_update">
			<form action="/board/${requestScope.boardDetail.board_id}" method="post">
				<table>
					<tr>
						<th>번호</th>
						<td>${requestScope.boardDetail.board_id}
							<input type="hidden" id="board_id" name="board_id" value="${requestScope.boardDetail.board_id}"/>
							<input type="hidden" id="board_moddate" name="board_moddate" value="${requestScope.boardDetail.board_moddate}" />
							<!-- <input type="hidden" id="board_deldate" name="board_deldate" value="${requestScope.boardDetail.board_deldate}" /> -->
							<input type="hidden" id="board_delyn" name="board_delyn" value="${requestScope.boardDetail.board_delyn}" />
							<input type="hidden" id="board_likes" name="board_likes" value="${requestScope.boardDetail.board_likes}" />
						</td>
						<th>작성자</th>
						<td>${requestScope.boardDetail.useremail}
							<input type="hidden" id="useremail" name="useremail" value="${requestScope.boardDetail.useremail}" />
						</td>
						<th>등록일</th>
						<td>${requestScope.boardDetail.board_regdate}
							<input type="hidden" id="board_regdate" name="board_regdate" value="${requestScope.boardDetail.board_regdate}" />
						</td>
					</tr>
					<tr>
						<th>글제목</th>
						<td colspan="5">
							<input type="text" id="board_title" name="board_title" value="${requestScope.boardDetail.board_title}" />
						</td>
					</tr>
					<tr>
						<td colspan="6"><textarea id="board_content" name="board_content">${requestScope.boardDetail.board_content}</textarea></td>
					</tr>
				</table>
				<div>
					<button type="reset">초기화</button>
					<button type="submit">수정</button>
				</div>
			</form>
		</div>
		<div>
			<c:if test="${not empty requestScope.message}">
				${requestScope.message}
			</c:if>
		</div>
	</div>
</body>
</html>