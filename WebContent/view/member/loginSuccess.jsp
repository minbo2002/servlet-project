<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>LOGIN SUCCESS!</h2>
 ${authUser.mId}님 로그인을 축하합니다.
<form action="/index.jsp" id="logoutFrm" name="logoutFrm" method="post">
<input type="submit" value="메인 화면으로"/>
</form>

</body>
</html>