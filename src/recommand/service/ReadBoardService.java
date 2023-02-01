package recommand.service;

import java.sql.Connection;
import java.sql.SQLException;
import jdbc.JdbcUtil;
import jdbc.conn.ConnectionProvider;
import recommand.dao.FileDAO;
import recommand.dao.RecomBoardDAO;
import recommand.domain.RecomBoard;
import recommand.domain.RecomFile;
import recommand.exception.BoardDataNotFoundException;
import recommand.model.BoardData;

public class ReadBoardService {

	Connection conn = null;
	
	private RecomBoardDAO recomBoardDAO = new RecomBoardDAO();
	private FileDAO fileDAO = new FileDAO();
	
	public BoardData readBoard(int no, boolean increaseReadCount) {
		
		try {
			conn = ConnectionProvider.getConnection();
			System.out.println("no = " + no);
			
			// 게시판 글 조회
			RecomBoard recomBoard = recomBoardDAO.selectById(conn, no);
			System.out.println("Service에서 recomBoard = " + recomBoard);
			
			if(recomBoard==null) {
				throw new BoardDataNotFoundException();
			}
			
			// 이미지파일 조회
			RecomFile recomFile = fileDAO.selectById(conn, no);
			
			if(increaseReadCount) {
				recomBoardDAO.increaseReadCount(conn, no);
			}
			
			return new BoardData(recomBoard, recomFile);
			
		} catch (SQLException e) {
			System.out.println("readBoard() 메서드 실행시 오류 발생");
			e.printStackTrace();
			return null;
			
		} finally {
			JdbcUtil.close(conn);
		}
		
	}
}
