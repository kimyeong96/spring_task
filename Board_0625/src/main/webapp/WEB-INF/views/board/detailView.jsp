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
.imgBox {
	width:200px;
	height:200px;
}

.imgBox img {
	width:200px;
	height:200px;
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
			<div class="mb-3 mt-1">
				<label class="form-label">글번호</label> 
				<input type="text" class="form-control" id="seq_board" name="seq_board" value="${list[0].seq_board}" readonly>
			</div>
			<div class="mb-3 mt-1">
				<label class="form-label">제목</label> 
				<input type="text" class="form-control" id="title" name="title" value="${list[0].title}" readonly>
			</div>
			<div class="mb-3 mt-1">
				<label class="form-label">작성자</label> 
				<input type="text" class="form-control" id="writer_id" name="writer_id"  value="${list[0].writer_id}" readonly>
			</div>
			<div class="mb-3 mt-1">
				<label class="form-label">닉네임</label> 
				<input type="text" class="form-control" id="writer_nickname" name="writer_nickname" value="${list[0].writer_nickname}" readonly>
			</div>
			<div class="mb-3 mt-1">
				<label class="form-label">날짜</label> 
				<input type="text" class="form-control" id="written_date" name="written_date" value="${list[0].written_date}" readonly>
			</div>
			
			<!-- 사진 유무 -->
			<div class="mb-3 mt-1">
				<c:choose>
					<c:when test="${imageList.size() == 0}">
						<p>등록된 사진이 없습니다.</p>
					</c:when>
					<c:otherwise>
						<label class="form-label">사진</label>
						<c:forEach items="${imageList}" var="dto">
							<div class="imgBox">
								<img src="/board/${dto.getBoard_image()}"
									id="board_image">
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="mb-3 mt-1">
				<label class="form-label">내용</label>
				<textarea class="form-control" style="height: 150px" name="content" readonly>${list[0].content}</textarea>
			</div>
			
			<!-- 뒤로가기, 수정 버튼 -->
			<div class="row mt-4">
				<div class="col d-flex justify-content-center">
					<button type="button" id="backBtn" class="btn btn-warning ms-3">뒤로가기</button>
					<button type="button" id="modifyBtn" class="btn btn-primary ms-3">수정</button>
				</div>
			</div>

	</div>
	
	
	<script>
	// 로그아웃 버튼 클릭
	$("#logoutBtn").on("click",function() {
		location.href = "/board/logout";
	})
	
	// 뒤로가기 버튼 클릭
	$("#backBtn").on("click",function() {
		location.href = "/board/back";
	})
	
	</script>
	
	
	
	
	
	
	
</body>
</html>