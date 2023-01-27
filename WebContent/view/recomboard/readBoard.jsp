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
	<title></title>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	
	<style>
	
	</style>
	
	<script>

	</script>
	 
</head>
<body>
	<%-- ReadBoardController에서 아래와 같은 Model을 받는다
		 
		request.setAttribute("boardData", boardData);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("rowSize", rowSize);
	--%>
	*내용 : ${boardData} <br/><br/>
	*요청페이지 : ${pageNo}  <br/><br/>
	*1페이지당 글갯수 : ${rowSize}  <br/><br/>
	*세션 : ${authUser} <br/><br/>

	<a href="<%=request.getContextPath()%>/index.jsp">HOME</a>

	<h2>readArticle.jsp</h2>
    <hr/>
    <table border="1" style="height:200px; width: 600px; margin: auto;">
    	<tr>
    		<th>작성자 아이디</th>
    		<td>${boardData.recomBoard.member.mId}</td>
    	</tr>
    	<tr>
    		<th>게시판 제목</th>
    		<td>${boardData.recomBoard.rTitle}</td>
    	</tr>
    	<tr>
    		<th>책제목</th>
    		<td>${boardData.recomBoard.rContent}</td>
    	</tr>
    	<tr>
    		<th>저자</th>
    		<td>${boardData.recomBoard.author}</td>
    	</tr>
    	<tr>
    		<th>출판사</th>
    		<td>${boardData.recomBoard.publisher}</td>
    	</tr>
    	<tr>
    		<th>책 이미지</th>
    		<td><img src="../uploadImage/${boardData.recomFile.fileRealName}"></td>
    	</tr>
    	<tr>
    		<th>게시판 내용</th>
    		<td>${boardData.recomBoard.rContent}</td>
    	</tr>
    	<tr>
    		<th>좋아요</th>
    		<td>${boardData.recomBoard.likeIt}</td>
    	</tr>
    	<tr>
    		<th>조회수</th>
    		<td>${boardData.recomBoard.rCnt}</td>
    	</tr>
    	<tr>
    		<th>작성일</th>
    		<td><fmt:formatDate pattern="yyyy년 MM월 dd일" value="${boardData.recomBoard.regDate}"/></td>
    	</tr>
    	<tr>
    		<th>수정일</th>
    		<td><fmt:formatDate pattern="yyyy년 MM월 dd일" value="${boardData.recomBoard.modDate}"/></td>
    	</tr>
    	<tr>
    		<td colspan="2" style="text-align: center;">	
   			<c:set var="pgNo" value="${ (empty param.pageNo) ? '1' : param.pageNo }"></c:set>	  <!-- parameter인 pageNo가 비어있다면 pageNo=1  pageNo이 비어있지않다면 해당 pageNo값을 가져간다  -->
  				<a href="/article/listArticle.do?pageNo=${pgNo}&rowSize=${rowSize}">목록보기</a>
   			
   			<c:if test="${AUTHUSER.memberid==articleData.article.writer.writer_id}">
    			<a href="/article/modify.do?no=${articleData.article.number}&pageNo=${pgNo}&rowSize=${rowSize}">글 수정</a>
    			<a href="/article/deleteArticle.do?no=${articleData.article.number}">글 삭제(delete용)</a>
    			<a href="/article/deleteArticle2.do?no=${articleData.article.number}">글 삭제(update용)</a>    			
   			</c:if>   		
    		</td>
    	</tr>
    </table>
	

</body>
</html>