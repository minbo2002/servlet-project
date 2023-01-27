package recommand.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.conn.ConnectionProvider;
import member.model.User;
import recommand.dao.FileDAO;
import recommand.dao.RecomBoardDAO;
import recommand.domain.RecomBoard;
import recommand.domain.RecomFile;
import recommand.exception.BoardDataNotFoundException;
import recommand.exception.FileDataNotFoundException;
import recommand.model.BoardData;

public class ReadBoardService {

	Connection conn = null;
	
	private RecomBoardDAO recomBoardDAO = new RecomBoardDAO();
	private FileDAO fileDAO = new FileDAO();
	
	public BoardData readBoard(int no, boolean increaseReadCount) {
		
		try {
			conn = ConnectionProvider.getConnection();
			System.out.println("no = " + no);
			
			// 게시판내용 조회
			RecomBoard recomBoard = recomBoardDAO.selectById(conn, no);
			System.out.println("Service에서 recomBoard = " + recomBoard);
			
			if(recomBoard==null) {
				throw new BoardDataNotFoundException();
			}
			
			// 이미지 이름조회 (jsp에 이미지 이름으로 불러오는 용도)
			RecomFile recomFile = fileDAO.selectById(conn, no);
			if(recomFile==null) {
				throw new FileDataNotFoundException();
			}
			
			if(increaseReadCount) {
				recomBoardDAO.increaseReadCount(conn, no);
			}
			
			return new BoardData(recomBoard, recomFile);
			
		} catch (SQLException e) {
			System.out.println("readBoard() 메서드 실행시 오류 발생");
			e.printStackTrace();
			//throw new RuntimeException();
			return null;
			
		} finally {
			JdbcUtil.close(conn);
		}
		
	}
}
