package kh.board.board;

public class BoardImageDTO {
    private int seq_board;
    private String board_image;
    
    public BoardImageDTO() {};
	public BoardImageDTO(int seq_board, String board_image) {
		super();
		this.seq_board = seq_board;
		this.board_image = board_image;
	}
	
	public int getSeq_board() {
		return seq_board;
	}
	public void setSeq_board(int seq_board) {
		this.seq_board = seq_board;
	}
	public String getBoard_image() {
		return board_image;
	}
	public void setBoard_image(String board_image) {
		this.board_image = board_image;
	}
	@Override
	public String toString() {
		return "seq_board=" + seq_board + ", board_image=" + board_image;
	}
    
    
    
}
