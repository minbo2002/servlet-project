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
	
	public void updateBoardAndImg(UpdateRequest updateReq) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			// DB에 있는 게시판 데이터 가져오기
			RecomBoard recomBoard = recomBoardDAO.selectById(conn, updateReq.getrNo());
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
			
			// 게시판 업데이트
			recomBoardDAO.update(
								  conn,
								  updateReq.getBookTitle(),
								  updateReq.getAuthor(),
								  updateReq.getPublisher(),
								  updateReq.getrTitle(),
								  updateReq.getrContent(),
								  updateReq.getrNo(),
								  updateReq.getUser().getM_no()
								);

			// 게시판 내부의 파일여부 확인
			int selectCount = fileDAO.selectCount(conn, updateReq.getrNo());
			System.out.println("게시판 내부의 파일 개수 = "+selectCount);
			
			if(selectCount!=0) {
				System.out.println("게시판에 이미지 파일 존재");
				
				// DB에 있는 이미지 파일데이터 가져오기
				RecomFile oldFile = fileDAO.selectById(conn, updateReq.getrNo());

				// 기존의 이미지 파일데이터 삭제
				fileDAO.delete(conn, oldFile.getR_no());

				// 새롭게 선택한 이미지파일 업데이트
				fileDAO.insertForUpdate(
						conn,
						updateReq.getRecomFile().getFilename(), 
						updateReq.getRecomFile().getFileRealName(),
						updateReq.getUser().getM_no(),
						updateReq.getrNo()
						);
			}else {
				System.out.println("게시판에 이미지 파일 없음");
				
				fileDAO.insertForUpdate(
						conn,
						updateReq.getRecomFile().getFilename(), 
						updateReq.getRecomFile().getFileRealName(),
						updateReq.getUser().getM_no(),
						updateReq.getrNo()
						);
			}

			conn.commit();
			
		}catch (SQLException e) {
			JdbcUtil.close(conn);
			e.printStackTrace();
			//throw new RuntimeException();
		
		}catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		
		}finally {
			JdbcUtil.close(conn);
		}
	}
	
	public void updateBoard(UpdateRequest updateReq) {
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
			
			// 게시판 업데이트
			recomBoardDAO.update(
								  conn,
								  updateReq.getBookTitle(),
								  updateReq.getAuthor(),
								  updateReq.getPublisher(),
								  updateReq.getrTitle(),
								  updateReq.getrContent(),
								  updateReq.getrNo(),
								  updateReq.getUser().getM_no()
								);
			conn.commit();
			
		}catch (SQLException e) {
			JdbcUtil.close(conn);
			e.printStackTrace();
			//throw new RuntimeException();
		
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
