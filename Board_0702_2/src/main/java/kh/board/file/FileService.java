package kh.board.file;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
	@Autowired
	private FileDAO dao;
	
	/* files 테이블에 있는 데이터는 실제 게시글이 살아있고, 첨부가 되어 있는 파일들
	   files테이블에는 데이터가 없지만 서버의 경로에는 여전히 살아있는 파일들
	   (게시글이 삭제됐지만 여전히 삭제되지 않는 실제 파일들)
	   -> files 테이블에 있는 sys_name 모두 필요함
	   -> 서버 경로에 있는 파일(sys_name)의 리스트
	*/
	
	//myBatis 성공
	public void deleteFiles(String path) throws Exception{
		// dao 를 통해 files 테이블의 sys_name 목록 가져오기
		List<String> files_db = dao.selectSys_name();
		// 파일 객체로 서버 경로(board)안에 존재하는 모든 파일을 가져올 것
		// -> 파일 객체를 통해 파일의 이름값을 얻어낼 수 있음
		// -> 파일 객체를 통해 해당 파일을 삭제하는 것도 가능
		File[] files_root = new File(path).listFiles();
		
		// DB와 서버 경로에 파일의 목록이 있다라고 한다면
		if(files_db.size() != 0 && files_root.length != 0) {
			for(File f : files_root) { // 서버에 저장된 파일들을 모두 for문 돌리며 db와 비교확인
				// 만약 서버에 저장된 파일의 이름이 DB sys_name 리스트에 포함되어있지 않다면 
				if(!files_db.contains(f.getName())) {
					System.out.println(f.getName() + " : 삭제할 파일");
					f.delete(); // 삭제할 파일을 실제 삭제 
				}
			}
		}		
	}
}
