<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
</head>
<body>
<form Name="findIdFrm" method="post" action="findId.do" >
<h3>FIND ID</h3>

<label>이름</label>
<input type="text" id="name" name="name" size="50" placeholder="이름을 입력해주세요." required="required" maxlength="50"/> 
<c:if test="${errors.mName}">가입시 입력한 이름을 입력하세요(필수 입력)</c:if>
<c:if test="${errors.badmName}">일치하는 이름이 없습니다.</c:if>
<br>
<label>이메일</label>
<input type="text" id="email" name="email" size="50" placeholder="이메일을 입력해주세요." required="required" maxlength="50"/> 
<c:if test="${errors.email}">가입시 입력한 이메일을 입력하세요(필수 입력)</c:if>
<c:if test="${errors.bademail}">일치하는 이메일이 없습니다.</c:if>
<br>
<input type="submit" value="찾기">
<button type="button" id="back" name="back" onclick="history.back()">취소</button>


</form>
</body>
</html>