package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.conn.ConnectionProvider;
import jdbc.JdbcUtil;
import member.dao.MemberDAO;
import member.exception.DuplicatedIdException;
import member.model.Member;

//�씠 �겢�옒�뒪�뒗 �꽌鍮꾩뒪�겢�옒�뒪濡쒖뜥 (�뿬湲곗뿉�꽌�뒗 二쇰줈 JoinHandler而⑦듃濡ㅻ윭�뿉 �쓽�빐 �샇異쒕릺�뒗) �쉶�썝媛��엯 愿��젴 �뿬�윭 湲곕뒫�쓣 �젣怨�. (p.596)
//Controller <-> Service <-> DAO <-> DB
//JoinHandler <-> JoinService <-> MemberDAO <-> DB
public class JoinService {

	//�븘�뱶
	private MemberDAO memberDao = new MemberDAO();
	
	//�깮�꽦�옄
	
	//硫붿꽌�뱶
	//�쉶�썝媛��엯(+�븘�씠�뵒 以묐났寃��궗)
	//留ㅺ컻蹂��닔 JoinRequest:�쉶�썝�젙蹂�
	public void join(JoinRequest joinReq) throws SQLException {
		System.out.println("JoinService-join() joinReq= "+joinReq);
		
		
		Connection conn = null;
		Member member = null;
		try {
			conn = ConnectionProvider.getConnection();
			
			//autoCommit湲곕뒫 �빐�젣
			conn.setAutoCommit(false);
			
			//id以묐났寃��궗 湲곕뒫�쓣 媛�吏� DAO硫붿꽌�뱶 �샇異�-p.596(22line)
			member = memberDao.selectById(joinReq.getmId(), conn);
			System.out.println("JoinService - selectById()由ы꽩 諛쏆� member= " + member);
			if(member!=null) { //id瑜� �씠誘� �궗�슜以묒씤 湲곗〈 �쉶�썝�씠 �엳�쑝誘�濡� id以묐났 �뿉�윭 諛쒖깮
				//rollback()
				JdbcUtil.rollback(conn);
				//id以묐났 �뿉�윭 諛쒖깮
				throw new DuplicatedIdException();
			}
			
			//insert湲곕뒫�쓣 媛�吏� DAO硫붿꽌�뱶 �샇異�-p.596(28line)
			//Member member蹂��닔�뿉�뒗 �쉶�썝�릺怨� �떢�� �쑀��媛� �엯�젰�븳 �쉶�썝�젙蹂�
			/*�뿬湲곗뿉�꽌 �쉶�썝踰덊샇�뒗 �젣�쇅(insert�떆 �옄�룞利앷� 媛믪쑝濡� �궗�슜�삁�젙)
			  �쉶�썝媛��엯�씪�� insert�떦�떆 �꽌踰꾩쓽 �쁽�옱 �궇吏쒖떆媛꾪쉶�썝 �벑湲됱� �젙�긽(1)濡� 媛��젙�븯怨� 援ы쁽�븯吏�留� 異붽� �옉�뾽�씠 �븘�슂*/
			Member newmember = 
					new Member(joinReq.getmId(), 
							   joinReq.getmPwd(), 
							   joinReq.getmName(), 
							   joinReq.getEmail(),
							   joinReq.getGender(),
							   joinReq.getAddr(),
							   new Date(), 
							   1);
			
			memberDao.insert(newmember, conn);
			
			//DB commit() 泥섎━
			conn.commit();
		}catch (SQLException e) {
			//DB rollback() 泥섎━
			JdbcUtil.rollback(conn);
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			JdbcUtil.close(conn);
		}
	}
}