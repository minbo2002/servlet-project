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
	
	
}
