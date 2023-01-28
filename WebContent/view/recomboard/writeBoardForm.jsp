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
	 .error {
	 	color:red; font-size:0.8em;
	 }
	</style>
	
	<script>

	</script>
	 
</head>
<body>
	<%-- WriteArticleHandler 컨트롤러의 GET방식에 의해 아래와 같은 Model을 받는다
		 
		 class User {
			private int m_no;
			private String mId;
			private String mName;
			private int grade;
			private String gender;
		 }
		 -->  request.getSession().setAttribute("authUser", user);	
		 
		 -->  request.setAttribute("rowSize", rowSize);
		 
		 -->  request.setAttribute("newArticleNo", newArticleNo); ??????????
		 
		 // 에러정보
		 errors.put("bInfoTitle", Boolean.TRUE);
		 errors.put("author", Boolean.TRUE);
		 errors.put("publisher", Boolean.TRUE);
		 errors.put("rTitle", Boolean.TRUE);
		 errors.put("rContent", Boolean.TRUE);
		 
		 -->  request.setAttribute("errors", errors);		 
	--%>
	*세션 : ${authUser} <br/>
	*보고싶은 페이지 pageNo : ${pageNo} <br/>
	*페이지당 글개수 rowSize : ${rowSize} <br/>
	*errors : ${errors}     	  <br/>
	*newBoardNo : ${newBoardNo}  <br/>>

	<a href="<%=request.getContextPath()%>/index.jsp">HOME</a>

	<h2>writeBookForm.jsp</h2>
    <hr/>
    
	<form name="writeFrm" id="writeFrm" method="post" action="<%=request.getContextPath()%>/recomboard/write.do" enctype="multipart/form-data">

		<!--  번호와 작성자명의 type="text"라면 수정이 가능하므로 type="hidden" 방식으로 name속성에 값만 담아서 POST방식으로 컨트롤러에 보낸다  -->
		<%--
		 <input type="hidden" name="no" id="no" value="${modReq.articleNumber}"/>
		 <input type="hidden" name="writer_name" id="writer_name" value="${modReq.writer_name}">
		--%>
		 
		<input type="hidden" name="rowSize" id="rowSize" value="${rowSize}"/>
		 
		<table border="1">
	   <!-- <tr>
		 		<th>작성자명</th>
		 		<td>${AUTHUSER.membername}</td>
		 	</tr>  -->
		 	<tr>
		 		<th>책제목</th>
		 		<td>
		 			<input type="text" name="bookTitle" id="bookTitle" />
		 			<span class="error"><c:if test="${errors.title}">제목을 입력하세요</c:if></span>
		 		</td>
		 	</tr>
		 	<tr>
		 		<th>저자</th>
		 		<td>
		 			<input type="text" name="author" id="author" />
		 			<span class="error"><c:if test="${errors.title}">제목을 입력하세요</c:if></span>
		 		</td>
		 	</tr>
		 	<tr>
		 		<th>출판사</th>
		 		<td>
		 			<input type="text" name="publisher" id="publisher" />
		 			<span class="error"><c:if test="${errors.title}">제목을 입력하세요</c:if></span>
		 		</td>
		 	</tr>
		 	<tr>
		 		<th>책이미지 첨부</th>
		 		<td>
		 			<input type="file" name="filename" id="filename" />
		 			<span class="error"><c:if test="${errors.title}">제목을 입력하세요</c:if></span>
		 		</td>
		 	</tr>
		 	<tr>
		 		<th>제목</th>
		 		<td>
		 			<input type="text" name="rTitle" id="rTitle" />
		 			<span class="error"><c:if test="${errors.title}">제목을 입력하세요</c:if></span>
		 		</td>
		 	</tr>
		 	<tr>
		 		<th>내용</th>
		 		<td>
		 			<textarea name="rContent" id="rContent" rows="5" cols="30"></textarea>
		 			<span class="error"><c:if test="${errors.content}">내용을 입력하세요</c:if></span>
		 		</td>
		 	</tr>
		 	<tr>          
		 		<td colspan="2" style="text-align:center;">
					<input type="submit" value="글쓰기" />
		 		</td>
		 	</tr>
		 	<tr>          
		 		<td colspan="2" style="text-align:center;">
		 			<a href="/recomboard/listboard.do?rowSize=${rowSize}">목록보기</a>
		 		</td>
			</tr>
		</table>
	</form>
   
</body>
</html>