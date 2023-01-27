package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.conn.ConnectionProvider;
import jdbc.JdbcUtil;
import member.dao.MemberDAO;
import member.exception.InvalidEmailException;
import member.exception.InvalidNameException;
import member.exception.MemberNotFoundException;
import member.model.Member;

public class FindIdService {

	private MemberDAO memberDAO = new MemberDAO();
	
	public Member Findid(String name, String mEmail) throws SQLException {

		Connection conn=null;
		Member member = null;
		try {
			conn = ConnectionProvider.getConnection();
			member = memberDAO.findId(name, mEmail, conn);
			System.out.println("memberDAO.member"+member);

			if(member == null) {
				System.out.println("member=null");
				throw new MemberNotFoundException();
			}

			if(!member.matchMemberName(name)) {
				throw new InvalidNameException();
			}
			if(!member.matchMemberEmail(mEmail)) {
				throw new InvalidEmailException();
			}

			return memberDAO.findId(name, mEmail, conn);	
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException();
		}finally {
			JdbcUtil.close(conn);
		}
		
		
	}
}
