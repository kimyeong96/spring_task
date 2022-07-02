<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<title>메인</title>
</head>
<body>
	<div class="container">
		<div class="row m-3"> 
			<div class="col d-flex justify-content-center">
				<h2>로그인</h2>
			</div>
		</div>
		<form id="loginForm">
			<div class="mb-3">
				<label for="id" class="form-label">아이디</label> 
				<input type="text" class="form-control" id="id" name="id" value="abc123">
			</div>
			<div class="mb-3">
				<label for="pw" class="form-label">비밀번호</label>
				<input type="password" class="form-control" id="pw" name="pw" value="abc123">
			</div>
			<div class="mb-3 form-check d-flex justify-conte-center">
				<input type="checkbox" class="form-check-input" id="rememberId">
				<label class="form-check-label" for="maintainId">아이디 기억하기</label>
			</div>
			<div class="mb-3 form-check text-center">
				<button type="button" id="loginBtn" class="btn btn-primary">로그인</button>
				<button type="button" id="toSignup" class="btn btn-warning">회원가입</button>
			</div>
		</form>
	</div>
	<script>
	
		// 회원가입 페이지 요청
		document.getElementById("toSignup").onclick = function(){
			location.href = "/member/toSignup";
		}
		
		// 로그인 요청 
		document.getElementById("loginBtn").onclick = function(){
			$.ajax({
				url : "/member/login"
				,type : "post"
				,data : {id : $("#id").val(), pw : $("#pw").val()}
				, success: function(data){
					console.log(data);
					if(data == "success"){
						location.href = "/member/toWelcome";
					}else if(data == "fail"){
						alert("아이디 혹은 비밀번호가 일치하지 않습니다.");
					}
				}, error : function(e){
					console.log(e);
				}
			})
		}
		
	</script>
</body>
</html>
