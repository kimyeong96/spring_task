<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board 게시판입니다</title>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
 	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-kjU+l4N0Yf4ZOJErLsIcvOU2qSb74wXpOhqTvwVx3OElZRweTnQ6d31fXEoRD1Jy" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>

<style>
.container {
	background: linear-gradient(#D7E1EC, #FFFFFF);
}
a {
text-decoration: none;

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
				<h3>게시판</h3>
			</div>
		</div>
		<!--table -->
		<div class="row mt-4">
			<div class="col">
				<table class="table">
					<thead>
						<tr>
							<th scope="col" class="text-center">글번호</th>
							<th scope="col" class="text-center">제목</th>
							<th scope="col" class="text-center">글쓴이</th>
							<th scope="col" class="text-center">날짜</th>
							<th scope="col" class="text-center">조회수</th>
						</tr>
					</thead>
					<tbody>
						<!-- 조건문을 통해 데이터가 있을 때와 없을때 분리 -->
						<c:choose>
							<c:when test="${list.size() == 0}">
								<tr>
									<td colspan=5 class="text-center">등록된 글이 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${list}" var="dto">
									<tr>
										<td class="text-center">${dto.seq_board}</td>
										<td class="text-center"><a href="/board/detailView?seq_board=${dto.seq_board}">${dto.title}</a></td>
										<td class="text-center">${dto.writer_nickname}</td>
										<td class="text-center">${dto.written_date}</td>
										<td class="text-center">${dto.view_count}</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 글쓰기 -->
		<div class="row mt-2">
			<div class="col d-flex justify-content-end">
				<button type="button" id="boardWriteBtn" class="btn btn-success">글쓰기</button>
			</div>
		</div>
	</div>
	
	<script>
	
	// 로그아웃 버튼 클릭
	$("#logoutBtn").on("click",function() {
		location.href = "/board/logout";
	})
	
	// 글쓰기 버튼 클릭
	$("#boardWriteBtn").on("click",function() {
		location.href = "/board/boardWrite";
	})
	
	
	
	
	
	
	</script>
	
	
	
	
	
	
	
	
</body>
</html>