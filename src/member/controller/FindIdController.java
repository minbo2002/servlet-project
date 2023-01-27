package member.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.exception.InvalidEmailException;
import member.exception.InvalidNameException;
import member.exception.MemberNotFoundException;
import member.model.Member;
import member.service.FindIdService;
import mvc.command.CommandHandler;

public class FindIdController implements CommandHandler {

	private static final String FROM_VIEW="view/member/findIdForm.jsp";
	private FindIdService findidService = new FindIdService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		}
		else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		}
		else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
			System.out.println("FindIdHandle.processForm()");
		return FROM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		System.out.println("FindIdHandler.processSubmit()");
		
		Map<String, Boolean> errors = new HashMap<String, Boolean> ();
		request.setAttribute("errors", errors);
		
		String name = request.getParameter("name");
		String mEmail = request.getParameter("email");
		
		if(name==null || name.isEmpty()) {
			errors.put("mName", Boolean.TRUE);
		}
		if(mEmail==null || mEmail.isEmpty()) {
			errors.put("email", Boolean.TRUE);
		}
		if(!errors.isEmpty()) {
			return FROM_VIEW;
		}
		try {
			Member member = this.findidService.Findid(name, mEmail);
			request.setAttribute("member", member);
			System.out.println(member.getmId());
			return "view/member/findIdSuccess.jsp";
		}catch(InvalidNameException e) {
			errors.put("badmName", Boolean.TRUE);
			return FROM_VIEW;
		}catch(InvalidEmailException e) {
			errors.put("bademail", Boolean.TRUE);
			return FROM_VIEW;
		}catch(MemberNotFoundException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			
			return null;
		}
	}

}
