<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<style>
 #memberFrm td, #memberFrm th{
 	border: 1px solid #ddd;
 	padding: 5px;
 }
 #memberFrm th{
  text-align:right;
  background-color:#555;
  color:white;
 }
 .c1 {width:120px;}
 /*에러 안내 글자색 설정*/
 .error1{color:red; font-size:10px;}
</style>
<script>
$(document).ready(function(){
	
//이메일 선택반영
$("#email_dd").change(function(){
	let val = $("select#email_dd option:selected").val();
	$("#email_d").val(val);
});

	
}); //jQuery끝
</script>
</head>
<body>
<%-- 컨트롤러에서 아래와 같이 model을 받음
	request.setAttribute("errors", errors);
						  errors.put("duplicateId", Boolean.TRUE); --%>
<h2>회원가입</h2>
 <form name="joinFrm" id="joinFrm" 
       method="post" action="<%=request.getContextPath() %>/join.do">
 <table>
 	<caption>회원가입</caption>
 	<tbody>
 	 <tr> 
 	  <th class="c1">아이디</th>
 	  <td>
 	   <input type="text" name="mId" id="mId" autofocus/>
 	   <span class="error1">
 	   <c:if test="${errors.mId}">ID를 입력하세요.</c:if>
 	   <c:if test="${errors.duplicateId}">이미 사용중인 ID입니다.</c:if>
 	   </span>
 	  </td>
 	 </tr>
 	 <tr>
 	  <th class="c1">비밀번호</th>
 	  <td>
 	   <input type="password" name="mPwd" id="mpwd"/>
 	   <span class="error1">
 	   <c:if test="${errors.mPwd}">비밀번호를 입력하세요.</c:if>
 	   </span>
 	  </td>
 	 </tr>
 	 <tr>
 	  <th class="c1">비밀번호재확인</th>
 	  <td>
 	   <input type="password"  name="re_mPwd" id="re_mpwd"/>
 	   <span class="error1">
 	   <c:if test="${errors.re_mPwd}">재확인용 비밀번호를 입력하세요.</c:if>
 	   <c:if test="${errors.notMatch}">비밀번호가 일치하지 않습니다.</c:if>
 	   </span>
 	  </td>
 	 </tr>
 	 <tr>
 	  <th class="c1">이름</th>
 	  <td>
 	   <input type="text" name="mName" id="mName"/>
 	   <span class="error1">
 	   <c:if test="${errors.mName}">이름을 입력하세요.</c:if>
 	   </span>
 	  </td>
 	 </tr>
 	 <tr>
 	  <th class="c1">성별</th>
 	  <td>
 	   <input type="radio" name="gender" id="gender0" value="1"/><label for="gender0">여성</label>
 	   <input type="radio" name="gender" id="gender1" value="0"/><label for="gender1">남성</label>
 	   <span class="error1">
 	   <c:if test="${errors.gender}">성별을 선택하세요.</c:if>
 	   </span>
 	  </td>
 	 </tr>
 	  <tr>
 	  <th class="c1">주소</th>
 	  <td>
 	   <input type="text" name="addr" id="addr"/>
 	  </td>
 	 </tr>
 	 <tr>
 	  <th class="c1">이메일</th>
 	  <td>
 	   <input type="text" name="email_id" id="email_id" size="12" required="required"/>
 	   @
 	   <input type="text" name="email_d" id="email_d"  size="12" required="required"/>
 		 <!-- <select name="email_dd" id="email_dd" onchange="selectEamil(this);"> -->
 		 <select name="email_dd" id="email_dd">
 				<option value="">직접입력</option>  
 				<option value="daum.net">daum.net</option>
 				<option value="gmail.com">gmail.com</option>
 				<option value="nate.com">nate.com</option>
 				<option value="naver.com">naver.com</option>
 		 </select>		
 	  </td>
 	 </tr>
<!--<tr>
 	  <td colspan="2" style="text-align:center;">
 	   <input type="checkbox" name="agree" id="agree"/>
 	   <label for="agree">약관에 동의합니다</label>
 	  </td> 	  
 	 </tr> -->
 	 <tr>
 	  <td colspan="2" style="text-align:center;">
 	   <input type="submit" value="가입"/>
 	   <input type="reset" value="취소"/>
 	  </td>
 	 </tr>
 	</tbody>
 </table>
</form>
</body>
</html>




