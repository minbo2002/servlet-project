package member.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import jdbc.JdbcUtil;
import member.model.Member;

public class MemberDAO {
	public Member selectById(String mid, Connection conn) {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "SELECT m_no, mId, mPwd, mName, email, gender, addr, grade, mJoin "+
				 "from member where mId= ?";
	
	Member member = null;
	
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, mid);
		rs = pstmt.executeQuery();
		System.out.println("rs="+rs);
		
		
		
		if(rs.next()) { 
			int m_no = rs.getInt("m_no");
			String mId = rs.getString("mId");
			String mPwd = rs.getString("mPwd");
			String mName = rs.getString("mName");
			String email = rs.getString("email");
			String gender = rs.getString("gender");
			String addr = rs.getString("addr");
			Date mJoin = toDate(rs.getTimestamp("mJoin"));
			int grade = rs.getInt("grade");
			member = new Member(m_no, mId, mPwd, mName, email, gender, addr, mJoin, grade);
			System.out.println("member= " + member);
			
			
			return member;
		}
	}catch(SQLException e){
		e.printStackTrace();
	}finally {
		JdbcUtil.close(rs);
		JdbcUtil.close(pstmt);
	}
	return member;
		
	}

	private Date toDate(Timestamp ts) {
		Date date = null;
		if( ts != null) {
			date = new Date(ts.getTime());
		}
		return date;
	}

	public void insert(Member member, Connection conn) throws SQLException {
		String sql = "insert into member(mId, mPwd, mName, email, gender, addr, mJoin, grade) values(?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, member.getmId());
		stmt.setString(2, member.getmPwd());
		stmt.setString(3, member.getmName());
		stmt.setString(4, member.getEmail());
		stmt.setString(5, member.getGender());
		stmt.setString(6, member.getAddr());
		//member.getRegdate()瑜� �넻�빐 Date媛앹껜諛쏅뒗�떎
		//�씠�젃寃� 諛쏆� Date.getTime()濡� long���엯 媛믪쓣 諛쏅뒗�떎(1970�뀈 1�썡 1�씪 �옄�젙�씠�썑�쓽 ms)
		//new Timestamp(long���엯蹂��닔)瑜� �씠�슜�빐�꽌 Timestamp媛앹껜�깮�꽦
		stmt.setTimestamp(7,new Timestamp(member.getmJoin().getTime()));//p593 48�씪�씤
		stmt.setInt(8,member.getGrade());
		int result = stmt.executeUpdate();
		System.out.println("�쉶�썝insert�떎�뻾寃곌낵:="+result);
	}
	
	//鍮꾨�踰덊샇 �룷�븿 �떎瑜� �쉶�썝�젙蹂� 蹂�寃�
	public void update(Connection conn, Member member) throws SQLException {
		String sql = "update member set mName=?, mPwd=? where mId=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getmName());
			pstmt.setString(2, member.getmPwd());
			pstmt.setString(3, member.getmId());
			pstmt.executeUpdate();
	}
	
	public Member findId(String mName, String email, Connection conn) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT m_no, mId, mPwd, mName, email, gender, addr, grade, mJoin "+
				 "from member where mName= ? and email=?";
		
		Member member = null; //user媛� �엯�젰�븳 id瑜� �궗�슜�븯�뒗 湲곗〈 member�젙蹂대�� ���옣�븯湲� �쐞�븳 蹂��닔�꽑�뼵 諛� 珥덇린�솕
		
		//4.�떎�뻾 
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mName);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			System.out.println("rs="+rs);
			
			if(rs.next()) { 
				int m_no = rs.getInt("m_no");
				String mId = rs.getString("mId");
				String mPwd = rs.getString("mPwd");
				String memberName = rs.getString("mName");
				String memberEmail = rs.getString("email");
				String gender = rs.getString("gender");
				String addr = rs.getString("addr");
				Date mJoin = toDate(rs.getTimestamp("mJoin"));
				int grade = rs.getInt("grade");
				
				//�빐�떦 �쉶�썝�쓽 �젙蹂대�� 媛�吏��뒗 �쉶�썝媛앹껜 �깮�꽦
				member = new Member(m_no, mId, mPwd, memberName, memberEmail, gender, addr, mJoin, grade);
				System.out.println("member= " + member);
				
				return member;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return member;
			
		}
	
	public Member findPwd(String mId, String mName, String email, Connection conn) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT m_no, mId, mPwd, mName, email, gender, addr, grade, mJoin "+
				 "from member where mId=? and mName= ? and email=?";
		
		Member member = null;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, mId);
			pstmt.setString(2, mName);
			pstmt.setString(3, email);
			rs=pstmt.executeQuery();
			if(rs.next()) { 
				int m_no = rs.getInt("m_no");
				String memberId = rs.getString("mId");
				String mPwd = rs.getString("mPwd");
				String memberName = rs.getString("mName");
				String memberEmail = rs.getString("email");
				String gender = rs.getString("gender");
				String addr = rs.getString("addr");
				Date mJoin = toDate(rs.getTimestamp("mJoin"));
				int grade = rs.getInt("grade");
				
				//�빐�떦 �쉶�썝�쓽 �젙蹂대�� 媛�吏��뒗 �쉶�썝媛앹껜 �깮�꽦
				member = new Member(m_no, memberId, mPwd, memberName, memberEmail, gender, addr, mJoin, grade);
				System.out.println("member= " + member);
				
				return member;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return member;
		
	}
}

