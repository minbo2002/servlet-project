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
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			
			System.out.println("conn="+conn);
			
			int total = recomBoardDAO.selectCount(conn);
			
			System.out.println("total="+total);
			
			System.out.println("pageNo="+pageNo+", rowSize="+rowSize+", col="+col+", word="+word);
			
			List<RecomBoard> boardList = recomBoardDAO.selectAllSearch(conn, (pageNo-1)*rowSize, rowSize, col, word);
			
			System.out.println("boardList="+boardList);
			
			return new BoardPage(total, pageNo, rowSize, boardList);
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
