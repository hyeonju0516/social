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
			<div>
				<table border="1px">
					<tbody>
							<tr>
								<th>닉네임</th>
								<th>리뷰내용</th>
								<th>작성일</th>
							</tr>
							<c:forEach items="${requestScope.commentList}" var="c">
								<tr>
									<td><input type="hidden" id="comment_id" value="${c.comment_id}">
									${c.useremail}</td>
									<td><input class="comment_content"  type="text" value="${c.comment_content}"  autofocus disabled></td>
									<td>
										<c:if test="${loginUser.useremail eq c.useremail }">
												<input type="button" data-idx="${c.comment_id}" value="수정">
												<input type="button" data-idx="${c.comment_id}" value="삭제" >
										</c:if>
										<br><label>${c.comment_regdate}</label>
									</td>
								</tr>
							</c:forEach>
					</tbody>
				</table>
				<div class="pageNation">
			        <c:choose>
			             <c:when test="${resultDTO.start != resultDTO.page}">
			                  <a class ="firstB" href="/board/${requestScope.boardDetail.board_id}?page=${resultDTO.start}">처음</a>
			                  <a class ="ltB" href="/board/${requestScope.boardDetail.board_id}?page=${resultDTO.page-1}">&LT;</a>
			             </c:when>
			             <c:otherwise>
			                  <span class ="firstB">처음</span>
			                  <span class ="ltB">&LT;</span>
			             </c:otherwise>
			         </c:choose>     
			              
			         <c:forEach var="i" items="${resultDTO.pageList}">
			             <c:if test="${i==resultDTO.page}">
			                 <span><strong>${i}</strong></span>&nbsp;
			             </c:if>
			             <c:if test="${i!=resultDTO.page}">
			                 <a href="/board/${requestScope.boardDetail.board_id}?page=${i}">${i}</a>&nbsp;
			             </c:if>
			         </c:forEach>
			                
			         <c:choose>
			             <c:when test="${resultDTO.end != resultDTO.page}">
			                 <a class="gtB" href="/board/${requestScope.boardDetail.board_id}?page=${resultDTO.page+1}">&GT;</a>
			                 <a class="lastB" href="/board/${requestScope.boardDetail.board_id}?page=${resultDTO.end}">마지막</a>
			             </c:when>
			             <c:otherwise>
			                 <span class="gtB">&GT;</span>
			                 <span class="lastB">마지막</span>
			             </c:otherwise>
			         </c:choose>
			    </div>
			</div>
		</div>
		<hr>
		<a href="/board/">전체목록</a>
	</div>
</body>
</html>