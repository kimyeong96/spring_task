package kh.board.board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	
	@Autowired
	private BasicDataSource bds;
	
	
	// 게시판 모든 정보 가져오기
	public ArrayList<BoardDTO> selectAll() throws Exception {
		String sql = "select * from board order by seq_board desc";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			ArrayList<BoardDTO> list = new ArrayList<>();
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int seq_board = rs.getInt("seq_board");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writer_nickname = rs.getString("writer_nickname");
				String writer_id = rs.getString("writer_id");
				int view_count = rs.getInt("view_count");
				String written_date = getStringDate(rs.getDate("written_date"));
				list.add(new BoardDTO(seq_board,title,content,writer_nickname,writer_id,view_count,written_date));
			}
			return list;
		}
 	}
	
	// 게시글 등록
	public int insert(BoardDTO dto) throws Exception {
		String sql = "insert into board values(seq_board.nextval, ?, ?, ?, ? , default, sysdate)";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getWriter_nickname());
			pstmt.setString(4, dto.getWriter_id());
			
			int rs = pstmt.executeUpdate();
			return rs;
		}
		
	}
	
	// 게시글 등록시 사진도 등록
	public int insertImage(String board_image) throws Exception {
		String sql = "insert into boardImage values (seq_board.currval, ?)";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, board_image);
			int rs = pstmt.executeUpdate();
			return rs;
		}
		
	}
	
	// 번호로 해당 게시글 내용 가져오기 
	public ArrayList<BoardDTO> selectBySeqBoard(int seq_board) throws Exception {
		String sql = "select * from board where seq_board = ?";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setInt(1, seq_board);
			ArrayList<BoardDTO> list = new ArrayList<>();
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writer_nickname = rs.getString("writer_nickname");
				String writer_id = rs.getString("writer_id");
				int view_count = rs.getInt("view_count");
				String written_date = getStringDate(rs.getDate("written_date"));
				list.add(new BoardDTO(seq_board,title,content,writer_nickname,writer_id,view_count,written_date));
			}
			return list;
		}
	}
	
	// 번호로 해당 게시글 이미지 가져오기 
		public ArrayList<BoardImageDTO> selectImageBySeqBoard(int seq_board) throws Exception {
			String sql = "select board_image from boardImage where seq_board = ?";
			
			try(Connection con = bds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
				
				pstmt.setInt(1, seq_board);
				ArrayList<BoardImageDTO> imageList = new ArrayList<>();
				
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					String board_image = rs.getString("board_image");
					imageList.add(new BoardImageDTO(seq_board, board_image));
				}
				return imageList;
			}
		}
	
	
	
	// 날짜 타입 변경
		public String getStringDate(Date date) {
			// 1900년 02월 02일 00시 00분 00초
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
			return sdf.format(date);
		}
	
	
	
	
	
}
