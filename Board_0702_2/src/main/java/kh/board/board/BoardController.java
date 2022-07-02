package kh.board.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/board")
@Controller
public class BoardController {
	@Autowired
	private BoardService service;
	@Autowired
	private HttpSession session;
	
	// myBatis 성공
	@RequestMapping(value = "/toBoard")//board페이지 요청
	public String toBoard(Model model) throws Exception{
		List<BoardDTO> list = service.selectAll();
		model.addAttribute("list", list);
		return "board/board";
	}
	
	// myBatis 성공
	@RequestMapping(value = "/toDetail") // 상세보기 페이지 요청
	public String toDetail(int seq_board, Model model) throws Exception{
		System.out.println("seq_board : " + seq_board);
		Map<String, Object> map = service.selectOne(seq_board);
		System.out.println( map.get("boardDTO") );
		System.out.println( map.get("fileList") );
		model.addAttribute("map", map);
		return "board/detail";
	}
	
	
	@RequestMapping(value = "/toWrite")//write페이지 요청
	public String toWrite(){
		return "board/write";
	}
	
	// myBatis 성공
	@RequestMapping(value = "/write") // 게시글 작성 요청
	public String write(BoardDTO dto, MultipartFile[] files) throws Exception{
		System.out.println(dto.toString());
		// boardDTO 저장 / File 업로드 / fileDTO 저장
		String path = session.getServletContext().getRealPath("board");
		service.insert(dto, path, files);
		return "redirect:/board/toBoard";
	} 
	
	//myBatis 성공
	@RequestMapping(value = "/modify")// 게시글 수정 요청
	public String modify(BoardDTO dto, MultipartFile[] files, @RequestParam(value="deleteFileList[]") String[] deleteFileList) throws Exception{
		String path = session.getServletContext().getRealPath("board");	
		service.modify(dto, path, files, deleteFileList);
		return "redirect:/board/toDetail?seq_board="+dto.getSeq_board();
	}
	
	// myBatis 성공
	@RequestMapping(value = "/delete") 
	public String delete(int seq_board) throws Exception{ // 게시글 삭제 요청
		System.out.println("seq_board : " + seq_board);
		service.delete(seq_board);
		return "redirect:/board/toBoard";
	}
	
	@ExceptionHandler // 에러 처리
	public String toError(Exception e) {
		e.printStackTrace();
		return "redirect:/toError";
	}
}