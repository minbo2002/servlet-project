package recommand.domain;

import java.util.Date;

import member.model.Member;
import member.model.User;

public class RecomBoard {

	private int rNo;		 // 추천게시판 번호(PK)
	private Member member;   // 회원
	private String bookTitle;	// 책제목
	private String author;		// 저자
	private String publisher;	// 출판사
	private String rTitle;	 // 게시판 제목
	private String rContent; // 게시판 내용
	private int likeIt;		 // 좋아요
	private int rCnt;		 // 조회수
	private Date regDate;	 // 등록일자	
	private Date modDate; 	 // 수정일자
	private int mNo;		 // 회원번호(FK)	

	// 상세보기를 위한 생성자
	public RecomBoard(int rNo, Member member, String bookTitle, String author, String publisher, String rTitle,
			String rContent, int likeIt, int rCnt, Date regDate, Date modDate, int mNo) {
		this.rNo = rNo;
		this.member = member;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publisher = publisher;
		this.rTitle = rTitle;
		this.rContent = rContent;
		this.likeIt = likeIt;
		this.rCnt = rCnt;
		this.regDate = regDate;
		this.modDate = modDate;
		this.mNo = mNo;
	}
	
	// 게시판 전체목록을 가져오기위한 생성자
	public RecomBoard(int rNo, Member member, String rTitle,
			String rContent, int likeIt, int rCnt, Date regDate, Date modDate, int mNo) {
		this.rNo = rNo;
		this.member = member;
		this.rTitle = rTitle;
		this.rContent = rContent;
		this.likeIt = likeIt;
		this.rCnt = rCnt;
		this.regDate = regDate;
		this.modDate = modDate;
		this.mNo = mNo;
	}

	// 게시판 글쓰기 데이터를 가져오기위한 생성자
	public RecomBoard(String bookTitle, String author, String publisher, String rTitle, String rContent, int likeIt, 
			int rCnt, Date regDate, Date modDate, int mNo) {
		this.bookTitle = bookTitle;
		this.author = author;
		this.publisher = publisher;
		this.rTitle = rTitle;
		this.rContent = rContent;
		this.likeIt = likeIt;
		this.rCnt = rCnt;
		this.regDate = regDate;
		this.modDate = modDate;
		this.mNo = mNo;
	}
	
	// 게시판 등록이후 DB에 insert된 게시글 데이터를 가져오기위한 생성자
	public RecomBoard(int rNo, String bookTitle, String author, String publisher, String rTitle, String rContent, 
			int likeIt, int rCnt, Date regDate, Date modDate, int mNo) {
		this.rNo = rNo;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publisher = publisher;
		this.rTitle = rTitle;
		this.rContent = rContent;
		this.likeIt = likeIt;
		this.rCnt = rCnt;
		this.regDate = regDate;
		this.modDate = modDate;
		this.mNo = mNo;
	}

	public int getrNo() {
		return rNo;
	}
	public Member getMember() {
		return member;
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
	public int getmNo() {
		return mNo;
	}

	@Override
	public String toString() {
		return "RecomBoard [rNo=" + rNo + ", member=" + member + ", bookTitle=" + bookTitle + ", author=" + author
				+ ", publisher=" + publisher + ", rTitle=" + rTitle + ", rContent=" + rContent + ", likeIt=" + likeIt
				+ ", rCnt=" + rCnt + ", regDate=" + regDate + ", modDate=" + modDate + ", mNo=" + mNo + "]";
	}

}
