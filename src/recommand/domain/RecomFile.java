package recommand.domain;

import java.util.Date;

public class RecomFile {

	private int rFileNo;	    // 파일번호	
	private String rFileName;	// 파일이름
	private String rFilePath;	// 파일경로
	
	public int getrFileNo() {
		return rFileNo;
	}
	public String getrFileName() {
		return rFileName;
	}
	public String getrFilePath() {
		return rFilePath;
	}
	
	@Override
	public String toString() {
		return "RecomFile [rFileNo=" + rFileNo + ", rFileName=" + rFileName + ", rFilePath=" + rFilePath + "]";
	}
}
