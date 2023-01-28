package recommand.service;

import java.sql.Connection;
import java.sql.SQLException;
import jdbc.JdbcUtil;
import jdbc.conn.ConnectionProvider;
import recommand.dao.FileDAO;
import recommand.dao.RecomBoardDAO;
import recommand.domain.RecomBoard;
import recommand.domain.RecomFile;
import recommand.exception.BoardDataNotFoundException;
import recommand.exception.PermissionDeniedException;
import recommand.model.UpdateRequest;

public class UpdateBoardService {

	private RecomBoardDAO recomBoardDAO = new RecomBoardDAO();
	private FileDAO fileDAO = new FileDAO();
	
	Connection conn = null;
	
	public void update(UpdateRequest updateReq) {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			// 특정id에 해당하는 DB에 있는 변경전 데이터 가져오기
			RecomBoard recomBoard = recomBoardDAO.selectById(conn, updateReq.getrNo());
			
			System.out.println("UpdateBoardService에서 생성된 recomBoard 객체 = "+ recomBoard);
			if(recomBoard==null) {
				throw new BoardDataNotFoundException();
			}
			
			// 수정불가시 commit 불가 예외처리
			// *파라미터 : 수정하려는 사용자id, 특정 글번호에 해당하는 (변경전) DB데이터
			// *반환타입 : boolean : DB에 있는 작성자id와 수정하려는 id가 동일하며 true, 다르다면 false 반환
			if(!canModify(updateReq.getUser().getmId(), recomBoard)) {
				System.out.println("DB에 있는 게시판작성자id와 수정을 시도하는 id가 다름");
				throw new PermissionDeniedException();
			}
			
			// article 테이블 수정처리 (p668 31번째줄)
			recomBoardDAO.update(
								  conn,
								  updateReq.getrNo(),
								  updateReq.getUser().getM_no(),
								  updateReq.getBookTitle(),
								  updateReq.getAuthor(),
								  updateReq.getPublisher(),
								  updateReq.getrTitle(),
								  updateReq.getrContent()
								);
			
			// article_content 테이블 수정처리 (p668 33번째줄)
			fileDAO.update(
							conn,
							updateReq.getRecomFile().getFilename(), 
							updateReq.getRecomFile().getFileRealName(),
							updateReq.getUser().getM_no(),
							updateReq.getrNo()
  					      );
		
			conn.commit();
			
		}catch (SQLException e) {
			JdbcUtil.close(conn);
			throw new RuntimeException();
		
		}catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		
		}finally {
			JdbcUtil.close(conn);
		}
	}
	
	private boolean canModify(String modifingUserId, RecomBoard recomBoard) {
		
		// DB에서 작성자id 가져오기
		String id = recomBoard.getMember().getmId();
		System.out.println("DB에 저장된 게시판작성자 id="+id+"    수정을 시도하는 id="+modifingUserId);
		return id.equals(modifingUserId);
	}
}
