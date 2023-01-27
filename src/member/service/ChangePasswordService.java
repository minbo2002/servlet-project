package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.conn.ConnectionProvider;
import jdbc.JdbcUtil;
import member.dao.MemberDAO;
import member.exception.InvalidPasswordException;
import member.exception.MemberNotFoundException;
import member.model.Member;

public class ChangePasswordService {

	private MemberDAO memberDAO = new MemberDAO();

	public void changePwd(String userId, String curPwd, String newPwd) {

		Connection conn=null;
		Member member = null;
		try {
			conn = ConnectionProvider.getConnection();

			conn.setAutoCommit(false);

			member = memberDAO.selectById(userId, conn);
			
			if(member == null) {
				throw new MemberNotFoundException();
			}

			if(!member.matchPassword(curPwd)) {
				throw new InvalidPasswordException();
			}

			member.changePassword(newPwd);
			memberDAO.update(conn, member);
						
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException();
		}finally {
			JdbcUtil.close(conn);
		}
		
	}
}
