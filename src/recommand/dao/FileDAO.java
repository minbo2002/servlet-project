package recommand.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import member.model.User;
import recommand.domain.RecomBoard;
import recommand.domain.RecomFile;

public class FileDAO {

	PreparedStatement pstmt = null;
	
	public RecomFile insert(Connection conn, RecomFile recomFile, RecomBoard savedRecomBoard) throws SQLException {
		
		String sql = "INSERT INTO recomfile(file_name, file_real_name, m_no, r_no) " + 
					 "VALUES(?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, recomFile.getFilename());
			pstmt.setString(2, recomFile.getFileRealName());
			pstmt.setInt(3, savedRecomBoard.getmNo());
			pstmt.setInt(4, savedRecomBoard.getrNo());
			
			int cnt = pstmt.executeUpdate();
			System.out.println("생성된 test_board테이블의 행 개수 = " + cnt);
	
			return recomFile;

		} finally {
			JdbcUtil.close(pstmt);
		}

	}
}
