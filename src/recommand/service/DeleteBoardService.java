package recommand.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.conn.ConnectionProvider;
import recommand.dao.FileDAO;
import recommand.dao.RecomBoardDAO;
import recommand.exception.FileDataNotFoundException;

public class DeleteBoardService {

	private RecomBoardDAO recomBoardDAO = new RecomBoardDAO();
	private FileDAO fileDAO = new FileDAO();
	
	Connection conn = null;
	int delFileCnt = 0;
	int delBoardCnt = 0;
	
	public int deleteBoard(int no) {
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			// 삭제된 이미지 파일 개수
			delFileCnt = fileDAO.delete(conn, no);

			// 삭제된 게시판 글 개수
			delBoardCnt = recomBoardDAO.delete(conn, no);				

			conn.commit();
			return delBoardCnt;
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
		
		} finally {
			JdbcUtil.close(conn);
		}
		
		return delBoardCnt;
	}
}
