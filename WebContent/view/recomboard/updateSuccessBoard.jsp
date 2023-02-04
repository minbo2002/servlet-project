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
	$(function() {
		
		$("#btnList").click(function() {

			location.href="/recomboard/listboard.do?pageNo="+${pageNo}+"&rowSize="+${rowSize};

		});
		
		$("#btnDelete").click(function(no) {
			
			no = ${updateReq.rNo};
			
			if(confirm("삭제하시겠습니까?")) {
				location.href = "/recomboard/delete.do?no="+no;
			}
		});
	});
	</script>
	 
</head>
<body>
	<%-- ReadBoardController에서 아래와 같은 Model을 받는다
		 
		request.setAttribute("updateReq", updateReq);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("rowSize", rowSize);
		request.setAttribute("no", no);
	--%>
	
	*변경내용 : ${updateReq} <br/><br/>
	*세션 : ${authUser} <br/>
	*글번호no : ${no} <br/>
	*보고싶은 페이지 pageNo : ${pageNo} <br/>
	*페이지당 글개수 rowSize : ${rowSize} <br/>

	<a href="<%=request.getContextPath()%>/index.jsp">HOME</a>

	<h2>updateSuccessBoard.jsp (수정 성공후 게시판)</h2>
    <hr/>
    
    <table border="1" style="margin: auto;">
    	<tr>
    		<th>작성자 아이디</th>
    		<td>${updateReq.user.mId}</td>
    	</tr>
    	<tr>
    		<th>게시판 제목</th>
    		<td>${updateReq.rTitle}</td>
    	</tr>
    	<tr>
    		<th>책제목</th>
    		<td>${updateReq.bookTitle}</td>
    	</tr>
    	<tr>
    		<th>저자</th>
    		<td>${updateReq.author}</td>
    	</tr>
    	<tr>
    		<th>출판사</th>
    		<td>${updateReq.publisher}</td>
    	</tr>
    	<tr>
    		<th>책 이미지</th>
    		<td align="center" valign="middle">
    			<img src="../uploadImage/${updateReq.recomFile.fileRealName}" style="height:300px; width:600px;" alt="이미지를 등록하세요">
    		</td>
    	</tr>
    	<tr>
    		<th>게시판 내용</th>
    		<td>
    			<textarea style="width: 600px" readonly="readonly">${updateReq.rContent}</textarea>
    		</td>
    	</tr>
    	<tr>
    		<th>좋아요</th>
    		<td>${updateReq.likeIt}</td>
    	</tr>
    	<tr>
    		<th>조회수</th>
    		<td>${updateReq.rCnt}</td>
    	</tr>
    	<tr>
    		<th>작성일</th>
    		<td><fmt:formatDate pattern="yyyy년 MM월 dd일 HH시:mm분:ss초" value="${updateReq.regDate}"/></td>
    	</tr>
    	<tr>
    		<th>수정일</th>
    		<td><fmt:formatDate pattern="yyyy년 MM월 dd일 HH시:mm분:ss초" value="${updateReq.modDate}"/></td>
    	</tr>
    	<tr>
    		<td colspan="2" style="text-align: center;">	
   			<c:set var="pageNo" value="${ (empty param.pageNo) ? '1' : param.pageNo }"></c:set>
   				<input type="button" value="목록 이동" id="btnList">
   				
   			<c:if test="${authUser.mId==updateReq.user.mId}">
   				<input type="button" value="삭제하기" id="btnDelete">
   			</c:if>   		
    		</td>
    	</tr>
    </table>
	

</body>
</html>