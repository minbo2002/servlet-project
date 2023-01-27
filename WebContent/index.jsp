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
	
	<%-- 이문서는 LoginController 의해서 아래와 같이 model을 받는다.
		private int m_no;
		private String mId;
		private String mName;
		private int grade;
		private String gender;
		request.getSession().setAttribute("authUser", user);
	--%>
	*model로 받은 authUser => ${authUser} <br/>
	*sessionScope.authUser => ${sessionScope.authUser} <br/><br/><br/>
	*세션id => <%=session.getId()%> <br/>
	*세션의 최근 접근시간 => <%=session.getLastAccessedTime() %> <br/>
	*session.getValue() => <%=session.getValue("authUser") %> <br/>

	<%-- 비로그인시 보여지는 메뉴 --%>
	<c:if test="${empty AUTHUSER}">
		<ul>
			<h4>회원</h4>
			<li><a href="${conPath}/login.do">로그인</a></li>
			<li><a href="${conPath}/logout.do">로그아웃</a></li>
			<li><a href="${conPath}/findId.do">아이디 찾기</a></li>
			<li><a href="${conPath}/findPwd.do">비밀번호 찾기</a></li>
		</ul>
		<ul>
			<h4>글 목록</h4>
			<li><a href="${conPath}/recomboard/listboard.do">추천게시판 목록</a></li>
		</ul>
	</c:if>

	
</body>
</html>