package recommand.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.conn.ConnectionProvider;
import recommand.dao.FileDAO;
import recommand.dao.RecomBoardDAO;
import recommand.domain.RecomBoard;
import recommand.domain.RecomFile;
import recommand.model.WriteRequest;

public class WriteBoardService {

	private RecomBoardDAO recomBoardDAO = new RecomBoardDAO();
	private FileDAO fileDAO = new FileDAO();
	
	Connection conn = null;
	
	public Integer writeBoard(WriteRequest writeReq) {  //  WriteRequest writeReq ==> 로그인한 유저id, 유저name, 책제목, 저자, 출판사, 게시판 title, content, like, cnt, date, date
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);  // autocommit 설정해제
			
			Date now = new Date();
			
			// 게시판등록
			RecomBoard recomBoard = new RecomBoard( 
													writeReq.getBookTitle(),
													writeReq.getAuthor(),
													writeReq.getPublisher(),
												    writeReq.getrTitle(), 
												    writeReq.getrContent(), 
												    0, 0, now, now,
												    writeReq.getUser().getM_no()
												  ); 
			System.out.println("서비스 recomBoard="+recomBoard);
			RecomBoard savedRecomBoard = recomBoardDAO.insert(conn, recomBoard);
			System.out.println("savedRecomBoard="+savedRecomBoard);
			if(savedRecomBoard == null) {
				throw new RuntimeException("추천게시판 등록 실패");
			}
			
			// 이미지등록
			RecomFile recomFile = new RecomFile( 
												 writeReq.getRecomfile().getFilename(), 
												 writeReq.getRecomfile().getFileRealName(), 
												 savedRecomBoard.getmNo(), 
												 savedRecomBoard.getrNo()
											   );
			
			RecomFile savedRecomFile = fileDAO.insert(conn, recomFile, savedRecomBoard);
			System.out.println("savedRecomFile="+savedRecomFile);
//			if(savedRecomFile == null) {
//				throw new RuntimeException("이미지 등록 실패");
//			}
			
			conn.commit();

			return savedRecomBoard.getrNo();
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException();
			
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
			
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
