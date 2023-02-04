package recommand.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import recommand.model.BoardPage;
import recommand.service.ListBoardService;

public class SearchBoardController implements CommandHandler{

	private ListBoardService listBoardService = new ListBoardService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("SearchBoardController클래스 진입");
		
		// 1. 파라미터받기
		// col : 조회조건
		// word : 조회단어
		String col = request.getParameter("col");
		String word = request.getParameter("word");
		if(word==null) {
			word="";
		}
		
		System.out.println("검색할 범위="+col);
		System.out.println("검색할 단어="+word);

		// pageNo : 보고싶은 페이지 	 
		String strPageNo = request.getParameter("pageNo");
		int pageNo = 1;
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		
		int rowSize = Integer.parseInt(request.getParameter("rowSize"));
		if(rowSize==0) {
			rowSize = 10;
		}
		
		
		// 2. 비지니스로직 <-> Service <-> DAO <-> DB
		BoardPage boardPage = listBoardService.getBoardPageSearch(pageNo, rowSize, col, word);
		
		System.out.println("SearchBoardController 클래스에서 getBoardPageSearch() 메서드에 의해 반환된 boardPage ="+boardPage);
		
		// 3. Model
		request.setAttribute("boardPage", boardPage);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("rowSize", rowSize);
		request.setAttribute("col", col);
		request.setAttribute("word", word);

		return "/view/recomboard/searchListBoard.jsp";
	}
}
