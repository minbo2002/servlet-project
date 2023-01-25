<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="conPath" value="<%=request.getContextPath()%>"></c:set>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index</title>
</head>
<body>
	<h2>메인화면</h2>

	<hr>
	
	<%-- 이문서는 LoginHandler 컨트롤러에 의해서 아래와 같이 model을 받는다.
		 class User
		   int memberno;	    // 회원번호
		   String memberid;     // id	
		   String membername;   // 이름
		   int grade;		    // 회원등급(기본1) =>  1(정상),2(강퇴),3(탈퇴),4(휴면),999(관리자)
		 session.setAttribute("AUTHUSER", user); 
	--%>
	*model로 받은 AUTHUSER => ${AUTHUSER} <br/>
	*sessionScope.AUTHUSER => ${sessionScope.AUTHUSER} <br/><br/><br/>
	*세션id => <%=session.getId()%> <br/>
	*세션의 최근 접근시간 => <%=session.getLastAccessedTime() %> <br/>
	*session.getValue() => <%=session.getValue("AUTHUSER") %> <br/>

	<%-- 비로그인시 보여지는 메뉴 --%>
	<c:if test="${empty AUTHUSER}">
		<ul>
			<h4>글 목록</h4>
			<li><a href="${conPath}/recomboard/listboard.do">추천게시판 목록</a></li>
		</ul>
	</c:if>

	
</body>
</html>