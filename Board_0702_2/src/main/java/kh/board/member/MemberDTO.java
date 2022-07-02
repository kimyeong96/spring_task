package kh.board.member;

public class MemberDTO {
	private String id;
	private String pw;
	private String nickname;
	private String profile_message;
	private String profile_image;
	
	public MemberDTO() {}
	public MemberDTO(String id, String pw, String nickname, String profile_message, String profile_image) {
		super();
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.profile_message = profile_message;
		this.profile_image = profile_image;
	}

	public String getId() {	return id;}
	public void setId(String id) {	this.id = id;}
	public String getPw() {	return pw;}
	public void setPw(String pw) {	this.pw = pw;}
	public String getNickname() {return nickname;}
	public void setNickname(String nickname) {	this.nickname = nickname;}
	public String getProfile_message() {return profile_message;}
	public void setProfile_message(String profile_message) {this.profile_message = profile_message;	}
	public String getProfile_image() {return profile_image;}
	public void setProfile_image(String profile_image) {this.profile_image = profile_image;}

	@Override
	public String toString() {
		return id + " : " + pw + " : " + nickname + " : " + profile_message + " : " + profile_image;
	}
	
	
}
