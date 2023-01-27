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
import recommand.model.BoardPage;
import recommand.model.WriteRequest;
import recommand.service.ListBoardService;
import recommand.service.WriteBoardService;

public class WriteBoardController implements CommandHandler {

	private static final String FORM_VIEW = "/view/recomboard/writeBoardForm.jsp";
	private WriteBoardService writeBoardService = new WriteBoardService();
	private ListBoardService listBoardService = new ListBoardService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);  // 책정보 등록으로 이동
			
		}else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);  // 책정보 등록 데이터 처리
		
		}else {

			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	// 추천게시판 등록폼 이동(책정보 + 게시판 + 파일)
	private String processForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("WriteBoardController클래스의  GET요청인 processForm() 메서드 진입 ");
		
		String rowSize = request.getParameter("rowSize");  // 등록폼애서 목록으로 이동할때 사용
		
		User authUser = loginedUser(request);		 // authUser = User [m_no=11, mId=user1, mName=name1, grade=1, gender=0]
		request.setAttribute("authUser", authUser);
		request.setAttribute("rowSize", rowSize);
		
		return FORM_VIEW;
	}
	
	public User loginedUser(HttpServletRequest request) {

		//로그인한 유저정보는 세션에서 받자
		User authUser = (User) request.getSession().getAttribute("authUser");  // authUser = User [m_no=11, mId=user1, mName=name1, grade=1, gender=0]
		System.out.println("authUser = " + authUser);
		return authUser;
	}
		
	// 추천게시판 등록 데이터 처리(책정보 + 게시판 + 파일)
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("WriteBoardController클래스의  POST요청인 processSubmit() 메서드 진입 ");

		String uploadPath = this.getClass().getResource("").getPath();
		
		uploadPath = uploadPath.substring(1, uploadPath.indexOf(".metadata")) + 
				".metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\folder\\uploadImage";
		
		System.out.println("uploadPath = " + uploadPath);  // 저장경로에 이미지 저장됨
		
		int maxSize = 1024 * 1024 * 100;
		String encoding = "UTF-8";
		
		MultipartRequest multipartRequest = new MultipartRequest(request, uploadPath, maxSize, encoding, new DefaultFileRenamePolicy());

		// (보고싶은 페이지)
		String strPageNo = multipartRequest.getParameter("pageNo");    // "pageNo" : 보고싶은 페이지
		int pageNo = 1;	 // int pageNo : 보고싶은 페이지 	 
		if(strPageNo!=null) {
			pageNo = Integer.parseInt(strPageNo);
		}
		
		// (한페이지당 보여줄 게시물 개수)
		String strRowSize = multipartRequest.getParameter("rowSize");  // "rowSize" : 한페이지당 보여줄 게시물 개수
		int rowSize = 1;
		if(strRowSize==null) {
			rowSize = 3;
		}else {
			rowSize = Integer.parseInt(strRowSize);;
		}
		
		BoardPage boardPage = listBoardService.getBoardPage(pageNo, rowSize);

		//유효성검사를 위한 errors 객체 생성
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		
		//로그인한 유저정보는 세션에서 받자
		User authUser = loginedUser(request);  // // authUser = User [m_no=11, mId=user1, mName=name1, grade=1, gender=0]
		
		WriteRequest writeReq = createWriteRequest(authUser, multipartRequest);
		System.out.println("writeReq ="+writeReq);
		writeReq.validate(errors);  // bInfoTitle(책제목), author(저자), publisher(출판사), rTitle(게시판제목), rContent(게시판내용) 유효성 검사
		request.setAttribute("errors", errors);  // p641 37번째 줄
		                      
		if(!errors.isEmpty()) {
			request.setAttribute("rowSize", rowSize);
			return FORM_VIEW;
		}
		
		// 2. 비지니스 로직 수행 <-> Serivce <-> DAO <-> DB
		int newBoardNo = writeBoardService.write(writeReq);  // Integer타입 -> int타입  변환 (unboxing)
		
		// 3. Model
		request.setAttribute("newBoardNo", newBoardNo);  // newArticleNo : 새로 생성된 article_no (PK)
		request.setAttribute("boardPage", boardPage);
		request.setAttribute("rowSize", rowSize);
		
		// 4. View
		return "/view/recomboard/listBoard.jsp";
	}
	
	private WriteRequest createWriteRequest(User authUser, MultipartRequest multipartRequest) {
		
		//1.파라미터받기
		String bookTitle = multipartRequest.getParameter("bookTitle");
		String author = multipartRequest.getParameter("author");
		String publisher = multipartRequest.getParameter("publisher");
		String rTitle = multipartRequest.getParameter("rTitle");
		String rContent = multipartRequest.getParameter("rContent");
        // 중복된 파일이름이 있기에 fileRealName이 실제로 서버에 저장된 경로이자 파일
		String filename = multipartRequest.getOriginalFileName("filename");   // finename : 사용자가 올린 파일의 이름이다
		// 실제 서버에 업로드 된 파일시스템 네임
		String fileRealName = multipartRequest.getFilesystemName("filename"); //  fileRealName : 실제로 서버에 저장된 경로이자 파일
		
		return new WriteRequest( new User(authUser.getM_no(), authUser.getmId(), authUser.getmName(), authUser.getGrade(), authUser.getGender()), 
								 bookTitle,
								 author,
								 publisher,
								 rTitle,
								 rContent,
								 new RecomFile(filename, fileRealName)
							   );
	}

	
}
