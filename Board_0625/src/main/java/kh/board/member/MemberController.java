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
	// 어디에서나 사용 가능하게 생성
	@Autowired
	private MemberService service;
	@Autowired // session은 bean 생성 안해도 spring에서 자동적으로 만들어서 container에 넣어줌
	private HttpSession session; 

	@RequestMapping("/login") 
	@ResponseBody	// 로그인 요청 
	public String login(String id, String pw) throws Exception{
		System.out.println(id + " : " + pw);
		MemberDTO dto = service.login(id, pw);
		if(dto != null) {
			session.setAttribute("loginSession", dto); // 해당 아이디에 관한 정보를 다 다 보내준다.
			return "success";
		}
		return "fail";
	}
	
	@RequestMapping(value = "/toSignup")//singup페이지 요청
	public String toSignup() {
		return "member/signup";
	}
	
	@RequestMapping(value = "/checkLogin") //ID중복검사 요청
	@ResponseBody	
	public String checkLogin(String id) throws Exception{
		if(service.checkLogin(id)) return "available";
		else return "unavailable";
	}
	
	@RequestMapping(value = "/signup") // 회원가입 요청
	public String signup(MemberDTO dto, MultipartFile file, HttpSession session) throws Exception{
		System.out.println(dto.toString());
		System.out.println("file : " + file);
		
		// 프로필 사진이 저장될 경로 설정
		// C:\spring_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\ + profile
		String realPath = session.getServletContext().getRealPath("profile");
		String profile_image = service.uploadProfile(file, realPath); // sys_name이 return 된다
		dto.setProfile_image(profile_image);
		
		service.signup(dto);
		return "redirect:/";
	}
	
	//welcome페이지 요청
	@RequestMapping(value = "/toWelcome")
	public String toWelcome() {
		System.out.println("welcome 페이지 요청");
		return "member/welcome";
	}
	
	// 로그아웃 
	// 세션을 종료 시키고 홈으로 페이지 이동
	@RequestMapping(value ="/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	// 프로필 수정 요청
	// 값을 받을 때 Multipart와 profile_message가 넘어온다
	@ResponseBody
	@RequestMapping(value="/modifyProfile")
	public String modifyProfile(String profile_message, MultipartFile file) throws Exception{
		System.out.println("profile_message : " + profile_message);
		System.out.println("MultipartFile : " + file);
		
		// 만약에 file이 비어있지 않다면 즉 넘어온 파일이 있다면
		if(!file.isEmpty()) {
			String realPath = session.getServletContext().getRealPath("profile");
			
			// 파일을 업로드 하는 service의 메서드를 호출하고 반환값으로 실제 저장된 파일명을 반환
			String profile_image = service.uploadProfile(file,realPath); // sys_name이 return 된다.
			
			// 현재의 loginSession 안에 들어있는 dto의 profile_image 멤버필드 값을 새롭게 업로드된 파일명으로 변경
			((MemberDTO)session.getAttribute("loginSession")).setProfile_image(profile_image);
		}
		// 만약 사용자가 사진 변경을 안했다면 즉 file 이 존재하지 않다면 -> 데이터의 default값 유지(기본 아이콘)
		// loginSession에 dto -> profile_image -> 사용자가 원래 가지고 있는 프로필사진의 이름값
		
		// 넘어온 변경된 프로필 메서드도 loginSession의 dto에 다시 셋팅(profile_message)
		((MemberDTO)session.getAttribute("loginSession")).setProfile_message(profile_message);

		// MemberDTO 값을 넘겨준다. session을 넘겨주는게 아니다
		int rs = service.modifyProfile((MemberDTO)session.getAttribute("loginSession"));
		if(rs > 0) {
			return "success";
		}else {
			return "fail";
		}
	}

	// 내정보에서 닉네임 수정(myInfo)
	@ResponseBody
	@RequestMapping(value = "/updateMyInfo")
	public String updateMyInfo(String myPageId, String myPageNickname) throws Exception {

		// 세션 값 변경(바로 확인 할 수 있게)
		//
		((MemberDTO) session.getAttribute("loginSession")).setNickname(myPageNickname);

		String result = service.modifyInfo(myPageId, myPageNickname); // success or fail
		return result;
	}
	
	@RequestMapping(value ="/toBoard")
	public String toBoard() throws Exception {
		System.out.println("MemberController에서 boardController로 Session 보내줌");
		return "redirect:/board/toBoard";
	}
	
	
	
	@ExceptionHandler
	public String toError(Exception e) {
		System.out.println("예외 발생");
		e.printStackTrace();
		return "redirect:/toError";
	}
}
