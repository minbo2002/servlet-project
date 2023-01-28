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
	*sessionScope.authUser => ${sessionScope.authUser} <br/>
	*세션id => <%=session.getId()%> <br/>
	*세션의 최근 접근시간 => <%=session.getLastAccessedTime() %> <br/>
	*session.getValue() => <%=session.getValue("authUser") %> <br/>


<%--관리자가 로그인했을때 보여지는 메뉴 --%>
	<c:if test="${not empty authUser && (authUser.grade==999)}">
		${AUTHUSER.membername}님 접속중
		<ul>
			<h4>관리자 전용</h4>
			<li><a href="${conPath}/logout.do">로그아웃</a></li>
			<li><a href="${conPath}/article/write.do">글쓰기</a></li>  <br/><br/>
			<h4>회원 관리</h4>
			<li><a href="${conPath}/admin/getMemberInfo.do">myPage</a></li>   <br/><br/>
			<h4>게시판 관리</h4>
		</ul>
		<ul>
			<h4>글 목록</h4>
			<li><a href="${conPath}/recomboard/listboard.do">추천게시판 목록</a></li>
		</ul>
	</c:if>
	
	<%--일반회원이 로그인했을때 보여지는 메뉴 --%>
	<c:if test="${not empty authUser && (authUser.grade==1)}">
		${AUTHUSER.membername}님 접속중
		<ul>
			<h4>일반회원 전용</h4>
			<li><a href="${conPath}/logout.do">로그아웃</a></li>  
		</ul>
		<ul>
			<h4>글 목록</h4>
			<li><a href="${conPath}/recomboard/listboard.do">추천게시판 목록</a></li>
		</ul>
	</c:if>

	<%-- 비로그인시 보여지는 메뉴 --%>
	<c:if test="${empty authUser}">
		<ul>
			<h4>비회원 전용</h4>
			<li><a href="${conPath}/login.do">로그인</a></li>
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