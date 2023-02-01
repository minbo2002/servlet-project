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
			
			// 입력한 게시판 데이터 가져오기
			RecomBoard recomBoard = new RecomBoard( 
													writeReq.getBookTitle(),
													writeReq.getAuthor(),
													writeReq.getPublisher(),
												    writeReq.getrTitle(), 
												    writeReq.getrContent(), 
												    0, 0, now, now,
												    writeReq.getUser().getM_no()
												  ); 

			RecomBoard savedRecomBoard = recomBoardDAO.insert(conn, recomBoard);

			if(savedRecomBoard == null) {
				throw new RuntimeException("추천게시판 등록 실패");
			}
			
			// 입력한 파일 데이터 가져오기
			RecomFile recomFile = new RecomFile( 
												 writeReq.getRecomfile().getFilename(), 
												 writeReq.getRecomfile().getFileRealName(), 
												 savedRecomBoard.getmNo(), 
												 savedRecomBoard.getrNo()
											   );
			
			RecomFile savedRecomFile = fileDAO.insert(conn, recomFile, savedRecomBoard);
			
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
