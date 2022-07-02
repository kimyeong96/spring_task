package kh.board.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/file")
public class FileController {
	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/download")
	public void download(HttpServletResponse response, String ori_name, String sys_name) throws Exception{ // 파일 다운로드 요청
		System.out.println("ori_name : " + ori_name);
		System.out.println("sys_name : " + sys_name);
		
		String filePath = session.getServletContext().getRealPath("board");
		// filePath + File.seperator + sys_name
		// FileInputStream을 사용할 수 있도록 파일의 full경로를 이용해 File 객체 생성
		File targetFile = new File(filePath+File.separator+sys_name);
		System.out.println("targetFile : " + targetFile.getPath());
		
		ori_name = new String(ori_name.getBytes(), "ISO-8859-1");
		// 응답되는 값이 일반데이터가 아닌 파일 다운로드에 대한 요청이라는 것을 response header에 셋팅
		response.setHeader("Content-Disposition", "attachment; filename=\"" + ori_name + "\";");
		
		try(DataInputStream dis = new DataInputStream(new FileInputStream(targetFile));
			DataOutputStream dos = new DataOutputStream(response.getOutputStream())){
			
			byte[] arr = dis.readAllBytes();// 파일을 한번에 바이트 배열로 불러 들이기
			dos.write(arr); // 파일을 한번에 보내주기
			dos.flush();			
		}		
	}
}
