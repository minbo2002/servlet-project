package recommand.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mvc.command.CommandHandler;
import recommand.model.BoardPage;
import recommand.service.ListBoardService;

public class ListBoardController implements CommandHandler {

	private static final String FORM_VIEW = "/view/recomboard/listBoard.jsp";
	private ListBoardService listBoardService = new ListBoardService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
			
		}else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);  
		
		}else {

			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	// 전체게시판 이동
	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ListBoardController클래스의 GET방식");
		
		// 1. 파라미터받기
		// pageNo : 보고싶은 페이지 	 
		String strPageNo = request.getParameter("pageNo");
		int pageNo = 1;
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		
		// rowSize: 한페이지당 보여줄 글 개수
		String strRowSize = request.getParameter("rowSize");
		int rowSize = 1;
		if(strRowSize==null) {
			rowSize = 10;
		}else {
			rowSize = Integer.parseInt(strRowSize);;
		}
		
		// 2. 비지니스로직 <-> Service <-> DAO <-> DB
		BoardPage boardPage = listBoardService.getBoardPage(pageNo, rowSize);

		// 3. Model
		request.setAttribute("boardPage", boardPage);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("rowSize", rowSize);
		
		// 4. View
		return FORM_VIEW;
	}
			
	// 추천게시판 등록 데이터 처리(책정보 + 게시판 + 파일)
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("ListBoardController클래스의 POST방식");

		return null;
	}
}
