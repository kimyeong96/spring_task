package kh.board.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

// D.S 과 controller 사이에서 요청/응답을 가로채서 추가적인 처리를 해줄 수 있는 클래스
// loginSession 이라는 key값이 session 에 들어있는지 아닌지를 검사
public class AuthLoginInterceptor implements HandlerInterceptor{
	
	/* preHandle() : D.S이 controller에게 요청을 보내기 전에 실행되는 메서드
	 * postHandle() : controller가 D.S에게 응답을 보내기 전에 실행되는 메서드
	 * 
	 * -> true 반환 : Interceptor가 동작한 후에 Controller/D.S 요청/응답을 정상적으로 전달
	 * -> false 반환 : Interceptor 동작 후 Controller/D.S 요청/응답을 전달 x 
	 * */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		System.out.println("로그인 검사 인터셉터 도착");
		// 사용자의 로그인 여부 검사
		if(request.getSession().getAttribute("loginSession") == null) {
			// 사용자 로그인이 안되어있음.
			System.out.println("로그인 안됨");
			// 로그인이 안되어있으면 /로 redirect 시켜버릴 것 
			response.sendRedirect("/");
			return false;
		}
		return true;
	}
}
