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

	public UpdateRequest(int rNo, User user, String bookTitle, String author, String publisher, String rTitle,
			String rContent, RecomFile recomFile) {
		this.rNo = rNo;
		this.user = user;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publisher = publisher;
		this.rTitle = rTitle;
		this.rContent = rContent;
		this.recomFile = recomFile;
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
	
	@Override
	public String toString() {
		return "UpdateRequest [rNo=" + rNo + ", user=" + user + ", bookTitle=" + bookTitle + ", author=" + author
				+ ", publisher=" + publisher + ", rTitle=" + rTitle + ", rContent=" + rContent + ", recomFile=" + recomFile + "]";
	}
	
	public void validate(Map<String, Boolean> errors) {
		if(rTitle==null || rTitle.trim().isEmpty()) {
			errors.put("rTitle", Boolean.TRUE);
		}
	}
}
