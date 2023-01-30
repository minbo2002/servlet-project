package recommand.model;

import java.util.Date;
import java.util.Map;

import member.model.User;
import recommand.domain.RecomFile;

public class UpdateRequest {

	private int rNo;		 	 // 추천게시판 번호(PK)
	private User user;		 	 // 유저
	private String bookTitle;	 // 책제목
	private String author;		 // 저자
	private String publisher;	 // 출판사
	private String rTitle;	 	 // 게시판 제목
	private String rContent; 	 // 게시판 내용
	private RecomFile recomFile; // 파일
	private int likeIt;			 // 좋아요
	private int rCnt;			 // 조회수	
	private Date regDate;		 // 작성일	
	private Date modDate;		 // 수정일

	
	public UpdateRequest(int rNo, User user, String bookTitle, String author, String publisher, String rTitle,
			String rContent, RecomFile recomFile, int likeIt, int rCnt, Date regDate, Date modDate) {
		this.rNo = rNo;
		this.user = user;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publisher = publisher;
		this.rTitle = rTitle;
		this.rContent = rContent;
		this.recomFile = recomFile;
		this.likeIt = likeIt;
		this.rCnt = rCnt;
		this.regDate = regDate;
		this.modDate = modDate;
	}
	
	public int getrNo() {
		return rNo;
	}
	public User getUser() {
		return user;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public String getAuthor() {
		return author;
	}
	public String getPublisher() {
		return publisher;
	}
	public String getrTitle() {
		return rTitle;
	}
	public String getrContent() {
		return rContent;
	}
	public RecomFile getRecomFile() {
		return recomFile;
	}
	public int getLikeIt() {
		return likeIt;
	}
	public int getrCnt() {
		return rCnt;
	}
	public Date getRegDate() {
		return regDate;
	}
	public Date getModDate() {
		return modDate;
	}

	@Override
	public String toString() {
		return "UpdateRequest [rNo=" + rNo + ", user=" + user + ", bookTitle=" + bookTitle + ", author=" + author
				+ ", publisher=" + publisher + ", rTitle=" + rTitle + ", rContent=" + rContent + ", recomFile="
				+ recomFile + ", likeIt=" + likeIt + ", rCnt=" + rCnt + ", regDate=" + regDate + ", modDate=" + modDate
				+ "]";
	}

	public void validate(Map<String, Boolean> errors) {
		if(rTitle==null || rTitle.trim().isEmpty()) {
			errors.put("rTitle", Boolean.TRUE);
		}
	}
}
