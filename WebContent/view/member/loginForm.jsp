<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
	.error {color: red; font-size:0.8em;}
</style>
</head>
<body>
<a href="<%=request.getContextPath()%>/index.jsp">HOME</a>
<%-- 컨트롤러를 거쳐오면 아래와 같은 모델을 받아온다.
	 request객체.setAttribute("속성명", 값);
	 session객체.setAttribute("속성명", 값); 
	 --%>

<form action="login.do" id="loginFrm" name="loginFrm" method="post">
<c:if test="${errors.idOrPwNotMatch}"><span class="error">아이디와 암호가 일치하지 않습니다.</span></c:if>
<p>
아이디:<input type="text" name="mId" id="mId" value="user1"/>
 <c:if test="${errors.memberid}"><span class="error">아이디를 입력하세요</span></c:if> 
</p>
<p>
패스워드:<input type="password" name="mPwd" id="mPwd" value="1111"/>
<c:if test="${errors.memberpwd}"><span class="error">패스워드를 입력하세요</span></c:if>
</p>
<input type="submit" value="로그인"/><br/>
<a href="findId.do">아이디 찾기</a>/<a href="findPwd.do">비밀번호 찾기</a><br/>
<a href="join.do">회원가입</a>
</form>
</body>
</html>