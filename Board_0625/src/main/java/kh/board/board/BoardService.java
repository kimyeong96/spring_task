package kh.board.board;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kh.board.member.MemberDAO;
import kh.board.member.MemberDTO;

@Service
public class BoardService {
	
	@Autowired
	private BoardDAO dao;
	
	// 사진 파일 업로드하기
	public String uploadBoardImage(MultipartFile multipartFile, String realPath) throws Exception {
		
		String sys_name = "";

		// profile 이라는 폴더 생성
		File realPathFile = new File(realPath);
		// 해당 경로에 profile 폴더가 존재하지 않는다면 폴더 만들기
		if (!realPathFile.exists()) {
			realPathFile.mkdir();
		}

		if (!multipartFile.isEmpty()) {
			String ori_name = multipartFile.getOriginalFilename();
			sys_name = UUID.randomUUID() + "_" + ori_name;
			multipartFile.transferTo(new File(realPath + File.separator + sys_name));
		}

		return sys_name;
	}
	
	// 게시글 등록
	public String registerBoard(BoardDTO dto) throws Exception{
		int rs = dao.insert(dto);
		if(rs > 0) {
			System.out.println("사진 등록 성공");
			return "success";
		}else {
			System.out.println("사진 등록 실패");
			return "fail";
		}
	}
	
	// 게시글 등록시 사진 등록
	public String registerBoardImage(String board_image) throws Exception{
		int rs = dao.insertImage(board_image);
		if(rs > 0) {
			System.out.println("게시글 사진 등록 성공");
			return "success";
		}else {
			System.out.println("게시글 사진 등록 실패");
			return "fail";
		}	
	}
	
	// 해당 번호로 게시글 내용 가져오기 selectBySeqBoard
	public ArrayList<BoardDTO> selectBySeqBoard(int seq_board) throws Exception{
		ArrayList<BoardDTO> list = dao.selectBySeqBoard(seq_board);
		return list;
	}
	
	// 해당 번호로 게시글 내용 가져오기 selectBySeqBoard
	public ArrayList<BoardImageDTO> selectImageBySeqBoard(int seq_board) throws Exception{
		ArrayList<BoardImageDTO> imageList = dao.selectImageBySeqBoard(seq_board);
		return imageList;
	}
	
	
	
	
}
