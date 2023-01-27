package recommand.domain;

public class RecomFile {

	private int rFileNo;	      // 파일번호	
	private String filename;	  // 사용자가 올린 파일의 이름
	private String fileRealName;  //  서버에 저장된 파일이름
	private int m_no;	// 회원 PK를 FK로 사용
	private int r_no;	// 추천게시판 PK를 FK로 사용
	
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
