package member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.exception.LoginFailException;
import member.model.User;
import member.service.LoginService;
import mvc.command.CommandHandler;


public class LoginController implements CommandHandler {

	private static final String FORM_VIEW="view/member/loginForm.jsp";
	private LoginService loginService = new LoginService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("LoginHandler�쓽 process()�샇異�");
		
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		}
		else if(request.getMethod().equalsIgnoreCase("POST")){
			return processSubmit(request, response);
		}
		else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
		
	private String processForm(HttpServletRequest request, HttpServletResponse response){
		System.out.println("processForm()吏꾩엯");
		return FORM_VIEW;
			
	}
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("processSubmit()吏꾩엯");

		String mId = trim(request.getParameter("mId"));
		System.out.println(mId);
		String mPwd = trim(request.getParameter("mPwd"));
		System.out.println(mPwd);

		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		request.setAttribute("errors", errors);
		
		if(mId == null || mId.isEmpty())
			errors.put("mId", Boolean.TRUE);
		if(mPwd == null || mPwd.isEmpty())
			errors.put("mPwd", Boolean.TRUE);
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}

		try {
			User user = loginService.login(mId, mPwd);
			request.getSession().setAttribute("authUser", user); //String name, Object value
			response.sendRedirect(request.getContextPath()+"/view/member/loginSuccess.jsp");
			return null;
			
		}catch(LoginFailException e) {
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			return FORM_VIEW;
		}
	}
		
	private String trim(String str) {
		return (str==null)? null:str.trim();
	}
	
}
