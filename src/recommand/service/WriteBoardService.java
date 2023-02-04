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
	
	public int writeBoard(WriteRequest writeReq) {
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Date now = new Date();
			
			// 입력한 데이터를 갖는 게시판  객체생성
			RecomBoard recomBoard = new RecomBoard( 
													writeReq.getBookTitle(),
													writeReq.getAuthor(),
													writeReq.getPublisher(),
												    writeReq.getrTitle(), 
												    writeReq.getrContent(), 
												    0, 0, now, now,
												    writeReq.getUser().getM_no()
												  ); 
			// 게시판DB에 저장
			RecomBoard savedRecomBoard = recomBoardDAO.insert(conn, recomBoard);

			System.out.println("savedRecomBoard="+savedRecomBoard);
			
			if(savedRecomBoard == null) {
				throw new RuntimeException("추천게시판 등록 실패");
			}
			
			// 저장할 이미지의 이름이 있다면
			if(writeReq.getRecomfile().getFilename()!=null) {
				
				// 입력한 이미지 파일 데이터를 갖는 파일객체 생성
				RecomFile recomFile = new RecomFile( 
						writeReq.getRecomfile().getFilename(), 
						writeReq.getRecomfile().getFileRealName(), 
						savedRecomBoard.getmNo(), 
						savedRecomBoard.getrNo()
						);
				
				// 파일DB에 저장
				fileDAO.insert(conn, recomFile, savedRecomBoard);
			}

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
