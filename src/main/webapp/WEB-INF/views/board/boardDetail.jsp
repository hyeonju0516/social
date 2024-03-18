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
					<td>${requestScope.boardDetail.useremail}
						<input type="hidden" id="username" value="${sessionScope.loginUser.username }"/>
					</td>
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
		<div class="board_bar">
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
		</div>
		<div id="comments">
			<div class="commentInsert">
				<form action="commentInsert" method="post">
					<p>${sessionScope.loginUser.useremail} &#40;${sessionScope.loginUser.username}&#41;</p>
					<input type="hidden" id="board_id" name="board_id" value="${requestScope.boardDetail.board_id }" />
					<input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail }" />
					<textarea id="comment_content" name="comment_content" placeholder="댓글을 입력해 주세요." maxlength="1000" required ></textarea>
					<button>등록</button>
				</form>
			</div>
			<div id="commentList">
	            <ul>   
					<c:if test="${not empty requestScope.commentList}">
		                <c:forEach var="c" items="${requestScope.commentList}">		                
							<li style="margin-left: ${c.comment_indent*5}%;">
	                        	<div>
	                                <p>${c.useremail}</p>
	                                <span>${c.comment_regdate}</span>
                                </div>
	                           	<div>
	                                <p class="comment-content">${c.comment_content}</p>
	                                <textarea class="edit-comment" style="display: none;">${c.comment_content}</textarea>
                                </div>
                                <div>
                                	<a id="toggle-reply" onclick="toggleReply(${c.comment_id})">답글달기</a>
	                                <c:if test="${sessionScope.loginUser.useremail == c.useremail}">
	                                    <div>
	                                        <button data-idx="${c.comment_id}" class="edit-btn" onclick="commentUpdate(this)">수정</button>
	                                        <button class="del-btn" type="button" data-idx="${c.comment_id}" onclick="commentDelete(${c.comment_id})">삭제</button>
		                                </div>
	                                </c:if>
                                </div>
                                <div class="commentInsert" id="reply-${c.comment_id}" style="display: none;">
                                	<form action="ReplyInsert" method="post">
										<p>${sessionScope.loginUser.useremail} &#40;${sessionScope.loginUser.username}&#41;</p>
										<input type="hidden" id="board_id" name="board_id" value="${c.board_id}" />
										<input type="hidden" id="useremail" name="useremail" value="${sessionScope.loginUser.useremail}" />
										<input type="hidden" id="comment_root" name="comment_root" value="${c.comment_id}" />
										<input type="hidden" id="comment_steps" name="comment_steps" value="${c.comment_steps}" />
										<input type="hidden" id="comment_indent" name="comment_indent" value="${c.comment_indent}" />
										<textarea id="comment_content" name="comment_content" placeholder="댓글을 입력해 주세요." maxlength="1000" required ></textarea>
										<button>등록</button>
										<button type="button" onclick="cancleComment(${c.comment_id})">취소</button>
									</form>
								</div>
	                        </li>
		                </c:forEach>
		        	</c:if>
	            </ul>
		
		        <c:if test="${empty requestScope.commentList}">
		            <div>작성된 댓글이 없습니다.</div>
		        </c:if>

			</div>
			<c:if test="${not empty requestScope.commentList}">
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
		    </c:if>
		</div>
		<hr>
		<a href="/board/">전체목록</a>
	</div>
</body>
</html>