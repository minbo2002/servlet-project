package recommand.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.conn.ConnectionProvider;
import recommand.dao.RecomBoardDAO;
import recommand.domain.RecomBoard;
import recommand.model.BoardPage;

public class ListBoardService {

	private RecomBoardDAO recomBoardDAO = new RecomBoardDAO();
	private int rowSize = 3;
	
	
	public BoardPage getBoardPage(int pageNo, int rowSize) {
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			int total = recomBoardDAO.selectCount(conn);
			List<RecomBoard> boardList = recomBoardDAO.select(conn, (pageNo-1)*rowSize, rowSize);
			
			return new BoardPage(total, pageNo, rowSize, boardList);
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
