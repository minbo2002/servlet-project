<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="description" content="member board Web Application">
	<meta name="keywords" content="member, board, article, mvc">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>게시판 목록</title>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	
	<style></style>
	<script type="text/javascript">
	$(document).ready(function () {
		//게시물출력수 선택
		//<select name="rowSize" id="rowSize">에 change 이벤트
		$("#rowSize").change(function(){

		   $("#rowSizeFrm").submit();
		});
	});
	</script>
	 
</head>
<body>
	<%-- ListArticleHandler 컨트롤러에 의해 아래와 같이 Model 받는다
		 request.setAttribute("boardPage", boardPage); 
		 request.setAttribute("rowSize", rowSize);
	--%>
	*세션 : ${authUser} <br/>
	*pageNo : ${pageNo} <br/>
	*rowSize : ${rowSize} <br/>
	
	<a href="<%=request.getContextPath()%>/index.jsp">HOME</a>
	<h2>추천게시판 목록페이지</h2>
    
    <p>
    <form name="rowSizeFrm" id="rowSizeFrm" action="<%=request.getContextPath()%>/recomboard/listboard.do" method="get">
        게시물수 :
		<select name="rowSize" id="rowSize">
			<option value="3">선택</option>
			<option value="3">3</option>
			<option value="5">5</option>
			<option value="10">10</option>
		</select>
    </form>
    </p>
    
    <c:if test="${not empty authUser}">
    <p> 
   		<a href="/recomboard/write.do?rowSize=${rowSize}">글쓰기</a>
	</p>
	</c:if>
    
    <table border="1" style="width: 600px;">
    	<thead>
    		<tr>
    			<th>번호</th>
    			<th>제목</th>
    			<th>작성자 아이디</th>
    			<th>작성일</th>
    			<th>조회수</th>
    			<th>좋아요</th>
    		</tr>
    	</thead>
    	<tbody>
	    	<%-- 게시글이 없는 경우 --%>
	    	<c:if test="${boardPage.hasNoBoards()}">
	    	<tr>
	    		<td colspan="6" style="text-align: center;">게시글이 없습니다</td>
	    	</tr>
	    	</c:if>

			<%-- 게시글이 있는 경우 --%>
			<c:if test="${boardPage.hasBoards()}">
	    	<c:forEach var="item" items="${boardPage.listAll}">
	    	<tr> 
	   			<td>${item.rNo}</td>
	   			<td><a href="/recomboard/read.do?no=${item.rNo}&pageNo=${boardPage.currentPage}&rowSize=${rowSize}">${item.rTitle}</a></td>
	   			<td>${item.member.mId}</td>
	   			<td>
			        <fmt:formatDate pattern="yyyy.MM.dd. HH:mm:ss" value="${item.regDate}" />
	   			</td>
	   			<td>${item.rCnt}</td>
	   			<td>${item.likeIt}</td>
	   		</tr>	
	    	</c:forEach>
			</c:if>
	   
	   		<%--반복문 이용해서 출력 --%>
	   		
	   		<%-- paging 처리 --%>
	   		<tr>
	    		<td colspan="6" style="text-align: center;">
	    			<%-- JSTL if조건문 : 이전출력 --%>
	    			<c:if test="${boardPage.startPage > 5}">
	    				<a href="/recomboard/listboard.do?pageNo=${boardPage.startPage-5}&rowSize=${rowSize}">prev</a>
	    			</c:if>
	    			
	    			<%-- JSTL forEach조건문 : 페이지번호출력 --%>
	    			<c:forEach var="pNo" begin="${boardPage.startPage}" end="${boardPage.endPage}">
	    				<a href="/recomboard/listboard.do?pageNo=${pNo}&rowSize=${rowSize}">${pNo}</a>
	    			</c:forEach>
	    			
	    			<%-- JSTL if조건문 : 다음출력 --%>
	    			<c:if test="${boardPage.endPage < boardPage.totalPages}">
	    				<a href="/recomboard/listboard.do?pageNo=${boardPage.startPage+5}&rowSize=${rowSize}">next</a>
	    			</c:if>
	    		</td>
	    	</tr>
    	</tbody>
    </table>

</body>
</html>