package kh.board.member;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/*
	dao를 통해 데이터를 추가,수정,삭제,조회해야하는 경우 메서드 생성
	혹은 요청/응답과는 별개로 추가적으로 가공해야하는 데이터가 있는 경우 
		
 	컨트롤러-> 클라이언트로부터 직접으로 요청을 받고 그 요청에 따라
	 * 추가적인 데이터 가공이 필요하거나, 혹은 DB를 통해 데이터
	 * 수정/삭제/조회/추가해야 하는 경우 controller가 직접 dao 를 호출하지 않고
	 * service 에게 그 작업을 전달함 
	 * -> 그럼 service의 호출된 메서드가 추가적으로 처리해야하는 가공이나 dao 호출을 통해
	 * 비니지스 로직을 수행하고 그에 따른 결과값만 바로 controller에게 전달해 줌.
	 * -> controller는 결과값을 받아서 판단에 따라 응답값을 어떻게 클라이언트에 되돌려
	 * 줄지 결정한다. 
	 * 
	 * 즉 controller는 클라이언트의 요청과 응답관 관련된 직접적인 일들만 처리
	 * 나머지 뒤에서 보이지 않는 일들은 서비스가 처리. 
	 * */
@Service
public class MemberService {
	@Autowired
	private MemberDAO dao;
	
	// 로그인 
	// myBatis 성공
	public MemberDTO login(String id, String pw) throws Exception{
		return dao.login(id, pw);
	}
	
	// 아이디 중복 검사
	// myBatis 성공
	public boolean checkLogin(String id) throws Exception{
		return dao.checkLogin(id);
	}
	
	// 회원가입 데이터 삽입
	// myBatis 성공
	public int signup(MemberDTO dto) throws Exception{
		return dao.insert(dto);
	}
	
	public String uploadProfile(MultipartFile file, String realPath) throws Exception{
		File realPathFile = new File(realPath);
		if(!realPathFile.exists()) realPathFile.mkdir();
		String sys_name = null;
		if(!file.isEmpty()) {
			String ori_name = file.getOriginalFilename();
			sys_name = UUID.randomUUID() + "_" + ori_name;
			file.transferTo(new File(realPath + File.separator + sys_name));
		}
		return sys_name;
	}
	
	// 프로필 변경
	// myBatis 성공
	public int modifyProfile(MemberDTO dto) throws Exception{
		return dao.modifyProfile(dto);
	}
	
	// 정보 수정
	// myBatis 성공
	public int modifyInfo(String id, String nickname) throws Exception{
		return dao.modifyInfo(id, nickname);
	}
}
