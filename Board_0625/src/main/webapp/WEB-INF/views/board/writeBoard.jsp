<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-kjU+l4N0Yf4ZOJErLsIcvOU2qSb74wXpOhqTvwVx3OElZRweTnQ6d31fXEoRD1Jy" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>

<style>
textarea {
	resize: none;
}
</style>
</head>
<body>
	<div class="container mt-4">
		<!-- 로그아웃 -->
		<div class="row">
			<div class="col d-flex justify-content-end">
				<button type="button" id="logoutBtn" class="btn btn-danger">로그아웃</button>
			</div>
		</div>
		<!-- head -->
		<div class="row">
			<div class="col text-center">
				<h3>글쓰기</h3>
			</div>
		</div>
		<!--글 쓰기 내용-->
		<form id="boardForm">
			<div class="mb-3 mt-1">
				<label class="form-label">제목</label> 
				<input type="text" class="form-control" id="title" name="title">
			</div>
			<div class="mb-3 mt-1">
				<label class="form-label">파일첨부</label> 
				<input type="file" class="form-control" id="file" name="file" multiple>
			</div>
			<div class="mb-3 mt-1">
				<label class="form-label">내용</label>
				<textarea class="form-control" style="height: 150px" name="content"></textarea>
			</div>
		</form>
		<!-- 뒤로가기, 등록 버튼 -->
		<div class="row mt-4">
			<div class="col d-flex justify-content-center">
				<button type="button" id="backBtn" class="btn btn-warning ms-3">뒤로가기</button>
				<button type="button" id="registerBtn" class="btn btn-primary ms-3">등록</button>
			</div>
		</div>

	</div>
	
	<script>
	
	// 뭐 올렸는지 확인 용도 
	document.getElementById("file").onchange = function(){
		let reader = new FileReader(); // 사용자가 파일태그를 이용해 파일을 선택했을 때 사용자의 로컬에 있는 파일의 정보를 브라우저에서 사용 가능하게끔 해주는 클래스(객체)
		reader.readAsDataURL(this.files[0]) // -> 인자값으로 file 객체
		
		for(i = 0; i < 2; i++) {
			reader.onload = function(e){
				console.log("e.target ============================", e.target.result);
				console.log("11111111111111111");
			}
		}
		
	}
	
	// 등록버튼 눌렀을 때
	$("#registerBtn").on("click",function(){
		
		console.log(document.getElementById("boardForm"));
		let data = new FormData(document.getElementById("boardForm"));
		
		$.ajax({
			url : "/board/registerBoard",
			type : "post", // file 데이터를 보낼때는 post로 해야한다
			contentType : false,
			processData : false,
			enctype : "multipart/form-data",
			data : data, // 위에서 생성한 form // 사진(multipart)와 input(profile_message)가 data에 담겨있다.
			success : function(data) {
				if(data == "success") {
					location.href = "/board/toBoard";
				}else {
					console.log("실패");
				}
			},
			error : function(e) {
				console.log(e);
			}
		})
	})
	
	// 뒤로 가기 버튼 눌렀을 때 
	$("#backBtn").on("click",function(){
		location.href = "/board/toBoard";
	})
	
	
	
	
	</script>
	
	
	
	
	
	
	
	
	
</body>
</html>