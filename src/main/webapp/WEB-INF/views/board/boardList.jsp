<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티 게시판</title>
<script>
	history.replaceState({}, null, location.pathname);
</script>
<link rel="stylesheet" href="/resources/css/board.css">
</head>
<body>
<div id="wrap">
   <div class="topSection">
   		<div class="searchBox">
			<form action="board/" method="get">
			    <select name="searchType" id="searchType" onchange="keywordClear()">
			        <option value="all" ${requestScope.searchType == 'all' ? "selected" : "" }>전체</option>
			        <option value="useremail" ${requestScope.searchType == 'useremail' ? "selected" : "" }>글쓴이</option>
			        <option value="board_title" ${requestScope.searchType == 'board_title' ? "selected" : "" }>글제목</option>
			        <option value="board_content" ${requestScope.searchType == 'board_content' ? "selected" : "" }>글내용</option>
			    </select>
			    <input type="text" name="keyword" id="keyword" placeholder="검색어를 입력하세요." value="${requestScope.keyword}" />
			    <button type="submit" id="searchBtn">Search</button>
			</form>
		</div>
   		<div>
   			<a href="boardInsert">새글등록</a>
   		</div>
   </div>
   <div>
   		<table>
   			<tr>
	   			<th>글번호</th>
	   			<th>제목</th>
	   			<th>작성자</th>
	   			<th>작성일</th>
	   			<th>조회수</th>
	   			<th>좋아요</th>
   			</tr>
   			<c:if test="${not empty requestScope.boardList}">
   				<c:forEach var="b" items="${requestScope.boardList}">
   					<tr>
	   					<td>${b.board_id}</td>
	   					<td><a href="/board/${b.board_id}">${b.board_title}</a></td>
	   					<td>${b.useremail }</td>
	   					<td>${b.board_regdate}</td>
	   					<td>${b.board_views}</td>
	   					<td>${b.board_likes}</td>
   					</tr>
   				</c:forEach>
   			</c:if>
   		</table>
   </div>
   <c:if test="${not empty requestScope.boardList}">
	   <div class="pageNation">
	        <c:choose>
	             <c:when test="${resultDTO.start != resultDTO.page}">
	                  <a class ="firstB" href="/board/?page=${resultDTO.start}&searchType=${searchType}&keyword=${keyword}">처음</a>
	                  <a class ="ltB" href="/board/?page=${resultDTO.page-1}&searchType=${searchType}&keyword=${keyword}">&LT;</a>
	             </c:when>
	             <c:otherwise>
	                  <span class ="firstB">처음</span>
	                  <span class ="ltB">&LT;</span>
	             </c:otherwise>
	         </c:choose>     
	              
	         <c:forEach var="i" items="${resultDTO.pageList}">
	             <c:if test="${i==resultDTO.page}">
	                 <span class="selectPage">${i}</span>&nbsp;
	             </c:if>
	             <c:if test="${i!=resultDTO.page}">
	                 <a href="/board/?page=${i}&searchType=${searchType}&keyword=${keyword}">${i}</a>&nbsp;
	             </c:if>
	         </c:forEach>
	                
	         <c:choose>
	             <c:when test="${resultDTO.end != resultDTO.page}">
	                 <a class="gtB" href="/board/?page=${resultDTO.page+1}&searchType=${searchType}&keyword=${keyword}">&GT;</a>
	                 <a class="lastB" href="/board/?page=${resultDTO.end}&searchType=${searchType}&keyword=${keyword}">마지막</a>
	             </c:when>
	             <c:otherwise>
	                 <span class="gtB">&GT;</span>
	                 <span class="lastB">마지막</span>
	             </c:otherwise>
	         </c:choose>
	    </div>
    </c:if>
</div>
</body>
</html>