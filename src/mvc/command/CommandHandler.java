package mvc.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// p531
// 모든 Controller 클래스에서 process() 추상메서드를 오버라이딩하여 강제구현을 하게한다.  
public interface CommandHandler {

	public abstract String process(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
