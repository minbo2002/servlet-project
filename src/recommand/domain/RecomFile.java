package recommand.domain;

public class RecomFile {

	private int rFileNo;	      // 파일번호(PK)
	private String filename;	  // 사용자가 지정한 파일이름
	private String fileRealName;  // DB에 저장되는 파일이름(사용자가 지정한 파일이름이 중복될경우 뒤에 숫자붙음)
	private int m_no;	// 회원번호(FK)
	private int r_no;	// 추천게시판 번호(FK)
	
	public RecomFile(int rFileNo, String filename, String fileRealName, int m_no, int r_no) {
		this.rFileNo = rFileNo;
		this.filename = filename;
		this.fileRealName = fileRealName;
		this.m_no = m_no;
		this.r_no = r_no;
	}
	
	public RecomFile(String filename, String fileRealName, int m_no, int r_no) {
		this.filename = filename;
		this.fileRealName = fileRealName;
		this.m_no = m_no;
		this.r_no = r_no;
	}
	
	public RecomFile(String filename, String fileRealName) {
		this.filename = filename;
		this.fileRealName = fileRealName;
	}

	public int getrFileNo() {
		return rFileNo;
	}
	public String getFilename() {
		return filename;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public int getM_no() {
		return m_no;
	}
	public int getR_no() {
		return r_no;
	}

	@Override
	public String toString() {
		return "RecomFile [rFileNo=" + rFileNo + ", filename=" + filename + ", fileRealName=" + fileRealName + ", m_no="
				+ m_no + ", r_no=" + r_no + "]";
	}
}
