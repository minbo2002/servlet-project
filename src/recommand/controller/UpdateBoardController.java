package recommand.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import member.model.User;
import mvc.command.CommandHandler;
import recommand.domain.RecomFile;
import recommand.exception.BoardDataNotFoundException;
import recommand.exception.PermissionDeniedException;
import recommand.model.BoardData;
import recommand.model.UpdateRequest;
import recommand.service.ReadBoardService;
import recommand.service.UpdateBoardService;

public class UpdateBoardController implements CommandHandler {

	private static final String FORM_VIEW = "/view/recomboard/updateBoard.jsp";
	
	// 상세보기위한 서비스
	private ReadBoardService readBoardService = new ReadBoardService();
	// 수정처리위한 서비스
	private UpdateBoardService updateBoardService = new UpdateBoardService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);  //  비밀번호변경 form 보여주는 요청
			
		}else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);  // 비밀번호 변경 데이터 처리
		
		}else {
			/*  상태코드 : SC_METHOD_NOT_ALLOWED
			 	405(허용되지않는 메서드):요청에 지정된 방법을 사용할 수 없다.  ex) 1. POST방식으로 요청을 받는서버에 GET요청을 보내는경우
			 												    2. 읽기전용 리소스에 PUT 요청을 보내는경우
			 */
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	// (해당게시글의 정보가 출력되어있는) 수정폼으로 이동 (p669 38번쨰줄)
	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("UpdateBoardController클래스의  GET요청인 processForm() 메서드 진입 ");
		
		try {
			// 1. 파라미터 가져오기 
			// no : 상세조회할 글번호
			String strNo =  request.getParameter("no");
			if(strNo==null) {
				throw new RuntimeException();  // 만약 파라미터 no(생세조회할 글번호) 가 null이면 RuntimeException 발생
			}
			int no = Integer.parseInt(strNo);  // 상세 조회할 글 번호

			/*===============================================================================================*/
			
			// pageNo : 요청 페이지
			String strPageNo = request.getParameter("pageNo");    // "pageNo" : listArtricle.jsp 파일에서 보고싶은 페이지
			
			int pageNo = 1;   // 만약 파라미터 pageNo(요청 페이지)가 null이라면 요청페이지를 1로 설정
			if(strPageNo!=null) {
				pageNo = Integer.parseInt(strPageNo);
			}
			
			/*===============================================================================================*/
			
			// rowSize : 한페이지에 보여줄 글 갯수
			String strRowSize = request.getParameter("rowSize");  // "rowSize" : listArtricle.jsp 파일에서  <select> 태그의 name 속성값
			
			int rowSize = 3;    // 만약 파라미터 size(한페이지에 보여줄 글 갯수)가 null이라면 한페이지에 보여줄 글 갯수를 3으로 설정
			if(strRowSize!=null) {
				rowSize = Integer.parseInt(strRowSize);;
			}
			
			/*===============================================================================================*/
			
			// 2. 비지니스 로직 수행 <-> Serivce <-> DAO <-> DB
			/*
			        파라미터
			    int no : 상세조회 할 글번호
			    boolean increaseReadCount : false 여기서ㅐ는 수정을 위해 상세보기를 진행하므로 조회수 증가를 하지 않는다.
			 */
			BoardData boardData = readBoardService.readBoard(no, false);
			
			// 로그인한 회원은 자신의 글에 대해서만 내용을 수정할 수 있어야 한다.
			// 조건1) 로그인을 했나?
			User authUser = (User) request.getSession().getAttribute("authUser");
			
			// 조건2) 로그인한 회원은 자신의 글인가? (로그인한 user의 id와  작성자의 id 일치여부)
			if(!canModify(authUser, boardData)) { // 수정불가이면
				response.sendError(HttpServletResponse.SC_FORBIDDEN); // 403error
				return null;
			}
			/*참고 
			 * HttpServletResponse.SC_FORBIDDEN: 403에러
			 서버가 허용하지 않는 웹 페이지나 미디어를 사용자가 요청할 때 
			 웹 서버가 반환하는 HTTP 상태 코드이다. 
			 다시 말해, 클라이언트가 서버에 도달할 수 있어도 
			 서버가 페이지 접근 허용을 거부했다는 것을 뜻한다*/
			
			// 3. Model & View
			request.setAttribute("boardData", boardData);
			request.setAttribute("no", no);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("rowSize", rowSize);
			
			// 4. View
			return FORM_VIEW; 
			
		} catch (BoardDataNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
		
	// 수정처리 (p670 66번째줄)
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ModifyArticleHandler클래스의  POST요청인 processSubmit() 메서드 진입 ");
		
		
		
		String uploadPath = this.getClass().getResource("").getPath();
		
		uploadPath = uploadPath.substring(1, uploadPath.indexOf(".metadata")) + 
				".metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\folder\\uploadImage";
		
		System.out.println("uploadPath = " + uploadPath);  // 저장경로에 이미지 저장됨
		
		int maxSize = 1024 * 1024 * 100;
		String encoding = "UTF-8";
		
		MultipartRequest multipartRequest = new MultipartRequest(request, uploadPath, maxSize, encoding, new DefaultFileRenamePolicy());
	
		/*===============================================================================================*/
		
		// pageNo : 요청 페이지
		String strPageNo = multipartRequest.getParameter("pageNo");    // "pageNo" : listArtricle.jsp 파일에서 보고싶은 페이지
		
		int pageNo = 1;   // 만약 파라미터 pageNo(요청 페이지)가 null이라면 요청페이지를 1로 설정
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		
		/*===============================================================================================*/
		
		// rowSize : 한페이지에 보여줄 글 갯수
		String strRowSize = multipartRequest.getParameter("rowSize");  // "rowSize" : listArtricle.jsp 파일에서  <select> 태그의 name 속성값
		
		int rowSize = 3;    // 만약 파라미터 size(한페이지에 보여줄 글 갯수)가 null이라면 한페이지에 보여줄 글 갯수를 3으로 설정
		if(strRowSize!=null) {
			rowSize = Integer.parseInt(strRowSize);;
		}
		
		/*===============================================================================================*/
		
		User authUser = (User) request.getSession().getAttribute("authUser");
		
		// ModifyRequest(로그인한userid, 글번호, db의 title, db의 content)
		
		//1.파라미터받기
		String strNo =  multipartRequest.getParameter("rNo");
		if(strNo==null) {
			throw new RuntimeException();  // 만약 파라미터 rNo(해당 게시글번호) 가 null이면 RuntimeException 발생
		}
		int rNo = Integer.parseInt(strNo);  // 상세 조회할 글 번호
		String rtitle = multipartRequest.getParameter("rtitle");
		String bookTitle = multipartRequest.getParameter("bookTitle");
		String author = multipartRequest.getParameter("author");
		String publisher = multipartRequest.getParameter("publisher");
		String rContent = multipartRequest.getParameter("rContent");
        // 중복된 파일이름이 있기에 fileRealName이 실제로 서버에 저장된 경로이자 파일
		String filename = multipartRequest.getOriginalFileName("filename");   // finename : 사용자가 올린 파일의 이름이다
		// 실제 서버에 업로드 된 파일시스템 네임
		String fileRealName = multipartRequest.getFilesystemName("filename"); //  fileRealName : 실제로 서버에 저장된 경로이자 파일
		
		UpdateRequest updateReq = new UpdateRequest(
													rNo, 
													new User(authUser.getM_no(), authUser.getmId()),
													bookTitle, author, publisher, rtitle, rContent,
													new RecomFile(filename, fileRealName) 
												   );
		
		System.out.println("updateReq = " + updateReq);
		
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		request.setAttribute("errors", errors);  
		updateReq.validate(errors);  // 제목유효성 검사
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			// 2. 비지니스 로직수행 (DB에 update쿼리)
			/* 
			 * 파라미터
			 * ModifyRequest modReq : 글 수정을 위한  -> 수정하는 사용자id, 수정할 글번호, 작성자이름, 수정할 제목, 수정할 내용 
			 */
			updateBoardService.update(updateReq);
			
			// 3. Model
			request.setAttribute("updateReq", updateReq);
			
			// 4. View
			return "/view/article/modifySuccess.jsp";
					
		}catch (BoardDataNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);  // 404 에러
			return null;
		
		}catch (PermissionDeniedException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);  // 403 에러
			return null;
		}
	}
	
	
	// 수정권한체크 (p670 61번째줄)
	/*
    	파라미터
		로그인한 유저정보
		작성자정보
	*/
	private boolean canModify(User authUser, BoardData boardData) {
		
		// 로그인한 유저정보에서 id 가져오기/article/listArticle.do
		String userId = authUser.getmId();
		
		// 작성자정보에서 id 가져오기
		String writerId = boardData.getRecomBoard().getMember().getmId();
		
		return userId.equals(writerId);
	}
}
