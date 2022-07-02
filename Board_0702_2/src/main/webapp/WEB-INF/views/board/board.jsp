<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<title>게시판</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col d-flex justify-content-end">
				<button type="button" class="btn btn-danger" id="logout">로그아웃</button>
			</div>
		</div>
		<div class="row">
			<div class="col d-flex justify-content-center">
				<h2>게시판</h2>
			</div>
		</div>
		<table class="table table-hover">
		  <thead>
		    <tr>
		      <th scope="col">글번호</th>
		      <th scope="col">제목</th>
		      <th scope="col">글쓴이</th>
		      <th scope="col">날짜</th>
		      <th scope="col">조회수</th>
		    </tr>
		  </thead>
		  <tbody>
		  	<c:if test="${list.size() == 0}">
		  		<tr>
				   <td colspan="5">등록된 글이 없습니다.</td>
				</tr>
		  	</c:if>
		  	<c:if test="${list.size() > 0}">
		  		<c:forEach items="${list}" var="dto">
		  			<tr>
				      <td>${dto.seq_board}</td>
				      <td><a href="/board/toDetail?seq_board=${dto.seq_board}">${dto.title}</a></td>
				      <td>${dto.writer_nickname}</td>
				      <td>${dto.written_date}</td>
				      <td>${dto.view_count}</td>
				    </tr>
		  		</c:forEach>
		  	</c:if>		  	
		  </tbody>
		</table>
		<div class="row">
			<div class="col d-flex justify-content-end">
				<button type="button" class="btn btn-success" id="toWrite">글쓰기</button>
			</div>
		</div>
	</div>
	<script>
		// 로그아웃 요청
		$("#logout").on("click", function(){
			location.href = "/member/logout";
		});
		// 글쓰기 페이지 요청
		$("#toWrite").on("click", function(){
			location.href = "/board/toWrite";
		});
	</script>
</body>
</html>