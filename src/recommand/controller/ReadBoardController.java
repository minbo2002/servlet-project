package recommand.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.User;
import mvc.command.CommandHandler;
import recommand.model.BoardData;
import recommand.service.ReadBoardService;

public class ReadBoardController implements CommandHandler {

	private ReadBoardService readBoardService = new ReadBoardService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// no : 상세조회할 게시판번호
		String strNo =  request.getParameter("no");
		if(strNo==null) {
			throw new RuntimeException();  // 만약 파라미터 no(생세조회할 글번호) 가 null이면 RuntimeException 발생
		}
		int no = Integer.parseInt(strNo);

		// pageNo : 요청 페이지
		String strPageNo = request.getParameter("pageNo");
		int pageNo = 1;   // 만약 파라미터 pageNo(요청 페이지)가 null이라면 요청페이지를 1로 설정
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		
		// rowSize : 한페이지에 보여줄 글 개수
		String strRowSize = request.getParameter("rowSize"); 
		int rowSize = 10;    // 만약 파라미터 size(한페이지에 보여줄 글 갯수)가 null이라면 한페이지에 보여줄 글 갯수를 3으로 설정
		if(strRowSize!=null) {
			rowSize = Integer.parseInt(strRowSize);;
		}
		
		// 2. 비지니스 로직  Service <-> DAO <-> DB
		BoardData boardData = readBoardService.readBoard(no, true);
		
		// 3. Model
		request.setAttribute("boardData", boardData);
		request.setAttribute("no", no);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("rowSize", rowSize);
		
		// 4. View
		return "/view/recomboard/readBoard.jsp";
	}

}
