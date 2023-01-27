package recommand.model;

import recommand.domain.RecomBoard;
import recommand.domain.RecomFile;

public class BoardData {

	private RecomBoard recomBoard;
	private RecomFile recomFile;
	
	public BoardData() {
		
	}
	
	public BoardData(RecomBoard recomBoard, RecomFile recomFile) {
		this.recomBoard = recomBoard;
		this.recomFile = recomFile;
	}

	public RecomBoard getRecomBoard() {
		return recomBoard;
	}
	public RecomFile getRecomFile() {
		return recomFile;
	}

	@Override
	public String toString() {
		return "BoardData [recomBoard=" + recomBoard + ", recomFile=" + recomFile + "]";
	}
}
