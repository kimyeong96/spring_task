<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<title>글쓰기</title>
</head>
<body>
	<form action="/board/write" method="post" enctype="multipart/form-data">
		<div class="container">
			<div class="row">
				<div class="col d-flex justify-content-end">
					<button type="button" class="btn btn-danger" id="logoutBtn">로그아웃</button>
				</div>
			</div>
			<div class="row">
				<div class="col d-flex justify-content-center">
					<h2>글쓰기</h2>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="title" class="form-label">제목</label>
			  		<input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요.">
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="file" class="form-label">파일첨부</label>
			  		<input type="file" class="form-control" id="files" name="files" multiple>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="content" class="form-label">내용</label>
			  		<textarea class="form-control" id="content" name="content" rows="10"></textarea>
				</div>
			</div>
			<div class="row">
				<div class="col d-flex justify-content-center">
					<button type="button" class="btn btn-secondary m-1" id="toBack">뒤로가기</button>
					<button type="submit" class="btn btn-primary m-1" id="write">등록</button>
				</div>
			</div>
		</div>
		<div class="d-none">
			<!-- jsp 에서 미리 loginSession에 있는 id, nickname을 셋팅해주면 controller쪽에서
				직접 세션값을 통해 id, nickname 꺼내 dto에 set해주지 않아도 되니 편함. -->
			<input type="text" name="writer_id" value="${loginSession.id}">
			<input type="text" name="writer_nickname" value="${loginSession.nickname}" >
		</div>
	</form>
		<script>
		 	// 뒤로 가기 버튼
			document.getElementById("toBack").onclick = function(){
				location.href = "/board/board";
			}
			
		</script>
</body>
</html>