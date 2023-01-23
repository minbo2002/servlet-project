package recommand.domain;

import java.util.Date;

import member.domain.Member;

public class RecomBoard {

	private int rNo;		 // 책추천 번호
	private Member member;   // 회원
	private String rTitle;	 // 게시판 제목
	private String rContent; // 게시판 내용
	private int likeIt;		 // 좋아요
	private int rCnt;		 // 조회수
	private Date regDate;	 // 등록일자	
	private Date modDate; 	 // 수정일자
	private int mNo;		 // 회원번호(FK)	

	public RecomBoard(int rNo, Member member, String rTitle, String rContent, int likeIt, int rCnt, Date regDate, Date modDate, int mNo) {
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
	
	public int getrNo() {
		return rNo;
	}
	public Member getMember() {
		return member;
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
		return "RecomBoard [rNo=" + rNo + ", member=" + member + ", rTitle=" + rTitle + ", rContent=" + rContent
				+ ", likeIt=" + likeIt + ", rCnt=" + rCnt + ", regDate=" + regDate + ", modDate=" + modDate + ", mNo=" + mNo + "]";
	}
	
	
}
