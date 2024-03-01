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
<link rel="stylesheet" href="/resources/css/board.css">
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
					<td colspan="6" id="board_content">${requestScope.boardDetail.board_content}</td>
				</tr>
			</table>
		</div>
		<c:if test="${not empty sessionScope.loginUser.useremail}">
			<div id="likes">
				<button onclick="boardLikes(${requestScope.boardDetail.board_id},'${sessionScope.loginUser.useremail}')">❤️</button>
				<span id="likeCount">${requestScope.boardDetail.board_likes}</span>
				<span id="likeStatus">
					${requestScope.likeStatus}
				</span>
			</div>
		</c:if>
		<div>
			<c:if test="${requestScope.boardDetail.useremail == sessionScope.loginUser.useremail }">
				<a href="/board/${requestScope.boardDetail.board_id}?jCode=U">수정하기</a>
				<button onclick="boardDelete(${requestScope.boardDetail.board_id})">삭제하기</button>
			</c:if>
		</div>
		<div id="comments">
			<div>
				<form action="commentInsert" method="post">
					<div>
						<span>댓글작성</span>
						<div>
							<span>${sessionScope.loginUser.useremail }</span>
							<input type="hidden" id="board_id" name="board_id" value="${requestScope.boardDetail.board_id }" />
							<input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail }" />
							<input type="text" id="comment_content" name="comment_content" placeholder="댓글을 입력해 주세요." maxlength="1000" required />
							<button>등록</button>
						</div>
					</div>
				</form>
			</div>
			<div id="commentList">
				<table>
					<tr>
						<th>번호</th>
						<th>작성자</th>
						<th>댓글내용</th>
						<th>등록일</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>

					<c:if test="${not empty requestScope.commentList}">
						<c:forEach var="c" items="${requestScope.commentList}">

							<tr>
								<td>${c.comment_id}</td>
								<td>${c.useremail}</td>
								<td><span class="comment-content">${c.comment_content}</span>
									<input type="text" class="edit-comment" style="display: none;"
									value="${c.comment_content}"></td>
								<td>${c.comment_regdate}</td>
								<c:if test="${sessionScope.loginUser.useremail == c.useremail}">
									<td>
										<button data-idx="${c.comment_id}" class="edit-btn">수정</button>
									</td>
									<td>
										<button type="button" data-idx="${c.comment_id}" onclick="commentDelete(${c.comment_id})">삭제</button>
									</td>
								</c:if>
							</tr>

						</c:forEach>
					</c:if>

					<c:if test="${empty requestScope.commentList}">
						<tr>
							<td colspan="6">작성된 댓글이 없습니다.</td>
						</tr>
					</c:if>
				</table>
			</div>
		    <div class="pageNation">
                 <a id="firstB" class="selectArrow" onclick="loadItems(${resultDTO.start},${requestScope.boardDetail.board_id})">처음</a>
                 <input type="hidden" id="templtB" value="${resultDTO.page-1}">
                 <a id="ltB" class="selectArrow" onclick="loadItems(getInputValue('templtB'),${requestScope.boardDetail.board_id})">&LT;</a>
		              
		         <c:forEach var="i" items="${resultDTO.pageList}">
	                 <a class="pageValue ${i==resultDTO.page ? 'selectPage' : null}" onclick="loadItems(${i},${requestScope.boardDetail.board_id})">${i}</a>&nbsp;
		         </c:forEach>
		                
                 <input type="hidden" id="tempgtB" value="${resultDTO.page+1}">
                 <a id="gtB" onclick="loadItems(getInputValue('tempgtB'), ${requestScope.boardDetail.board_id})">&GT;</a>
                 <a id="lastB" onclick="loadItems(${resultDTO.end},${requestScope.boardDetail.board_id})">마지막</a>
		    </div>
		</div>
		<hr>
		<a href="/board/">전체목록</a>
	</div>
</body>
</html>