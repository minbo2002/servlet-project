package mvc.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import mvc.command.NullHandler;


//요청 URI를 명령어로 사용하기위한 클래스

public class ControllerUsingURI extends HttpServlet {
    
	// 필드
    // <커맨드, 핸들러인스턴스> 매핑 정보 저장
    private Map<String, CommandHandler> commandHandlerMap =	new HashMap<>();  // CommandHandler : 실제로 구현할 수 있는 파일형태

    
    //메서드
    //init()는 서블릿을 처음 메모리에 올릴때 실행되어, 서블릿을 초기화하며 처음에 한번만 실행. 
    public void init() throws ServletException {
		
    	// web.xml문서 설정부분에서   /WEB-INF/commandHandlerURI.properties를 가져와 String타입  configFile변수에 저장
    	// String타입의 configFile 변수에 저장되는 값은 "/WEB-INF/commandHandlerURI.properties"
        String configFile = getInitParameter("configFile");
        
        Properties prop = new Properties();  // Map 컬렉션과 비슷하게 "key=value" 형태로된 Properties 클래스의 객체인 prop인스턴스 변수생성.
        
        String configFilePath = getServletContext().getRealPath(configFile);  /* "/WEB-INF/commandHandlerURI.properties" 절대경로를
        																	      String타입의 configFilePath 변수에 저장                                        */
        
        //실행동작할 수 있는 파일로 만든다
        try (FileReader fis = new FileReader(configFilePath)) {  /* FileReader : 파일에 저장된 문자열을 읽음   
        														  			  	 즉  "/WEB-INF/commandHandlerURI.properties" 의 절대경로에서
     																			 commandHandlerURI.properties 파일을 읽음 			      */
        	
        	
        	prop.load(fis);  /*  prop.load() "키=값" 형식의 문자열로부터 Properties를 로딩  ==>  즉 commandHandlerURI.properties 파일에서 읽은값들을
        					 														 prop 변수에다가 
        					 														 	   
        					 															    key   =   value 
        					 													  	  	  join.do = member.command.JoinHandler
        					 										
        					 													             형태로 로딩한다.									*/
        } catch (IOException e) {
            throw new ServletException(e);
        }
        
        // keySet() : key를 Set컬렉션에 담아서 반환          iterator() : 자바에서 컬렉션(Set)에 저장되어있는 요소를 읽음
        // key목록을 가져오기 (파일에 저장된 문자열을 읽은것 뿐이라서 key값이 뭔지 모르기 때문에 .keySet() 메서드로 key를 꺼낸다
        Iterator keyIter = prop.keySet().iterator();
        
        //key가 있는 만큼 반복
        while (keyIter.hasNext()) {   // hasNext(): 다음 요소에 읽어 올 요소가 있는지 확인 하는 메소드 있으면 true, 없으면 false 를 반환
        	
            String command = (String) keyIter.next();  // next(): 다음 요소를 가져온다(key 한개씩 꺼내기)
            //꺼내온 key는 client의 요청으로서 
            
            String handlerClassName = prop.getProperty(command);  /*   .getProperty(String key) :  key값을 제공하면 해당되는 value값을 문자열로 반환
            																					       즉 String타입의 handlerClassName 변수에다가
            																					   value인 member.command.JoinHandler를 저장   			*/							
            try {
            	// 동작 할 수 있는 파일목록을 만든다.
                Class<?> handlerClass = Class.forName(handlerClassName);  /*  Class.forName() : 물리적인 클래스파일명을 매개변수로 넣어서 이에 해당하는 클래스를 반환
                																				(인스턴스화 되지않은 상태. 메모리에 아직 안올라옴)
                																				즉  member.command.JoinHandler 에서  JoinHandler 클래스를 반환 */
                
                CommandHandler handlerInstance = (CommandHandler) handlerClass.newInstance();  /* .newInstance() : 인스턴스화해서 메모리에 올림.  
                																								       반환타입이 Object여서 다운캐스팅한다. 
                																								       즉 JoinHandler클래스가 인스턴스화 됨     */

     
                // Map참조변수명.put(key,value)
                commandHandlerMap.put(command, handlerInstance);   // commandHandlerMap.put(join.do, JoinHandler)
            
            } catch (ClassNotFoundException | InstantiationException 
            		| IllegalAccessException e) {
                throw new ServletException(e);
            }
        }
    }

    
    //doGet()는 get방식으로 요청시 호출되는 메서드
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    
    //doPost()는 post방식으로 요청시 호출되는 메서드
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    
    /*URI를 명령어로 사용하려먼 컨트롤러 서블릿의 process()에서 인식할 수 있어야 한다.*/
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//요청URI에서 request.getContextPath()부분을 제거하여 요청URI만 사용하기
    	
		String command = request.getRequestURI();  /* request.getRequestURI() : 요청 URI에서 프로젝트의 ContextPath+파일경로를 가져옴. 
																				ex) http://localhost:8088/mBoard/join.do 
																				String타입의 command 변수에   "mBoard/join.do" 저장	 */
		
		
		if (command.indexOf(request.getContextPath()) == 0) {    /* request.getContextPath() : 프로젝트 ContextPath만 가져옴
		 															.indexOf() : 특정문자열이 처음으로 나타나는 index값을 반환 
		 															
		 															즉 command.indexOf(mBoard) =>  "mBoard/join.do".indexOf(mBoard) = 0	*/
			
			command = command.substring(request.getContextPath().length());   /* .substring(startIndex, endIndex) : 시작index부터 종료index까지 부분문자열 반환  
																				 .substring(startIndex) :  시작index부터 끝까지 부분문자열 반환
			 																	
			 																	 즉 command.substring(6) = "mBoard/join.do".substring(6) = join.do	*/
			 																												
		}
		
		// 담당컨트롤러를 가져오기
		// 위에서 선언했던 Map<String, CommandHandler> commandHandlerMap에서
		// 키에 해당하는 command를 이용하여
		// 값에 해당하는 CommandHandler를 Map에서 꺼낸다
        CommandHandler handler = commandHandlerMap.get(command);   /*  commandHandlerMap.get(command)  ->  Map(join.do, JoinHandler).get(join.do) = JoinHandler클래스
        
         																												*/
        if (handler == null) {
            handler = new NullHandler();
        }
        
        String viewPage = null;
        
        try {
        	//(handler 변수에 가져온) 담당컨트롤러 서블릿 클래스의 process()메서드를 호출
        	//(여기에서는)모든 컨트롤러는 반드시 CommandHandler인터페이스를 구현하고 있다.
        	//모든 컨트롤러의 process() 메서드는  String타입의 view(jsp문서)를 반환하는 String타입 viewPage에 저장
            viewPage = handler.process(request, response);
        
        } catch (Throwable e) {
            throw new ServletException(e);
        }
        
        if (viewPage != null) {//viewPage가 존재하면
        	//해당  viewPage로 페이지를 이동해라-> 브라우저에 출력해라
	        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);  // .getRequestDispatcher(parameter) : parameter 인자값으로 이동할 페이지 경로인(뷰페이지경로.jsp) 지정 
	        
	        dispatcher.forward(request, response);  // .forward()      : 페이지출력, 페이지 전환
	        										// .sendRedirect() : 특정 url로 재요청
        }
    }
}








