package member.model;

//이 클래스는 로그인한 user에 대한 정보를 다루는 클래스이다.
public class User {
	
	//필드
	private int m_no;
	private String mId;
	private String mName;
	private int grade;
	private String gender;
	
	//생성자
	public User(int m_no, String mId, String mName, int grade, String gender) {
		this.m_no = m_no;
		this.mId = mId;
		this.mName = mName;
		this.grade = grade;
		this.gender = gender;
	}
	
	public User(int m_no, String mId) {
		this.m_no = m_no;
		this.mId = mId;
	}

	//메서드
	public int getM_no() {
		return m_no;
	}

	public String getmId() {
		return mId;
	}

	public String getmName() {
		return mName;
	}

	public int getGrade() {
		return grade;
	}
	public String getGender() {
		return gender;
	}

	@Override
	public String toString() {
		return "User [m_no=" + m_no + ", mId=" + mId + ", mName=" + mName + ", grade=" + grade + ", gender=" + gender + "]";
	}

	

	
	
}
