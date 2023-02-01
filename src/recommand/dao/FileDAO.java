package recommand.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.JdbcUtil;
import member.model.User;
import recommand.domain.RecomBoard;
import recommand.domain.RecomFile;

public class FileDAO {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
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
	
	public RecomFile selectById(Connection conn, int no) throws SQLException {
		System.out.println("FileDAO 클래스의 selectById() 메서드 실행");
		
		String sql = "SELECT R_FILE_NO, FILE_NAME, FILE_REAL_NAME, M_NO, R_NO " + 
					 "FROM recomfile " + 
					 "WHERE R_NO=?";
		
		RecomFile recomFile = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				recomFile = new RecomFile(
										   rs.getInt("R_FILE_NO"), 
										   rs.getString("FILE_NAME"), 
										   rs.getString("FILE_REAL_NAME"), 
										   rs.getInt("M_NO"), 
										   rs.getInt("R_NO")
										 );

			}
			return recomFile;
			
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	// 파일을 새로운 파일로 변경하고 게시판도 변경할때의 메서드
	public void update(Connection conn, String filename, String fileRealName, int mNo, int rNo) throws SQLException {
		System.out.println("FileDAO 클래스의 update() 메서드 진입");

		String sql = "INSERT INTO recomfile(file_name, file_real_name, m_no, r_no) " + 
				 	 "VALUES(?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, filename);
			pstmt.setString(2, fileRealName);
			pstmt.setInt(3, mNo); 
			pstmt.setInt(4, rNo);
			
			int cnt = pstmt.executeUpdate();
			System.out.println("생성된 파일 개수 = " + cnt);

		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	// 파일을 기존의 파일 그대로 사용하고 게시판만 변경할때  파일update 메서드
	public void oldUpdate(Connection conn, String filename, String fileRealName, int mNo, int rNo, int rFileNo) throws SQLException {
		System.out.println("FileDAO 클래스의 oldUpdate() 메서드 실행");
		
		String sql = "UPDATE recomfile " + 
					 "SET File_name=?, file_real_name=?, m_no=?, r_no=? " + 
					 "WHERE r_file_no=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, filename);
			pstmt.setString(2, fileRealName);
			pstmt.setInt(3, mNo);
			pstmt.setInt(4, rNo);
			pstmt.setInt(5, rFileNo);
			
			int cnt = pstmt.executeUpdate();
			System.out.println("업데이트된 File테이블 데이터 개수 = " + cnt);

		} finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public void deleteForUpdate(Connection conn, int no) {
		
		System.out.println("FileDAO클래스의 deleteForUpdate() 메서드 실행");
		
		String sql = "DELETE FROM recomfile " + 
	 	 		   "WHERE r_no=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			int cnt = pstmt.executeUpdate();
			System.out.println("삭제된 파일 행 개수="+cnt);
			
		}catch (SQLException e) {
			e.printStackTrace();
		
		}finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public int delete(Connection conn, int no) {
		
		String sql = "DELETE from recomfile " + 
					 "WHERE r_no=?";
		
		int cnt = 0;  // 삭제한 파일 행 개수
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			cnt = pstmt.executeUpdate();
			System.out.println("삭제된 파일 행 개수="+cnt);
			
			return cnt;
			
		}catch (SQLException e) {
			e.printStackTrace();
		
		}finally {
			JdbcUtil.close(pstmt);
		}
		
		return cnt;
	}
}
