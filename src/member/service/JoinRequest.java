package member.service;

import java.util.Map;

//이 클래스는 회원가입 요청시 필요한 (회원이 입력한) 회원정보 설정 및 제공하는 클래스이다.
//insert into member(memberid, memberpwd, membername, email, regdate, grade) values('adminid', '123', '관리자', 'admin@test.com', '2022-01-01 00:00:00', 999);
public class JoinRequest {
	//필드
	private int m_no;
	private String mId;
	private String mPwd;
	private String re_mPwd;
	private String mName;
	private String email;
	private String gender;
	private String addr;
	
	//생성자
	
	
	//메서드
	//getter&setter
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmPwd() {
		return mPwd;
	}
	public void setmPwd(String mPwd) {
		this.mPwd = mPwd;
	}
	public String getRe_mPwd() {
		return re_mPwd;
	}
	public void setRe_mPwd(String re_mPwd) {
		this.re_mPwd = re_mPwd;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
	//비밀번호와 재확인용 비밀번호 일치 체크(p.594-44 line)
	public boolean isPasswordEqualsToConfirm() {
		return mPwd!=null && mPwd.equals(re_mPwd);
	}

	//유효성검사(p.595 48line)
	public void validate(Map<String, Boolean> errors) {
		//필수 입력 체크 메서드 호출 
		checkEmpty(errors, this.mId, "mId"); //아이디 필수 입력
		checkEmpty(errors, this.mPwd, "mPwd"); //비밀번호 필수 입력
		checkEmpty(errors, this.re_mPwd, "re_mPwd"); //비밀번호 재확인 필수 입력
		checkEmpty(errors, this.mName, "mName"); //이름 필수 입력
		checkEmpty(errors, this.email, "email"); //이메일 필수 입력
		checkEmpty(errors, this.addr, "addr"); //주소 필수 입력
		//여기에서는 이메일1과 이메일2를 html5의 required속성으로 유효성 검증 처리하여 생략
		
		if(!errors.containsKey("re_mPwd")) {
			//비밀번호와 재확인용 비밀번호 일치 체크
			if(!isPasswordEqualsToConfirm()) { //비밀번호와 재확인용 비밀번호 일치 체크
				errors.put("notMatch", Boolean.TRUE);
			}
		}
		
	}
	
	//필수입력체크(p.595 60line)
	private void checkEmpty(Map<String, Boolean> errors, String value, String fieldName) {
		if(value==null || value.isEmpty()) {
			//유저가 입력(선택)한 값을 value / joinForm.jsp의 폼안에 각 input의 name속성을 key로 사용
			errors.put(fieldName, Boolean.TRUE);
		}
	}
	
	@Override
	public String toString() {
		return "JoinRequest [m_no=" + m_no + ", mId=" + mId + ", mPwd=" + mPwd + ", re_mPwd=" + re_mPwd + ", mName="
				+ mName + ", email=" + email + ", gender=" + gender + ", addr=" + addr + "]";
	}

}
