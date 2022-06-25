<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome!</title>
 <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
 <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-kjU+l4N0Yf4ZOJErLsIcvOU2qSb74wXpOhqTvwVx3OElZRweTnQ6d31fXEoRD1Jy" crossorigin="anonymous"></script>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
<style>
.container {
	height: 500px;
	width:1000px;
	background: linear-gradient(#F9D976, #F39F86);
}

.profileContent {
	height: 300px;
}

.profileIcon {
	border-radius: 50%;
	width: 180px;
	height: 180px;
	overflow: hidden;
}

#profile_image {
	widht: 180px;
	height: 180px;
}
</style>

</head>
<body>
	<div class="container mt-2">
		<form id="profileForm">
		
		${loginSession}
			<!--프로필 영역-->
			<div class="row profileContent">
				<div class="col d-flex flex-column mt-1 justify-content-center align-items-center">
					<div class="profileIcon">
						<!-- 사용자가 사진을 등록할 때와 안했을 때의 조건문 -->
						<c:choose>
							<c:when test="${empty loginSession.profile_image}">
								<img src="/resources/images/default_profile.png"
									id="profile_image">
							</c:when>
							<c:otherwise>
								<img src="/profile/${loginSession.profile_image}"
									id="profile_image">
							</c:otherwise>
						</c:choose>
					</div>

					<!-- 파일 태그 숨겨두기 -->
					<div class="d-flex justify-content-center">
						<input type="file" class="form-control w-100 d-none" id="file"
							name="file">
					</div>

					<!-- 프로필 메세지가 있을 때와 없을 때 구분-->
					<c:choose>
						<c:when test="${empty loginSession.profile_message}">
							<input type="text" value="상태메세지가 없습니다." name="profile_message"
								class="mt-3 form-control w-50 text-center" id="profile_message"
								readonly>
						</c:when>
						<c:otherwise>
							<input type="text" value="${loginSession.profile_message}"
								name="profile_message" class="mt-3 form-control w-50 text-center"
								id="profile_message" readonly>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form>
		
		<!--버튼 영역-->
		<div class="row mt-3 ">
			<div class="col d-flex justify-content-evenly m-4">
				<button type="button" class="btn btn-primary btn-lg" id="changeProfile">프로필변경</button>
				<button type="button" class="btn btn-danger btn-lg d-none" id="saveProfile">프로필 저장</button>
				<button type="button" class="btn btn-secondary btn-lg" id="logout">로그아웃</button>
				<button type="button" class="btn btn-success btn-lg" id="myPage">내정보</button>
				<button type="button" class="btn btn-danger btn-lg" id="board">게시판</button>
			</div>
		</div>
		
		<!-- 모달 -->
		<div class="modal" tabindex="-1" id="modalForm">
		<form>
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">내정보</h5>
						<button type="button" class="btn-close closeBtn"
							data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div class="form-group mt-2">
							<label>아이디</label> 
							<input type="text" class="form-control" id="myPageId" name="id" value="${loginSession.id}" >
						</div>
						<div class="form-group mt-2">
							<label>닉네임</label> 
							<input type="text" class="form-control" id="myPageNickname" name="nickname" value="${loginSession.nickname}" >
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary closeBtn"
							data-bs-dismiss="modal">닫기</button>
						<button type="button" class="btn btn-warning modifyBtn">수정</button>
						<button type="button" class="btn btn-primary completeBtn d-none">완료</button>
					</div>
				</div>
			</div>
		</form>
		</div>

	</div>
</body>

<script>
	// 로그아웃 버튼 클릭시
	document.getElementById("logout").onclick = function() {
		location.href = "/member/logout";
	};

	// 프로필 변경 클릭시 
	$("#changeProfile").on("click", function() {
		// 감춰있던 파일태그 보여주기(d-none 없애기)
		$("#file").removeClass("d-none");

		// readonly 없애기
		$("#profile_message").attr({
			readonly : false
		}).focus();

		// 프로필변경 안보이게 하기 
		$("#changeProfile").addClass("d-none");

		// 프로필 저장 보이게 하기(d-none 없애기)
		$("#saveProfile").removeClass("d-none");

		// 사용자가 새로운 프로필을 선택했을 때 이미지 띄워주기 
		document.getElementById("file").onchange = function() {
			let reader = new FileReader(); // 사용자가 파일태그를 이용해 파일을 선택했을 때 사용자의 로컬에 있는 파일의 정보를 브라우저에서 사용 가능하게끔 해주는 클래스(객체)
			reader.readAsDataURL(this.files[0]) // -> 인자값으로 file 객체
			reader.onload = function(e) {
				console.log("e.target ", e.target.result);
				document.getElementById("profile_image").src = e.target.result;
			}
		}
	})

	$("#saveProfile").on("click", function() {
		// 프로필 저장 안보이게 하기
		$("#saveProfile").addClass("d-none");

		// 프로필변경 보이게 하기 
		$("#changeProfile").removeClass("d-none");

		// 파일 태그 숨기기 
		$("#file").addClass("d-none");

		// 메제시 입력창 다시 readonly적용 
		$("#profile_message").attr("readonly", true);

		// jquery의 serialize 함수를 이용해서 form을 전송할 수 있는 형태로 변환
		// 파일을 전송해야하는 경우에는 serialize로 데이터를 변환해도 파일데이터가 정상적으로 변환 x
		// 자바스크립트의 FormData 객체에 우리가 만들어준 form을 자바스크립트 객체로 넘겨서 만든 변수를 ajax의 data 영역에 셋팅

		console.log(document.getElementById("profileForm"));
		let data = new FormData(document.getElementById("profileForm"));

		$.ajax({
			url : "/member/modifyProfile",
			type : "post", // file 데이터를 보낼때는 post로 해야한다
			contentType : false,
			processData : false,
			enctype : "multipart/form-data",
			data : data, // 위에서 생성한 form // 사진(multipart)와 input(profile_message)가 data에 담겨있다.
			success : function(data) {
				if (data == "success") {
					alert("수정에 성공했습니다.");
				} else if (data == "fail") {
					alert("수정에 실패했습니다. 다시 시도해주세요");
				}
			},
			error : function(e) {
				console.log(e);
			}
		})
	})
	
// 내정보 
// 모달 열기
$(document).ready(function() {
	$("#myPage").on("click", function() {
		$('#modalForm').modal('show'); // 모달 보이게 하기 
	})
	
	// 닫기 버튼
	$(".closeBtn").on("click", function() {
		$('#modalForm').modal('hide'); // 모달 숨기기 
	})

	// 내 정보에서 수정 버튼 누르기
	$(".modifyBtn").on("click", function() {
		$(".modifyBtn").addClass("d-none"); // 수정 버튼 안보이게 하기 
		$(".completeBtn").removeClass("d-none"); // 완료 버튼 보이게 하기 
	})
	
	// 내 정보에서 완료 버튼 누르기
	$(".completeBtn").on("click", function() {
		
		$.ajax({
			url:"/member/updateMyInfo",
			type:"post",
			data: {"myPageId" : $("#myPageId").val(), "myPageNickname" : $("#myPageNickname").val()},
			success:function(data) {
				console.log(data);
			},
			error:function(e) {
				console.log(e);
			}
		})
		
		$(".modifyBtn").removeClass("d-none"); // 수정 버튼 보이게 하기
		$(".completeBtn").addClass("d-none"); // 완료 버튼 안보이게 하기 
		$('#modalForm').modal('hide'); // 모달 숨기기 
		alert("닉네임변경을 성공하였습니다.")
	})
});

	
// 게시판으로 이동 
$("#board").on("click",function() {
	location.href = "/member/toBoard";
})
	
	
	
</script>








</html>