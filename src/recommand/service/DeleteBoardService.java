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
	int cnt = 0;
	
	public int deleteBoard(int no) {
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			cnt = fileDAO.delete(conn, no);
//			if(cnt==0) {
//				throw new FileDataNotFoundException();
//			}
			
//			if(cnt==1) {
				recomBoardDAO.delete(conn, no);				
//			}
			
			conn.commit();
			return cnt;
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
		
		} finally {
			JdbcUtil.close(conn);
		}
		
		return cnt;
	}
}
