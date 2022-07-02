package kh.board.utils;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kh.board.file.FileService;

@Component
public class Scheduler {
	@Autowired
	private FileService service;
	@Autowired
	private ServletContext sCtx;

	public Scheduler() {
		System.out.println("스케쥴러 생성");
	}
	
	/*@Scheduled : 해당 메서드를 인자로 넘겨준 표현식에 따른 주기마다 실행시키겠다는 어노테이션 
	cron 표현식 : 스케쥴러에게 어느 주기마다 스케쥴러를 동작시킬건지에 대한 정보값을 넘겨주는 시간 표현식
	초 : 0~59
	분 : 0~59
	시 : 0~23
	일 : 1~31
	월 : 1~12
	요일	: 0(일요일)~6(토요일) / MON, WED, FRI
	* : 모든 시간을 대체 
	
	0/3 * * * * * : 3초마다
	* 0/5 * * * * : 5분마다
	* * 0/1 * * * : 1시간마다
	
	*/
	//@Scheduled(cron="30 * * * * *")
	/*
	 * public void test() { System.out.println("매분 30초마다 스케쥴러 동작"); }
	 * 
	 * //@Scheduled(cron="0/3 * * * * *") public void test2() {
	 * System.out.println("3초마다 스케쥴러 동작"); }
	 * 
	 * @Scheduled(cron="0 1 0 0/7 * *") public void deleteFiles() throws Exception{
	 * String path = sCtx.getRealPath("board"); service.deleteFiles(path); }
	 */
	
}
