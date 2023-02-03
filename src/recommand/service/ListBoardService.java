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

	public BoardPage getBoardPage(int pageNo, int rowSize) {
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			int total = recomBoardDAO.selectCount(conn);
			List<RecomBoard> boardList = recomBoardDAO.selectAll(conn, (pageNo-1)*rowSize, rowSize);
			
			return new BoardPage(total, pageNo, rowSize, boardList);
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public BoardPage getBoardPageSearch(int pageNo, int rowSize, String col, String word) {
		
		System.out.println("ListBoardService클래스의  getBoardPageSearch()메서드 진입");
		
		try {
			Connection conn = ConnectionProvider.getConnection();

			int afterTotal = recomBoardDAO.selectSearchCount(conn, col, word); 
			System.out.println("afterTotal="+afterTotal);
			
			List<RecomBoard> afterBoardList = recomBoardDAO.selectAllSearch(conn, (pageNo-1)*rowSize, rowSize, col, word);
			
			return new BoardPage(afterTotal, pageNo, rowSize, afterBoardList);
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
