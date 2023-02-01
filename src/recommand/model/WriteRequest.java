package recommand.model;

import java.util.Map;

import member.model.User;
import recommand.domain.RecomFile;

public class WriteRequest {

	private User user;			 // 유저
	private String bookTitle;	 // 책제목
	private String author;		 // 저자
	private String publisher;	 // 출판사
	private String rTitle;		 // 게시판 제목
	private String rContent;     // 게시판 내용
	private RecomFile recomfile; // 파일
	
	public WriteRequest(User user, String bookTitle, String author, String publisher, String rTitle, String rContent, RecomFile recomfile) {
		this.user = user;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publisher = publisher;
		this.rTitle = rTitle;
		this.rContent = rContent;
		this.recomfile = recomfile;
	}

	// 유효성검사 - 필수입력체크
	public void validate(Map<String, Boolean> errors) {
		if( bookTitle==null || bookTitle.isEmpty() ) {
			errors.put("bInfoTitle", Boolean.TRUE);
		}
		if( author==null || author.isEmpty()) {
			errors.put("author", Boolean.TRUE);
		}
		if( publisher==null || publisher.isEmpty()) {
			errors.put("publisher", Boolean.TRUE);
		}
		if( rTitle==null || rTitle.isEmpty()) {
			errors.put("rTitle", Boolean.TRUE);
		}
		if( rContent==null || rContent.isEmpty()) {
			errors.put("rContent", Boolean.TRUE);
		}
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getrTitle() {
		return rTitle;
	}
	public void setrTitle(String rTitle) {
		this.rTitle = rTitle;
	}
	public String getrContent() {
		return rContent;
	}
	public void setrContent(String rContent) {
		this.rContent = rContent;
	}
	public RecomFile getRecomfile() {
		return recomfile;
	}
	public void setRecomfile(RecomFile recomfile) {
		this.recomfile = recomfile;
	}

	@Override
	public String toString() {
		return "WriteRequest [user=" + user + ", bookTitle=" + bookTitle + ", author=" + author + ", publisher="
				+ publisher + ", rTitle=" + rTitle + ", rContent=" + rContent + ", recomfile=" + recomfile + "]";
	}

}
