<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	
	<title>Welcome!</title>
	<style>
		* {
			box-sizing: border-box;
		}
		
		.profile {
			background-color: #f5e6d550;
		}
		
		.profile_border {
			width: 100px;
			height: 100px;
			border-radius: 50%;
			overflow: hidden;
		}
		
		#profile_image {
			widht: 100%;
			height: 100%;
			border-radius: 50%;
		}
		
		#profile_message {
			font-weight: bold;
		}
		
		.form-control[readonly] {
			background-color: transparent;
			border: none;
		}
	</style>
</head>
<body>
	<div class="container">
		<form id="profileForm">
			<div class="row m-3 p-3 profile">
				<div class="col-12">
					<div class="profile_border m-auto">
						<c:choose>
							<c:when test="${empty loginSession.profile_image}">
								<img src="/resources/images/default_profile.png" id="profile_image">
							</c:when>
							<c:otherwise>
								<img src="/profile/${loginSession.profile_image}" id="profile_image">
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="col-12 d-flex justify-content-center">
					<input type="file" class="form-control w-50 d-none" id="file" name="file">
				</div>
				<div class="col-12 text-center">
					<c:choose>
						<c:when test="${empty loginSession.profile_message}">
							<input type="text" class="form-control text-center" id="profile_message" value="상태 메세지가 없습니다." readonly>
							<input type="text" class="d-none" id="form_message" name="profile_message" readonly>
						</c:when>
						<c:otherwise>
							<input type="text" class="form-control text-center" id="profile_message" value="${loginSession.profile_message}" name="profile_message" readonly>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form>
		<div class="row m-2 text-center">
			<div class="col p-1">
				<button type="button" class="btn btn-primary w-100"
					id="changeProfile">프로필변경</button>
				<button type="button" class="btn btn-danger w-100 d-none"
					id="saveProfile">프로필저장</button>
			</div>
			<div class="col p-1">
				<button type="button" class="btn btn-secondary w-100" id="toLogout">로그아웃</button>
			</div>
			<div class="col p-1">
				<button type="button" class="btn btn-warning w-100" data-bs-toggle="modal" data-bs-target="#myInfoModal">내 정보</button>
			</div>
			<div class="col p-1">
				<button type="button" class="btn btn-success w-100" id="toBoard">게시판</button>
			</div>
		</div>
	</div>
	
	<!-- 내 정보 Modal -->
	<div class="modal fade" id="myInfoModal" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header text-center">
	        <h5 class="modal-title text-center" id="myInfoModalLabel">내 정보</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <div class="mb-3">
				<label for="id" class="form-label">아이디</label> 
				<input type="text" class="form-control" id="id" value="${loginSession.id}" readonly>
			</div>
			<div class="mb-3">
				<label for="nickname" class="form-label">닉네임</label>
				<input type="text" class="form-control" id="nickname" value="${loginSession.nickname}" readonly>
			</div>		
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
	        <button type="button" class="btn btn-warning" id="modifyInfo">수정</button>
	        <button type="button" class="btn btn-primary d-none" id="saveInfo">완료</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<script>
	
		// 로그아웃 요청
		$("#toLogout").click(function(){
			location.href = "/member/logout";
		})
		
		// 게시판 페이지 요청
		$("#toBoard").click(function(){
			location.href = "/board/toBoard";
		})
		
		// 프로필 저장 버튼을 클릭했을 때
		$("#saveProfile").on("click", function(){
			$("#saveProfile").addClass("d-none");// 프로필 저장 버튼을 프로필 변경버튼으로 바꾸기
			$("#changeProfile").removeClass("d-none");
			$("#file").addClass("d-none"); // 파일 태그 숨기기
			$("#profile_message").attr("readonly", true); // 메세지 입력창 다시 readonly 적용
			/* jQuery의 serialize 함수를 이용해서 form을 전송할 수 있는 형태로 변환 
				파일을 전송해야하는 경우에는 serialize 로 데이터를 변환해도 파일데이터가 정상적으로 변환 X
				
				자바스크립트의 FormData 객체에 우리가 만들어둔 form을 자바스크립트 객체로 넘겨서 만든 변수를
				ajax의 data 영역에 셋팅 
			*/
			if($("#profile_message").val() !== "상태 메세지가 없습니다."){
				$("#form_message").val($("#profile_message").val());
			}
			let data = new FormData(document.getElementById("profileForm"));
			$.ajax({
				url : "/member/modifyProfile"
				, type : "post"
				, enctype : "multipart/form-data"
				, contentType : false
				, processData : false
				, data : data
				, success: function(data){
					console.log(data); 
					if(data == "success"){
						alert("수정에 성공했습니다.");
					}else if(data == "fail"){
						alert("수정에 실패했습니다. 다시 시도해 주세요.");
					}
				}, error : function(e){
					console.log(e);
				}
			})
		})
	
		// 프로필 변경 버튼을 클릭했을 때
		$("#changeProfile").on("click", function() {
			$("#file").removeClass("d-none"); // 파일 태그 보이기
			$("#profile_message").attr({ readonly : false }).focus(); // 메세지 입력창 활성화
 			$("#changeProfile").addClass("d-none"); // 프로필변경 버튼 숨기기
			$("#saveProfile").removeClass("d-none"); // 프로필저장 버튼 보이기
		})

		// 사용자가 새로운 프로필을 선택했을때 이미지 띄워주기
		document.getElementById("file").onchange = function() {
			let reader = new FileReader(); 
			reader.readAsDataURL(this.files[0]) 
			reader.onload = function(e) {
				document.getElementById("profile_image").src = e.target.result;
			}
		}
		
		// 정보 수정  Modal관련 
		$("#modifyInfo").on("click", function(){ // 정보 수정 버튼 클릭 시 
			$("#modifyInfo").addClass("d-none");
			$("#saveInfo").removeClass("d-none");
			$("#nickname").attr("readonly", false).css("background-color","white").focus();
		});
		
		$("#saveInfo").on("click", function(){ // 수정 완료 버튼 클릭 시 
			if($("#nickname").val() == ""){
				alert("닉네임을 올바르게 입력하여 주세요.");
				return;
			}else{
				$.ajax({
					url: "/member/modifyInfo"
					, type: "post"
					, data: {nickname : $("#nickname").val()}
					, success: function(data){
						if(data == "success"){
							$("#modifyInfo").removeClass("d-none");
							$("#saveInfo").addClass("d-none");
							$("#nickname").attr("readonly", true).css("background-color","transparent");
						}else if(data == "fail"){
							alert("닉네임 수정에 실패했습니다. 다시 시도해 주세요.");
						}
					}, error: function(e){
						console.log(e);
					}
				})
			}
		})
	</script>
</body>
</html>