package kh.board.board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired
	private BasicDataSource bds;
	
	@Autowired
	private SqlSession session;
	
	//myBatis 성공
	public void modify(BoardDTO dto) throws Exception{ // 게시글 수정
//		String sql = "update board set title=?,content=? where seq_board=?";
//		try(Connection con = bds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql)){
//			
//			pstmt.setString(1, dto.getTitle());
//			pstmt.setString(2, dto.getContent());
//			pstmt.setInt(3, dto.getSeq_board());
//			pstmt.executeUpdate();
//		}
		//myBatis 
		int rs = session.update("boardMapper.modify", dto);
		if(rs > 0) System.out.println("myBatis 게시글 수정 성공");
		else System.out.println("myBatis 게시글 수정 삭제");
	}
	
	// myBatis 성공
	public int delete(int seq_board) throws Exception{ // 게시글 삭제 요청
//		String sql = "delete from board where seq_board=?";
//		try(Connection con = bds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql)){
//			
//			pstmt.setInt(1, seq_board);
//			return pstmt.executeUpdate();
//		}
		
		// myBatis 
		int rs = session.delete("boardMapper.delete",seq_board);
		if(rs > 0) {
			System.out.println("myBatis 게시글 삭제 성공 ");
			return 1;
		}
		else {
			System.out.println("myBatis 게시글 삭제 실패 ");
			return 0;
		}
	}
	
	// myBatis 성공
	public List<BoardDTO> selectAll() throws Exception{ // 모든 게시글 조회
//		String sql = "select * from board";
//		try(Connection con = bds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql)){
//			
//			ResultSet rs = pstmt.executeQuery();
//			// ArrayList가 List를 상속받기 때문에 List타입 참조변수에 ArrayList인스턴스를 담을 수 있음.
//			List<BoardDTO> list = new ArrayList<>();
//			while(rs.next()) {
//				int seq_board = rs.getInt("seq_board");
//				String title = rs.getString("title");
//				String content = rs.getString("content");
//				String writer_nickname = rs.getString("writer_nickname");
//				String writer_id = rs.getString("writer_id");
//				int view_count = rs.getInt("view_count");
//				Date written_date = rs.getDate("written_date");
//				list.add(new BoardDTO(seq_board,title,content,writer_nickname,writer_id,view_count,written_date));
//			}
//			return list;
//		}
		
		// myBatis
		List<BoardDTO> list = session.selectList("boardMapper.selectBoard");
		return list;
	}
	
	// myBatis 성공
	public BoardDTO selectOne(int seq_board) throws Exception{ // 하나의 게시글 조회
//		String sql = "select * from board where seq_board=?";
//		try(Connection con = bds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql)){
//			
//			pstmt.setInt(1, seq_board);
//			ResultSet rs = pstmt.executeQuery();
//
//			if(rs.next()) {
//				String title = rs.getString("title");
//				String content = rs.getString("content");
//				String writer_nickname = rs.getString("writer_nickname");
//				String writer_id = rs.getString("writer_id");
//				int view_count = rs.getInt("view_count");
//				Date written_date = rs.getDate("written_date");
//				return new BoardDTO(seq_board,title,content,writer_nickname,writer_id,view_count,written_date);
//			}
//			return null;
//		}
		
		// myBatis
		return session.selectOne("boardMapper.selectOne", seq_board);
	}
	
	// myBatis 성공
	public void insert(BoardDTO dto) throws Exception{ // 게시글 저장
//		String sql = "insert into board values(?,?,?,?,?,0,sysdate)";
//		try(Connection con = bds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql)){
//			pstmt.setInt(1, dto.getSeq_board());
//			pstmt.setString(2, dto.getTitle());
//			pstmt.setString(3, dto.getContent());
//			pstmt.setString(4, dto.getWriter_nickname());
//			pstmt.setString(5, dto.getWriter_id());
//			pstmt.executeUpdate();
//		}
		
		// myBatis
		int rs = session.insert("boardMapper.boardInsert", dto);
		if(rs > 0) System.out.println("myBatis : 게시글 작성 성공");
		else System.out.println("myBatis : 게시글 작성 실패");
	}
	
	public int selectSeq() throws Exception{ // 새로운 게시글 시퀀스 번호 생성
		String sql = "select seq_board.nextval from dual";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)){
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);// 생성된 시퀀스 번호 반환			
		}
	}
}
