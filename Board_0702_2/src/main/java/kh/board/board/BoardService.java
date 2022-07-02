package kh.board.board;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kh.board.file.FileDAO;
import kh.board.file.FileDTO;

@Service
public class BoardService {
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private FileDAO fileDAO;
	
	// 서비스의 메서드는 사용자 입장에서의 하나의 기능에 해당하게끔
	/* 사용자가 글 제목을 클릭했을때(상세보기를 요청했을때) 실행되는 메서드
	 * 게시글의 데이터 가져오기 + 파일 데이터 가져오기 
	 * Map -> HashMap 클래스가 상속받는 부모클래스
	 * */
	
	// myBatis 성공
	public Map<String, Object> selectOne(int seq_board) throws Exception{ // 하나의 게시글 조회
		// 다른 타입의 데이터를 반환하기 위해 map를 이용
		Map<String, Object> map = new HashMap<>();
		map.put("fileList", fileDAO.selectBySeq(seq_board)); // 파일 데이터 가져오기
		map.put("boardDTO", boardDAO.selectOne(seq_board)); // 게시글 데이터 가져오기
		return map;
	}
	
	// 
	public List<BoardDTO> selectAll() throws Exception{ //게시글 전체 조회
		return boardDAO.selectAll();
	}
	
	// myBatis 성공
	public void insert(BoardDTO dto, String path, MultipartFile[] files) throws Exception{ // 게시글 저장
		// boarddto, realpath(서버경로), files
		// 1. 새로운 seq_board를 하나 생성 
		int seq_board = boardDAO.selectSeq();
		System.out.println("seq_board : " + seq_board);
		
		dto.setSeq_board(seq_board);
		boardDAO.insert(dto);
		
		// 만약 서버 root 에 board 폴더가 없으면 일단 board폴더 먼저 만들자.
		File realPath = new File(path);
		if(!realPath.exists()) realPath.mkdir();
		
		// files 안에 몇개의 파일이 들어있을지 예측할 수 없기 때문에
		// for문을 돌려가면서 만약 파일이 들어있다면 파일을 업로드 / DB 저장 
		System.out.println("file.length : " + files.length);
		System.out.println("file : " + files[0]);
		for(MultipartFile mf : files) {
			if(!mf.isEmpty()) { // 파일이 들어있다면 
				String ori_name = mf.getOriginalFilename();
				String sys_name = UUID.randomUUID()+"_"+ori_name;
				
				mf.transferTo(new File(path+File.separator+sys_name));
				fileDAO.insert(new FileDTO(0, seq_board, ori_name, sys_name));
			}
		}
		// 1. 사용자가 파일을 업로드했을때만 insert 
		// 2. 여러개의 파일을 업로드했는데 딱 한 파일의 데이터만 저장
		//fileDAO.insert(FILEDTO)
	}
	
	//myBatis 성공
	public void modify(BoardDTO dto, String path, MultipartFile[] files, String[] deleteFileList) throws Exception{ // 게시글 수정 요청
		// 새롭게 첨부된 파일 업로드 + DB 에 파일데이터 저장
		File realPath = new File(path);
		if(!realPath.exists()) realPath.mkdir();
		
		System.out.println("file.length : " + files.length);
		System.out.println("file : " + files[0]);
		for(MultipartFile mf : files) {
			if(!mf.isEmpty()) { // 파일이 들어있다면 
				String ori_name = mf.getOriginalFilename();
				String sys_name = UUID.randomUUID()+"_"+ori_name;
				
				mf.transferTo(new File(path+File.separator+sys_name));
				fileDAO.insert(new FileDTO(0, dto.getSeq_board(), ori_name, sys_name));
			}
		}
		
		// 수정된 게시글 정보 DB에서 수정하기 
		boardDAO.modify(dto);
		
		// 삭제 요청된 파일들을 서버 경로에서 삭제
		if(deleteFileList.length != 0) {
			for(String sys_name : deleteFileList) {
				// full 파일 경로를 만들어서 File 객체 생성 -> delete메서드로 파일 삭제
				File file = new File(path + File.separator + sys_name);
				if(file.exists()) { // 실제 그 경로의 파일이 존재하는지 체크 
					file.delete(); // 실제 서버경로에서 파일 삭제
					fileDAO.delete(sys_name); // DB 에서 파일 데이터 삭제
				}
			}
		}
	}
	
	// myBatis 성공
	public int delete(int seq_board) throws Exception{ //게시글 삭제 요청
		return boardDAO.delete(seq_board);
	}
	
	
	
	
	
	
	
	
	
	
}
