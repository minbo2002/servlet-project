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
	<%-- UpdateBoardController에 의해 아래와 같이 Model 받는다
			request.setAttribute("boardData", boardData);
			request.setAttribute("no", no);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("rowSize", rowSize);
	--%>
	*내용 : ${boardData} <br/><br/>
	*세션 : ${authUser} <br/>
	*글번호no : ${no} <br/>
	*보고싶은 페이지 pageNo : ${pageNo} <br/>
	*페이지당 글개수 rowSize : ${rowSize} <br/>
	
	<a href="<%=request.getContextPath()%>/index.jsp">HOME</a>

	<h2>updateBoard.jsp</h2>
    <hr/>
    
    <form name="updateFrm" id="updateFrm" method="post" action="<%=request.getContextPath()%>/recomboard/update.do" enctype="multipart/form-data">
    
    	<input type="hidden" name="rNo" id="rNo" value="${boardData.recomBoard.rNo}"/>
    
	    <table border="1" style="margin: auto;">
	    	<tr>
	    		<th>작성자 아이디</th>
	    		<td>${boardData.recomBoard.member.mId}</td>
	    	</tr>
	    	<tr>
	    		<th>게시판 제목</th>
	    		<td>
	    			<input type="text" name="rtitle" id="rtitle" value="${boardData.recomBoard.rTitle}" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>책제목</th>
	    		<td>
	    			<input type="text" name="bookTitle" id="bookTitle" value="${boardData.recomBoard.bookTitle}" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>저자</th>
	    		<td>
	    			<input type="text" name="author" id="author" value="${boardData.recomBoard.author}" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>출판사</th>
	    		<td>
	    			<input type="text" name="publisher" id="publisher" value="${boardData.recomBoard.publisher}" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>책 이미지</th>
	    		<td align="center" valign="middle">
	    			<img src="../uploadImage/${boardData.recomFile.fileRealName}" style="height:300px; width:600px;">
	    			<input type="file" name="filename" id="filename" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>게시판 내용</th>
	    		<td>
	    			<textarea name="rContent" id="rContent" style="width: 600px">${boardData.recomBoard.rContent}</textarea>
	    		</td>
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
	    		<td><fmt:formatDate pattern="yyyy년 MM월 dd일 HH시:mm분:ss초" value="${boardData.recomBoard.regDate}"/></td>
	    	</tr>
	    	<tr>
	    		<th>수정일</th>
	    		<td><fmt:formatDate pattern="yyyy년 MM월 dd일 HH시:mm분:ss초" value="${boardData.recomBoard.modDate}"/></td>
	    	</tr>
	    	<tr>          
		 		<td colspan="2" style="text-align:center;">
					<input type="submit" value="수정하기"/>
		 		</td>
		 	</tr>
	    	<tr>
	    		<td colspan="2" style="text-align: center;">	
	   			<c:set var="pageNo" value="${ (empty param.pageNo) ? '1' : param.pageNo }"></c:set>	  <!-- parameter인 pageNo가 비어있다면 pageNo=1  pageNo이 비어있지않다면 해당 pageNo값을 가져간다  -->
	  				<a href="/recomboard/listboard.do?pageNo=${pageNo}&rowSize=${rowSize}">목록보기</a>
	   				<a href="/recomboard/delete.do?no=${boardData.recomBoard.rNo}">글 삭제(delete용)</a>   		
	    		</td>
	    	</tr>
	    </table>
	    
	</form>
	
</body>
</html>