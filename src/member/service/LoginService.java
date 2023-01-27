package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.conn.ConnectionProvider;
import member.dao.MemberDAO;
import member.exception.LoginFailException;
import member.model.Member;
import member.model.User;

public class LoginService {

	private MemberDAO memberDAO = new MemberDAO();

	public User login(String mId, String mPwd) {
		try (Connection conn = ConnectionProvider.getConnection()){
		
			Member member = memberDAO.selectById(mId, conn);
			if(member == null) {
				throw new LoginFailException();
			}

			if(!member.matchPassword(mPwd)) {
				throw new LoginFailException();
			}
			return new User(member.getM_no(), member.getmId(), member.getmName(), member.getGrade(), member.getGender());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
