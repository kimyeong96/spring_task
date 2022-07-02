<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<title>회원가입</title>
	<style>
		.profile_border{
			width: 100px;
			height: 100px;
			border : 1px solid grey;
			border-radius : 50%;
			overflow: hidden;
		}
		#profile_default{
			widht: 100px;
			height: 100px;
		}
	</style>
</head>
<body>
	<form id="signupForm" action="/member/signup" method="post" enctype="multipart/form-data">
		<div class="container">
			<div class="m-3">
				<div class="col text-center">
					<h2>회원가입</h2>
				</div>
			</div>
			<div class="m-3">
				<div class="profile_border m-auto">
					<img src="/resources/images/default_profile.png" id="profile_default">
				</div>
			</div>
			<div class="m-3 text-center">
				<label for="profile_preload" class="form-label">프로필 등록</label> 
				<input type="file" class="form-control w-50 m-auto" id="profile_image" name="file">
			</div>
			<div class="mb-3 ">
				<label for="id" class="form-label">아이디</label> 
				<input type="text" class="form-control" id="id" name="id">
			</div>
			<div class="mb-3 d-flex justify-content-end">
				<button type="button" id="checkBtn" class="btn btn-danger">중복확인</button>
			</div>
			<div class="mb-3">
				<label for="pw" class="form-label">비밀번호</label>
				<input type="password" class="form-control" id="pw" name="pw">
			</div>
			<div class="mb-3">
				<label for="nickname" class="form-label">닉네임</label>
				<input type="text" class="form-control" id="nickname" name="nickname">
			</div>		
			<div class="mb-3 form-check text-center">
				<button type="button" id="cancelBtn" class="btn btn-secondary">취소</button>
				<button type="submit" class="btn btn-primary">가입</button>
			</div>
		</div>		
	</form>
	<script>
		// 사용자가 profile_image 파일태그를 이용해 프로필 사진을 선택했을 때 profile_default 이미지 태그에 선택된 사진을 띄워주는 작업
		document.getElementById("profile_image").onchange = function(){
			let reader = new FileReader(); // 사용자가 파일태그를 이용해 파일을 선택했을 때 사용자의 로컬에 있는 파일의 정보를 브라우저에서 사용 가능하게끔 해주는 클래스(객체)
			reader.readAsDataURL(this.files[0]) // -> 인자값으로 file 객체
			
			// onload 함수가 트리거됨 -> onload 이벤트가 발생했을때 콜백펑션안에서 위에있는 이미지 태그의 src 에 이미지를 띄워줄 수 있는 경로값으로 대체  
			reader.onload = function(e){
				// e.target.result -> 브라우저에서 바로 해석(로드)이 가능하게끔 변환된 이미지의 경로값 
				console.log("e.target ", e.target.result);
				// 위에 있는 이미지 태그의 src 속성값을 변환된 이미지 경로값으로 셋팅 해주기(사용자가 선택한 이미지 띄우기)
				document.getElementById("profile_default").src = e.target.result;
			}
		}
		
		$("#checkBtn").on("click", function(){ // 아이디 중복확인 
			$.ajax({
				url : "/member/checkLogin"
				,type : "get"
				,data : {id : $("#id").val()}
				, success: function(data){
					if(data == "available"){
						alert("사용가능한 아이디입니다.");
					}else if(data == "unavailable"){
						alert("사용 불가한 아이디입니다.");
					}
				}, error : function(e){
					console.log(e);
				}
			})
		})
		
		$("#signupForm").on("submit", function(){ // 회원가입 제출 유효성 검사
			let regexId = /[a-zA-Z0-9]{6,12}/;
			let regexPw = /[a-zA-Z0-9]{6,20}/;
			let regexNickname = /[a-zA-Z0-9ㄱ-힣]{4,10}/;
			
			let id = $("#id").val();
			let pw = $("#pw").val();
			let nickname = $("#nickname").val();
			
			if(!regexId.test(id) || id === ""){
				alert("아이디를 정확히 입력하세요.");
				return false;
			}else if(!regexPw.test(pw) || pw === ""){
				alert("비밀번호를 정확히 입력하세요.");
				return false;
			}else if(!regexNickname.test(nickname) || nickname === ""){
				alert("닉네임을 정확히 입력하세요.");
				return false;
			}			
		})
	
	</script>
</body>
</html>