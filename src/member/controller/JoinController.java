package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.exception.DuplicatedIdException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;

public class JoinController implements CommandHandler {

	//�븘�뱶
	private static final String FORM_VIEW="view/member/joinForm.jsp";
	
	//�깮�꽦�옄
	
	
	//硫붿꽌�뱶
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("JoinHandler�쓽 process()�샇異쒖꽦怨�");
		System.out.println("JoinHandler:request.getMethod()= "+request.getMethod());
		
		//�뤌�쓽 �슂泥� 諛⑹떇�뿉 �뵲�씪 �쉶�썝媛��엯�뤌 �슂泥�怨� 媛��엯泥섎━�슂泥��쓣 援щ텇
		if(request.getMethod().equalsIgnoreCase("GET")) { //method媛� get�씠硫�
			return processForm(request, response);//�쉶�썝媛��엯 �뤌 �슂泥�
		}else if(request.getMethod().equalsIgnoreCase("POST")) { //method媛� post�씠硫�
			return processSubmit(request, response);//媛��엯泥섎━ �슂泥�
		}else {
			//�긽�깭肄붾뱶:SC_METHOD_NOT_ALLOWED => 405(�뿀�슜�릺吏� �븡�뒗 硫붿꽌�룄): �슂泥��뿉 吏��젙�맂 諛⑸쾿�쓣 �궗�슜�븷 �닔 �뾾�떎.
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		
	}//process()
	
	//�쉶�썝媛��엯 �뤌 �슂泥�.p598 31�씪�씤
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return FORM_VIEW;
	}//processForm()
	
	//�쉶�썝媛��엯 泥섎━ �슂泥�.p598 35�씪�씤
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		System.out.println("JoinHandler而⑦듃濡ㅻ윭:processSubmit()");
		
		//1. �뙆�씪誘명꽣 �뼸湲�
		String mId = request.getParameter("mId");
		String mPwd = request.getParameter("mPwd");
		String re_mPwd = request.getParameter("re_mPwd");
		String mName = request.getParameter("mName");
		String gender = request.getParameter("gender");
		String addr = request.getParameter("addr");
		String email_id = request.getParameter("email_id");
		String email_d = request.getParameter("email_d");
		String email = email_id+"@"+email_d;
		
		//2.鍮꾩쫰�땲�뒪 濡쒖쭅 �닔�뻾<-> Service <-> DAO <-> DB & 3.model泥섎━ request媛앹껜(HttpServletRequest), session媛앹껜(HttpSession) & 4.view 吏��젙
		JoinService joinService = new JoinService();
		JoinRequest joinReq = new JoinRequest();//p.
		joinReq.setmId(mId);
		joinReq.setmPwd(mPwd);
		joinReq.setRe_mPwd(re_mPwd);
		joinReq.setmName(mName);
		joinReq.setGender(gender);
		joinReq.setAddr(addr);
		joinReq.setEmail(email);
		System.out.println("joinReq.getMemberid()= "+ joinReq.getmId());
		
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		request.setAttribute("errors", errors); //p.598 43�씪�씤
		
		//�쑀�슚�꽦 寃��궗(�븘�닔�엯�젰, 鍮꾨쾲怨� 鍮꾨쾲�옱�솗�씤 �씪移섏뿬遺� �벑)
		joinReq.validate(errors);
		/*�뿉�윭媛� �엳�쓣 寃쎌슦
		  errors.put("memberid", Boolean.TRUE);
		  errors.put("memberpwd", Boolean.TRUE);
		  errors.put("re_memberpwd", Boolean.TRUE);
		  errors.put("membername", Boolean.TRUE);
		  errors.put("notMatch", Boolean.TRUE);*/
		
		//3.model泥섎━ request媛앹껜(HttpServletRequest), session媛앹껜(HttpSession)& 4.view 吏��젙
		if(!errors.isEmpty()) { //�뿉�윭媛� 議댁옱�븯硫�
			return FORM_VIEW;
		}
		
		try {
			joinService.join(joinReq); //p.598(52 line)
			return "view/member/joinSuccess.jsp";
			
		}catch(DuplicatedIdException e){
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
	}//processSubmit()

}
