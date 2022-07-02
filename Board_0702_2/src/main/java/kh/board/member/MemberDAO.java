package kh.board.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	@Autowired
	private BasicDataSource bds;

	@Autowired
	private SqlSession session;
	
	// 로그인 유효성 검사
	// myBatis 성공
	public MemberDTO login(String id, String pw) throws Exception {
//		String sql = "select * from member where id=? and pw=?";
//		try(Connection con = bds.getConnection();
//				PreparedStatement pstmt = con.prepareStatement(sql)){
//			pstmt.setString(1, id);
//			pstmt.setString(2, pw);
//
//			ResultSet rs = pstmt.executeQuery();
//			if(rs.next()) {
//				return new MemberDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
//			}
//			return null;
//		}
		
		// myBatis
		Map <String,String> map = new HashMap<>();
		map.put("id", id);
		map.put("pw", pw);
		return session.selectOne("memberMapper.login", map);
	}

	// 아이디 중복확인
	// myBatis 성공
	public boolean checkLogin(String id) throws Exception {
		/*
		 * String sql = "select count(*) from member where id=?"; try(Connection con =
		 * bds.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)){
		 * pstmt.setString(1, id);
		 * 
		 * ResultSet rs = pstmt.executeQuery(); rs.next(); int result = rs.getInt(1);
		 * if(result == 1) return false; // 중복 아이디라면 false 반환 else return true;// 사용가능
		 * 아이디라면 true 반환 }
		 */
		
		// myBatis
		MemberDTO dto = session.selectOne("memberMapper.checkLogin",id);
		if(dto == null) { // dto 값이 비었을 때 -> 사용 가능할 때
			System.out.println("null 도출 -> 아이디 사용 가능");
			return true;
		}else { // dto 값이 있을 때 -> 사용 불가능 할 때
			System.out.println("MemberDAO에서 호출 : " + dto.toString());
			return false;
		}
		
	}

	// 프로필 수정
	// myBatis 성공
	public int modifyProfile(MemberDTO dto) throws Exception{
//		String sql = "update member set profile_message=?, profile_image=? where id=?";
//		try(Connection con = bds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql)){
//			
//			pstmt.setString(1, dto.getProfile_message());
//			pstmt.setString(2, dto.getProfile_image());
//			pstmt.setString(3, dto.getId());
//			return pstmt.executeUpdate();			
//		}
		
		// myBatis
		int rs = session.update("memberMapper.update", dto);
		if(rs > 0) {
			System.out.println("MemberDAO : myBatis 회원정보 수정 : " + rs);
			return 1;
		}
		else {
			return 0;
		}
		
	}
	
	// 정보 수정
	// myBatis 성공
	public int modifyInfo(String id, String nickname) throws Exception {
//		String sql = "update member set nickname=? where id=?";
//		try(Connection con = bds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql)){
//			pstmt.setString(1, nickname);
//			pstmt.setString(2, id);
//
//			return pstmt.executeUpdate();
//		}
		Map<String,String> map = new HashMap<>();
		map.put("id", id);
		map.put("nickname", nickname);
		int rs = session.update("memberMapper.updateInfo", map);
		if(rs > 0) {
			System.out.println("myBatis 정보 수정 성공");
			return 1;
		}else {
			System.out.println("myBatis 정보 수정 실패");
			return 0;
		}
	}

	// 회원가입
	// myBatis 성공
	public int insert(MemberDTO dto) throws Exception{
//		String sql = "insert into member values(?,?,?,null,?)";
//		try(Connection con = bds.getConnection();
//				PreparedStatement pstmt = con.prepareStatement(sql)){
//
//			pstmt.setString(1, dto.getId());
//			pstmt.setString(2, dto.getPw());
//			pstmt.setString(3, dto.getNickname());
//			pstmt.setString(4, dto.getProfile_image());
//
//			return pstmt.executeUpdate();
//		}
		
		// myBatis
		int rs = session.insert("memberMapper.insert", dto);
		if(rs > 0) {
			return 1;
		}else {
			return 0;
		}
		
	}
}
