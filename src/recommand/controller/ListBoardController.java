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
				
		// (보고싶은 페이지)
		String strPageNo = request.getParameter("pageNo");    // "pageNo" : 보고싶은 페이지
		int pageNo = 1;	 // int pageNo : 보고싶은 페이지 	 
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		
		// (한페이지당 보여줄 게시물 개수)
		String strRowSize = request.getParameter("rowSize");  // "rowSize" : 한페이지당 보여줄 게시물 개수
		int rowSize = 1;
		if(strRowSize==null) {
			rowSize = 10;
		}else {
			rowSize = Integer.parseInt(strRowSize);;
		}
		
		BoardPage boardPage = listBoardService.getBoardPage(pageNo, rowSize);
		
		request.setAttribute("boardPage", boardPage);
		request.setAttribute("rowSize", rowSize);
		
		return FORM_VIEW;
	}
	
	
}
