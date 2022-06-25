package kh.board.board;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kh.board.member.MemberDAO;
import kh.board.member.MemberDTO;
import kh.board.member.MemberService;

@RequestMapping(value = "/board")
@Controller
public class BoardController {
	
	@Autowired
	private BoardDAO dao;
	
	@Autowired // session은 bean 생성 안해도 spring에서 자동적으로 만들어서 container에 넣어줌
	private HttpSession session; 
	
	@Autowired
	private BoardService service;
	
	@RequestMapping(value="/toBoard")
	public String toBoard(Model model) throws Exception{
		
		System.out.println(((MemberDTO) session.getAttribute("loginSession")));
		
		ArrayList<BoardDTO> list = dao.selectAll();
		// model 로 값넘기기 
		model.addAttribute("list", list);
		return "board/board";
	}
	
	
	// 로그아웃 
	@RequestMapping(value="/logout")
	public String logout() throws Exception {
		// 세션 죽이기
		session.invalidate();
		// 홈으로 이동
		return "redirect:/";
		
	}
	
	// 뒤로가기 버튼
	@RequestMapping(value="/back")
	public String back() throws Exception {
		System.out.println("잘 들어옴요");
		return "redirect:/board/toBoard";
	}
	
	// 글쓰기 버튼
	@RequestMapping(value = "/boardWrite")
	public String boardWrite() throws Exception {
		return "board/writeBoard";
	}
	
	// 글등록 
	@ResponseBody
	@RequestMapping(value ="/registerBoard")
	public String registerBoard(String title, String content, ArrayList<MultipartFile> file ) throws Exception {
		
		// 세션으로 부터 아이디, 닉네임 얻어오기
		String writer_id = ((MemberDTO)session.getAttribute("loginSession")).getId();
		String writer_nickname = ((MemberDTO)session.getAttribute("loginSession")).getNickname();
		
		// dto에 값넣어주기
		BoardDTO dto = new BoardDTO(0,title,content,writer_nickname,writer_id,0,null);
		
		// 빈 값으로 들어올 경우
		String board_image = "";
		
		// 미리 이미지의 결과를 선언해주기 return 문에서 판단하기 위해
		String imageResult = "";
		
		// 글 삽입 성공 유무
		String insertResult = service.registerBoard(dto);
		System.out.println("게시글 삽입 성공 유무 : " + insertResult);
		
		for (int i = 0; i < file.size(); i++) {
			if (!file.get(i).isEmpty()) {
				String realPath = session.getServletContext().getRealPath("board");
				System.out.println(realPath);
				board_image = service.uploadBoardImage(file.get(i), realPath);
			}	
			//이미지 삽입
			imageResult = service.registerBoardImage(board_image);
			System.out.println("게시글 이미지 성공 유무 : " + imageResult);
		}
		
		System.out.println("board_image 입니다 : " + board_image);
		
		if(insertResult.equals("success") && imageResult.equals("success")) {
			return "success";
		}else {
			return "fail";
		}

	}
	
	// 페이지 상세보기 
	@RequestMapping(value="/detailView")
	public String detailView(int seq_board,Model model) throws Exception {
		System.out.println(seq_board);
		
		// 번호에 해당하는 게시글 내용 데이터
		ArrayList<BoardDTO> list = service.selectBySeqBoard(seq_board);
		
		// 번호에 해당하는 게시글 이미지 데이터 
		ArrayList<BoardImageDTO> imageList = service.selectImageBySeqBoard(seq_board);
		
		for(BoardImageDTO dto : imageList) {
			System.out.println(dto.toString());
		}
		
		// 값 model로 담아서 보내기
		model.addAttribute("list",list);
		model.addAttribute("imageList",imageList);
		
		return "board/detailView";
	}
	
	// 예외 처리
	@ExceptionHandler
	public String toError(Exception e) {
		System.out.println("예외 발생");
		e.printStackTrace();
		return "redirect:/toError";
	}
	
	
	
	
}
