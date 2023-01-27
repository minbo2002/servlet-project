package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//로그인 여부를 검사하는 필터역할의 클래스(p.612)
/*로그인 하지 않았으면 로그인 화면으로, 로그인을 하였다면 요청한 기능을 실행한다.*/
public class LoginCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		//그냥 Servlet보다 HttpServlet이 상위 인터페이스이므로  상위 인터페이스의 변수에 값을 넘겨 저장 
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(false);
		
		//세션이 없거나 세션에 들어간 유저의 정보가 없다면 강제로 로그인 페이지 이동
		if(session == null || session.getAttribute("authUser")==null) {
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect(request.getContextPath()+"/login.do");
		}
		else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
