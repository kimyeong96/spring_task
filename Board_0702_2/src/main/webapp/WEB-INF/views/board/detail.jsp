<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<title>상세보기</title>
</head>
<body>
	<form id="modifyForm" action="/board/modify" method="post" enctype="multipart/form-data">
		<div class="container">
			<div class="row">
				<div class="col d-flex justify-content-end">
					<button type="button" class="btn btn-danger" id="logout">로그아웃</button>
				</div>
			</div>
			<div class="row">
				<div class="col d-flex justify-content-center">
					<h2>상세보기</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label for="title" class="form-label">제목</label>
			  		<input type="text" class="form-control" id="title" name="title" value="${map.boardDTO.title}" readonly>
				</div>
				<div class="col-6">
					<label for="title" class="form-label">글쓴이</label>
			  		<input type="text" class="form-control" value="${map.boardDTO.writer_nickname}" readonly>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<label for="file" class="form-label">첨부파일</label>
				</div>
				<ul class="list-group p-2">
					<c:if test="${map.fileList.size() == 0}">
						<li class="list-group-item"><b>첨부된 파일이 없습니다.</b></li>
					</c:if>
					<c:if test="${map.fileList.size() > 0}">
						<c:forEach items="${map.fileList}" var="fileDTO">
							<li class="list-group-item">
								<a href="/file/download?sys_name=${fileDTO.sys_name}&ori_name=${fileDTO.ori_name}">${fileDTO.ori_name}</a>
								<button type="button" class="btn btn-danger d-none deleteFile" value="${fileDTO.sys_name}">-</button>
							</li>	
						</c:forEach>
					</c:if>					
				</ul>
				<div class="col-12 file-col d-none">
					<input type="file" class='form-control' name="files" multiple>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="content" class="form-label">내용</label>
			  		<textarea class="form-control" id="content" name="content" rows="10" readonly>${map.boardDTO.content}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col d-flex justify-content-center">
					<button type="button" class="btn btn-secondary m-1" id="toBack">뒤로가기</button>
					<c:if test="${loginSession.id eq map.boardDTO.writer_id}">
						<button type="button" class="btn btn-primary m-1" id="modify">수정</button>
						<button type="submit" class="btn btn-success m-1 d-none" id="complete">완료</button>
						<button type="button" class="btn btn-danger m-1" id="delete">삭제</button>				
					</c:if>
				</div>
			</div>
		</div>
		<div class="d-none"> <!--  숨겨진 데이터 영역  -->
			<input type="text" value="${map.boardDTO.seq_board}" name="seq_board" id="seq_board">
			<input type="text" id="deleteFileList" name="deleteFileList[]">
		</div>
	</form>
	<script>
		// 수정완료 이벤트가 일어났을때 (form 제출이 일어날때)
		$("#modifyForm").on("submit", function(){
			// 최종적인 파일 삭제 리스트를 deleteFileList 에 담아주는 작업
			$("#deleteFileList").val(deleteFileList);
			console.log($("#deleteFileList").val());
			//return false;//form 제출 막는 작업
		})
	
	
		let deleteFileList = new Array(); // 삭제할 파일의 이름을 기록하는 용도
		/* - 버튼을 눌렀을때 삭제할 파일의 sys_name 을 배열에 기록/저장
		-> 수정완료버튼을 눌렀을때 그 배열도 함께 컨트롤러로 넘겨 줌.
		-> service의 modify 메서드를 호출했을때 그 메서드 내부에서
		게시글 수정 + 새로 첨부된 파일 업로드  / + 넘겨받은 삭제할 파일의 배열을 이용해 서버경로에서 파일 삭제*/
		$(".deleteFile").on("click", function(e){
			deleteFileList.push(e.target.value);
			console.log(deleteFileList);
			$(this).parent("li").remove(); // 부모 li를 지워서 - 버튼을 누르면 안보이게 하기
		})
	
		// 수정 버튼을 클릭했을 때
		$("#modify").on("click", function(){
			$("#modify").addClass("d-none");
			$("#complete").removeClass("d-none");
			$(".file-col").removeClass("d-none");
			$(".deleteFile").removeClass("d-none");
			$("#title").attr("readonly", false);
			$("#content").attr("readonly", false);
		})
		
			// 로그아웃 요청
		$("#logout").on("click", function(){
			location.href = "/member/logout";
		});
		// 뒤로가기 
		$("#toBack").on("click", function(){
			location.href = "/board/toBoard";
		});
		$("#delete").on("click", function(){
			let answer = confirm("정말 삭제하시겠습니까?");
			if(answer){ // 만약 확인 버튼을 누르면
				// 동적 form 생성
				let deleteForm = $("<form>").attr({
					"method" : "post"
					, "action" : "/board/delete"
				}).css("display", "none");
				// form에 서버로 보낼 데이터 태그 append
				deleteForm.append($("#seq_board"));
				// body영역에 실제 html요소로써 form append
				deleteForm.appendTo("body");
				deleteForm.submit();
			}			
		})
	</script>
</body>
</html>