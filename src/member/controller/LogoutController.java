package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;

public class LogoutController implements CommandHandler {

	//필드
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("LogoutHandler의 process()호출");
		HttpSession session = request.getSession();
		if(session!=null) {
			session.invalidate(); //세션종료
		}
		response.sendRedirect("/index.jsp");
		return null;
	}

}
