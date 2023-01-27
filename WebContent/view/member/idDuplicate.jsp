<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("#btn1").on("click", function(){
			opener.document.memberFrm.memberid.value=idDuplicationFrm.idTxt.value;
			self.close();
		});
	});
</script>
</head>
<body>
<%-- server로부터 응답받은 후에는(여기서는 id찾기를 클릭하여 controller<->service<->dao<->db 비즈니스 로직 수행 후)
	 수행 결과(model)를 받아오면 conroller에 의해서 request.setAttribute("result", result);를 받음--%>
<h3>아이디 중복 검색</h3>
<!-- http://localhost:80/mBoard/view/member/idDuplicate.jsp
							   ../..idDuplicate.jsp -->
<form name="idDuplicationFrm" id="idDuplicationFrm" action="<%=request.getContextPath()%>/idDuplicateProc.do" method="get"> 
 *id검색:<input type="text" name="idTxt" id="idTxt" value="${userid}"/>
 <input type="submit" name="idBtn" id="idBtn" value="id찾기"/>
</form>
 <hr/>
 <%-- *수행결과: ${result} --%>
 <%-- jstl의 if문에는 else가 없음. 따라서 else에 해당하는 부분도 별도의 if tag를 사용해야한다.
	    만약 여러개의 조건이라면 when태그를 사용하는 것이 좋음. --%>
<c:if test="${result==0}">
${userid}은(는) 사용 가능한 ID입니다. <input type="button" id="btn1" name="btn1" value="사용"/>
</c:if>

<c:if test="${result==1}">
${userid}은(는) 중복 된 ID입니다.
</c:if>

</body>
</html>