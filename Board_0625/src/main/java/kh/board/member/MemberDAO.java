package kh.board.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	@Autowired
	private BasicDataSource bds;

	// 로그인 유효성 검사
	public MemberDTO login(String id, String pw) throws Exception {
		String sql = "select * from member where id=? and pw=?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new MemberDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
			}
			return null;
		}
	}

	// 아이디 중복확인
	public boolean checkLogin(String id) throws Exception {
		String sql = "select count(*) from member where id=?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int result = rs.getInt(1);
			if(result == 1) return false; // 중복 아이디라면 false 반환
			else return true;// 사용가능 아이디라면 true 반환
		}
	}


	// 회원가입
	public int insert(MemberDTO dto) throws Exception{
		String sql = "insert into member values(?,?,?,null,?)";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)){

			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getNickname());
			pstmt.setString(4, dto.getProfile_image());

			return pstmt.executeUpdate();
		}
	}
	
	// 사용자 프로필 업데이트 
	public int modifyProfile(MemberDTO dto) throws Exception {
		String sql = "update member set profile_message =?, profile_image =? where id =?";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, dto.getProfile_message());
			pstmt.setString(2, dto.getProfile_image());
			pstmt.setString(3, dto.getId());	
			return pstmt.executeUpdate();
		}
	}
	
	// 내정보에서 수정 
	// 사용자 프로필 업데이트 
	public int modifyMyInfo(String myPageId, String myPageNickname) throws Exception {
		String sql = "update member set nickname =? where id =?";

		try (Connection con = bds.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, myPageNickname);
			pstmt.setString(2, myPageId);
			return pstmt.executeUpdate();
		}
	}
	
	
	
	
	
	
	
}
