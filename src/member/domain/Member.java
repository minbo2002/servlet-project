package member.domain;

import java.util.Date;

public class Member {

	private int mNo;
	private String mId;
	private String mName;
	private String mPwd;
	private String email;
	private int gender;
	private String addr;
	private int grade;
	private Date mJoin;
		
	public Member(int mNo, String mId, String mName, String mPwd, String email, int gender, String addr, int grade, Date mJoin) {
		this.mNo = mNo;
		this.mId = mId;
		this.mName = mName;
		this.mPwd = mPwd;
		this.email = email;
		this.gender = gender;
		this.addr = addr;
		this.grade = grade;
		this.mJoin = mJoin;
	}
	
	public Member(String mId, String mName) {
		this.mId = mId;
		this.mName = mName;
	}
	
	public int getmNo() {
		return mNo;
	}
	public String getmId() {
		return mId;
	}
	public String getmName() {
		return mName;
	}
	public String getmPwd() {
		return mPwd;
	}
	public String getEmail() {
		return email;
	}
	public int getGender() {
		return gender;
	}
	public String getAddr() {
		return addr;
	}
	public int getGrade() {
		return grade;
	}
	public Date getmJoin() {
		return mJoin;
	}
	
	@Override
	public String toString() {
		return "Member [mNo=" + mNo + ", mId=" + mId + ", mName=" + mName + ", mPwd=" + mPwd + ", email=" + email
				+ ", gender=" + gender + ", addr=" + addr + ", grade=" + grade + ", mJoin=" + mJoin + "]";
	}

}
