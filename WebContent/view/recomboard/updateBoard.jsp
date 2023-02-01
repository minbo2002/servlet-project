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
		
		
		$("#btnList").click(function() {

			location.href="<%=request.getContextPath()%>/recomboard/listboard.do?pageNo=${pageNo}&rowSize=${rowSize}";

		});
		
		$("#btnDelete").click(function(no) {

			if(confirm("삭제하시겠습니까?")) {
				location.href = "<%=request.getContextPath()%>/recomboard/delete.do?no=${boardData.recomBoard.rNo}";
			}
		});
		
		$("#btnModify").click(function(){
			
			let v = document.querySelector("#filename").value;
			alert(v);
			
			if(v == ""){
				//$("#filename").val($("#hideFileName").val());
				document.querySelector("#filename").value=document.querySelector("#hideFileName").value;
			}
			//$("#updateFrm").submit();
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

    	rNo:<input type="text" name="rNo" id="rNo" value="${boardData.recomBoard.rNo}"/><br/>
    <!-- 	hidFileName:<input type="text" name="hidFileName" id="hidFileName" value="${boardData.recomFile.fileRealName}"/>  -->
	    <input type="text" name="hideFileName" id="hideFileName" value="${boardData.recomFile.fileRealName}"/>
	    
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
	    		<!--	tt:<input type="text" name="tt" id="tt" value="${boardData.recomFile.fileRealName}"/>   -->
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
	    		<td>
	    			<input type="text" name="likeIt" id="likeIt" value="${boardData.recomBoard.likeIt}" readonly="readonly" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>조회수</th>
	    		<td>
	    			<input type="text" name="rCnt" id="rCnt" value="${boardData.recomBoard.rCnt}" readonly="readonly" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>작성일</th>
	    		<td>
	    			<input type="text" name="regDate" id="regDate" value="<fmt:formatDate pattern="yyyy년 MM월 dd일 HH시:mm분:ss초" value="${boardData.recomBoard.regDate}"/>" readonly="readonly" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<th>수정일</th>
	    		<td>
	    			<input type="text" name="modDate" id="modDate" value="<fmt:formatDate pattern="yyyy년 MM월 dd일 HH시:mm분:ss초" value="${boardData.recomBoard.modDate}"/>" readonly="readonly" />
	    		</td>
	    	</tr>
	    	<tr>          
		 		<td colspan="2" style="text-align:center;">
					<%-- <input type="submit" value="수정 진행"/> --%>
					<input type="button"  id="btnModify" value="수정 진행"/>
		 		</td>
		 	</tr>
	    	<tr>
	    		<td colspan="2" style="text-align: center;">	
	   			<c:set var="pageNo" value="${ (empty param.pageNo) ? '1' : param.pageNo }"></c:set>
	  				<input type="button" value="목록 이동" id="btnList">
	  				<input type="button" value="삭제하기" id="btnDelete">  		
	    		</td>
	    	</tr>
	    </table>
	    
	</form>
	
</body>
</html>