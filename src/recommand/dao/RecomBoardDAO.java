package recommand.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jdbc.JdbcUtil;
import member.domain.Member;
import recommand.domain.RecomBoard;

public class RecomBoardDAO {

	PreparedStatement pstmt = null; // insert용
	Statement stmt = null;  // 조회용
	ResultSet rs = null;
	
	/*
	   int startRow : 시작행 index번호를 의미. 가장 첫번째행은 0부터 시작.
	   int rowSize : 1페이지당 출력게시물 개수 
	 */
	public List<RecomBoard> select(Connection conn, int startRow, int rowSize) throws SQLException {
		
		String sql = "SELECT a.R_NO, b.MID, b.MNAME, a.R_TITLE, a.R_CONTENT, a.LIKE_IT, a.R_CNT, a.REGDATE, a.MODDATE, a.M_NO " + 
				"FROM recomboard a, member b " + 
				"WHERE a.M_NO=b.M_NO " + 
				"order BY a.R_NO desc LIMIT ?,?";
		
		List<RecomBoard> boardList = new ArrayList<RecomBoard>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, startRow);  // int startRow : 시작행 index번호를 의미. 가장 첫번째행은 0부터 시작.
			pstmt.setInt(2, rowSize);	// int rowSize : 1페이지당 출력게시물 개수
			
			rs = pstmt.executeQuery();
			System.out.println("rs = " + rs);
			
			while(rs.next()) {
				RecomBoard board = convertBoard(rs);

				boardList.add(board);					
			}
			return boardList;
			
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	// RecomBoard 객체로 변환하기 p647 36번째줄
	private RecomBoard convertBoard(ResultSet rs) throws SQLException {
		
		return new RecomBoard(rs.getInt("R_NO"), 
							  new Member(rs.getString("MID"), rs.getString("MNAME")),
							  rs.getString("R_TITLE"), 
							  rs.getString("R_CONTENT"),
							  rs.getInt("LIKE_IT"),
							  rs.getInt("R_CNT"),
							  toDate(rs.getTimestamp("REGDATE")), 
							  toDate(rs.getTimestamp("MODDATE")),
							  rs.getInt("M_NO"));

	}
	
	// Timestamp -> Date 객체로 변환하기 (p648 47번째줄)
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	
	public int selectCount(Connection conn) throws SQLException  {
		
		String sql = "select count(R_NO) " +
					 "from recomboard";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {				
				return rs.getInt(1);  // count란 컬럼이 없기때문에 조회해서 index 값을 준다. 
			}
			return 0;
			
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}
