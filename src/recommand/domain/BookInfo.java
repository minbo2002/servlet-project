package recommand.domain;

public class BookInfo {

	private int bInfoNo;		// 책번호
	private String bInfoTitle;	// 책제목
	private String author;		// 저자
	private String publisher;	// 출판사
	
	public int getbInfoNo() {
		return bInfoNo;
	}
	public String getbInfoTitle() {
		return bInfoTitle;
	}
	public String getAuthor() {
		return author;
	}
	public String getPublisher() {
		return publisher;
	}
	
	@Override
	public String toString() {
		return "BookInfo [bInfoNo=" + bInfoNo + ", bInfoTitle=" + bInfoTitle + ", author=" + author + ", publisher="
				+ publisher + "]";
	}
}
