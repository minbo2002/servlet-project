package member.model;

import java.util.Date;

public class Member {

	private int m_no;
	private String mId;
	private String mPwd;
	private String mName;
	private String email;
	private String gender;
	private String addr;
	private Date mJoin;
	private int grade;
		
	public Member(int m_no, String mId, String mPwd, String mName, String email, String gender, String addr,  Date mJoin, int grade) {
		this.m_no = m_no;
		this.mId = mId;
		this.mPwd = mPwd;
		this.mName = mName;
		this.email = email;
		this.gender = gender;
		this.addr = addr;
		this.mJoin = mJoin;
		this.grade = grade;
	}
	
	public Member(String mId, String mPwd, String mName, String email, String gender, String addr, Date mJoin, int grade) {
		this.mId = mId;
		this.mPwd = mPwd;
		this.mName = mName;
		this.email = email;
		this.gender = gender;
		this.addr = addr;
		this.mJoin = mJoin;
		this.grade = grade;
	}
	
	public Member(int m_no, String mId, String mName) {
		this.m_no = m_no;
		this.mId = mId;
		this.mName = mName;
	}
	
	public Member(String mId, String mName) {
		this.mId = mId;
		this.mName = mName;
	}
	
	// 비밀번호 매칭확인
	// 매개변수pwd에 저장된값과 mPwd 필드의 값이 일치하면 true 반환
	public boolean matchPassword(String pwd) {
		return mPwd.equals(pwd);
	}
	
	// 회원이름 매칭확인
	public boolean matchMemberName(String name) {
		return mName.equals(name);
	}
	
	public boolean matchMemberEmail(String mEmail) {
		return email.equals(mEmail);
	}
	
	public boolean matchMemberId(String id) {
		return mId.equals(id);
	}
	
	// user가 입력한 새로운 newPwd를  Member클래스의 필드 memberpwd의 값으로 설정
	public void changePassword(String newPwd) {
		this.mPwd = newPwd;		
	}
	
	
	public int getM_no() {
		return m_no;
	}
	public String getmId() {
		return mId;
	}
	public String getmPwd() {
		return mPwd;
	}
	public String getmName() {
		return mName;
	}
	public String getEmail() {
		return email;
	}
	public String getGender() {
		return gender;
	}
	public String getAddr() {
		return addr;
	}
	public Date getmJoin() {
		return mJoin;
	}
	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return "Member [m_no=" + m_no + ", mId=" + mId + ", mPwd=" + mPwd + ", mName=" + mName + ", email=" + email
				+ ", gender=" + gender + ", addr=" + addr + ", mJoin=" + mJoin + ", grade=" + grade + "]";
	}
}
