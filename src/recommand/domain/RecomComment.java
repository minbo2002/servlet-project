package recommand.domain;

import java.util.Date;

public class RecomComment {

	private int rCmtNo;			 // 댓글번호
	private String rCmtContent;	 // 댓글내용
	private Date regDate;		 // 작성일
	private Date modDate;		 // 수정일
	
	public int getrCmtNo() {
		return rCmtNo;
	}
	public String getrCmtContent() {
		return rCmtContent;
	}
	public Date getRegDate() {
		return regDate;
	}
	public Date getModDate() {
		return modDate;
	}
	
	@Override
	public String toString() {
		return "RecomComment [rCmtNo=" + rCmtNo + ", rCmtContent=" + rCmtContent + ", regDate=" + regDate + ", modDate="
				+ modDate + "]";
	}
}
