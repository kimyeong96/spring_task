package kh.board.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

// 클라이언트의 요청 중 /member로 시작하는 모든 요청은 이 컨트롤러가 받음
@RequestMapping("/member")
@Controller
public class MemberController {
	@Autowired
	private MemberService service;
	
	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/logout")//logout페이지 요청
	public String logout() {
		session.removeAttribute("loginSession");
		return "redirect:/";
	}
	
	// myBatis 성공
	@RequestMapping("/login") 
	@ResponseBody	// 로그인 요청 
	public String login(String id, String pw) throws Exception{
		System.out.println(id + " : " + pw);
		MemberDTO dto = service.login(id, pw);
		if(dto != null) {
			session.setAttribute("loginSession", dto);
			System.out.println(((MemberDTO)session.getAttribute("loginSession")).toString());
			return "success";
		}
		return "fail";
	}
	
	@RequestMapping(value = "/toSignup")//singup페이지 요청
	public String toSignup() {
		return "member/signup";
	}
	
	// myBatis 성공
	@RequestMapping(value = "/checkLogin") //ID중복검사 요청
	@ResponseBody	
	public String checkLogin(String id) throws Exception{
		 if(service.checkLogin(id)) return "available";
		 else return "unavailable";
	}
	
	// myBatis 성공
	@RequestMapping(value = "/signup") // 회원가입 요청
	public String signup(MemberDTO dto, MultipartFile file, HttpSession session) throws Exception{
		System.out.println(dto.toString());
		System.out.println("file : " + file);
		String realPath = session.getServletContext().getRealPath("profile");
		String profile_image = service.uploadProfile(file, realPath);
		dto.setProfile_image(profile_image); // 바뀐걸 볼수있게 바로 dto에 저장
		
		int rs = service.signup(dto);
		if(rs > 0) {
			System.out.println("회원가입 성공");
		}else {
			System.out.println("회원가입 실패");
		}
		return "redirect:/";
	}
	
	
	// myBatis 성공
	@RequestMapping(value = "/modifyProfile")// 프로필 수정 요청
	@ResponseBody
	public String modifyProfile(String profile_message, MultipartFile file) throws Exception{
		System.out.println("profile_message : " + profile_message);
		System.out.println("file : " + file);
		
		// 1. 서버의 profile 폴더에 새로운 프로필 사진을 업로드
		// 만약 사용자가 프로필사진을 변경하지 않았다면(업로드 X)
		// 새로운 프로필 사진을 업로드 X 
		if(!file.isEmpty()) { // 넘어온 파일이 있다면
			String realPath = session.getServletContext().getRealPath("profile");
			// 파일을 업로드하는 service 의 메서드를 호출하고 반환값으로 실제 저장된 파일명을 반환
			String profile_image = service.uploadProfile(file, realPath);
			// loginSession 안에 들어있는 dto의 profile_image 멤버필드 값을 새롭게 업로드된 파일명으로 변경
			((MemberDTO)session.getAttribute("loginSession")).setProfile_image(profile_image);
		}// 만약 사용자가 프로필 사진 수정을 안했다면(파일 업로드 X) == 원래의 값을 유지
		// loginSession에 DTO -> profile_image -> 사용자가 원래 가지고 있는 프로필사진의 이름값 
		
		// 넘어온 변경된 프로필 메세지도 loginSession의 dto에 다시 셋팅
		((MemberDTO)session.getAttribute("loginSession")).setProfile_message(profile_message);
		
		// 2. member테이블의 현재 프로필 수정중인 멤버의 데이터를 수정(프로필메세지,프로필사진)
		// update member set profile_message=?, profile_image=? where id=?;
		int rs = service.modifyProfile((MemberDTO)session.getAttribute("loginSession"));
		
		if(rs > 0) {
			System.out.println("myBatis 회원 정보 수정 완료");
			return "success";
		}else {
			System.out.println("myBatis 회원 정보 수정 실패");
			return "fail";
		}
	}	
	
	// myBatis 성공
	@RequestMapping(value = "/modifyInfo") // 정보 수정 요청
	@ResponseBody	
	public String modifyInfo(String nickname) throws Exception{
		System.out.println("수정할 nickname : " + nickname);
		
		int rs = service.modifyInfo(((MemberDTO)session.getAttribute("loginSession")).getId(), nickname);
		if (rs > 0) {
			((MemberDTO)session.getAttribute("loginSession")).setNickname(nickname);
			return "success";
		}
		else return "fail";
	}
	
	@RequestMapping(value = "/toWelcome")//welcome페이지 요청
	public String toWelcome() {
		System.out.println("welcome 페이지 요청");
		return "member/welcome";
	}
	
	@ExceptionHandler
	public String toError(Exception e) {
		System.out.println("예외 발생");
		e.printStackTrace();
		return "redirect:/toError";
	}
}
