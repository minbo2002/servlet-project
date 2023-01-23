package jdbc;

import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

//커넥션관련코드(p418->p510->p573->)p583
//DBCP를 이용하여 커넥션풀 사용(P417참고)
public class DBCPInitListener implements ServletContextListener {

	/*
	     실행순서 => 1. ServletContextListener 인터페이스를 구현한 클래스를 작성   ex) public class DBCPInitListener implements ServletContextListener {...}  )

			   2. 웹컨테이너 구동   
	     	   
	     	   3. web.xml 파일에 등록된 <param-name> 태그 내부의 초기화 파리미터를 통해서
	     	      DBCPInitListener 객체의 contextInitialized() 메서드가 실행되면서 커넥션풀 초기화.
	     	   	  
	     	   	  Properties 클래스의 객체인 prop 인스턴스변수에다가 .getProperty() 메서드 이용해서
	     	   	  web.xml 파일 내부의 <param-value> 태그안에 담긴 "key=value" 형태에서 value를 담는다.
	     	   	  
	     	   	   이후에  loadJDBCDriver() 메서드 및 initConnectionPool() 메서드 호출
	     	   
	     	   4. loadJDBCDriver() 메서드 및 initConnectionPool() 메서드가  
	     	   	    임의로 지정한 poolConfig 초기화 파라미터를 이용해서 생성한 Properties 객체인 prop 인스턴스변수로부터 커넥션풀을 생성하는데 필요한 값 읽어서
	     	   	  JDCBCDriver 로딩하고  커넥션풀 초기화한다. 
	*/
	
	//poolConfig 컨텍스트 초기화
	@Override 
	public void contextInitialized(ServletContextEvent sce) {
		
		String poolConfig = 
				sce.getServletContext().getInitParameter("poolConfig");
		 /* ServletContextEvent 클래스는 웹어플리케이션 컨텍스트를 구할 수 있는 getServletContext( ) 메서드를 제공.

			getServletContext( ) 메서드가 반환하는 ServletContext 객체의 메서드인  
			getInitParameter(String name) 메서드의 파라미터인 name을 지정해주면 해당 name값을
			web.xml 파일에서 <context-param> 태그내부의 <param-name> 태그에 입력해서 컨텍스트 초기화 파라미터를 설정. */
		
		
		/*
		    Properties 클래스  :  Map 컬렉션과 비슷하게 "key=value" 형태로된  .properties파일 또는 xml파일    
		   
		        값 가져오는 메서드  => .load(FileReader file)   :  스트림으로 열린 Properties 파일 객체를 로딩
		        		   .getProperty(String key) :  key값을 제공하면 해당되는 value값을 문자열로 반환			
		 */
		Properties prop = new Properties();  // Properties 객체인 prop 인스턴스변수 생성
		/* "키=값"형식의 문자열로부터 Properties를 로딩하기위해
		    String 키명=String 값   ==> ex) "dbUser=gangnam"  즉 Properties는 "dbUser"프로퍼티값으로  "gangnam"을 갖는다. */ 
		
		try {
			prop.load(new StringReader(poolConfig));  // prop.load() "키=값"형식의 문자열로부터 Properties를 로딩
		
		} catch (IOException e) {
			throw new RuntimeException("config load fail", e);
		}
		
		loadJDBCDriver(prop);     // JDBCDriver를 로딩
		initConnectionPool(prop); // ConnectionPool 초기화
	}
	
	
	//매개변수 Properties prop에 담겨온 내용을 이용하여 JDBCDriver를 로딩
	private void loadJDBCDriver(Properties prop) {
		
		// "키=값"형식
		// jdbcdriver=com.mysql.jdbc.Driver
		String driverClass = prop.getProperty("jdbcdriver");
		//com.mysql.jdbc.Driver값이  String타입의 driverClass변수에 저장
		
		try {
			Class.forName(driverClass);
			
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("fail to load JDBC Driver", ex);
		}
	}

	
	//ConnectionPool 초기화
	private void initConnectionPool(Properties prop) {
		
		try {
			//참고 jdbc:mysql://ip주소 : port번호/DB스키마명?characterEncoding=UTF-8&serverTimezone=UTC
			/*     키명   =  값
			 * jdbcUrl = jdbc:mysql://localhost:3306/board?characterEncoding=utf8&amp;serverTimezone=UTC
				dbUser = jspexam
				dbPass = jsppw
			 */
			String jdbcUrl = prop.getProperty("jdbcUrl");
			String username = prop.getProperty("dbUser");
			String pw = prop.getProperty("dbPass");

			ConnectionFactory connFactory = 
					new DriverManagerConnectionFactory(jdbcUrl, username, pw);

			// DBCP가 커넥션풀에 커넥션 보관할때 PoolableConnection 사용
			// 내부적으로 실제 커넥션을 담고있으며 커넥션풀을 관리
			PoolableConnectionFactory poolableConnFactory = 
					new PoolableConnectionFactory(connFactory, null);
			
			// 풀의 커넥션 (유효성)검사		
			String validationQuery = prop.getProperty("validationQuery");
			
			if (validationQuery != null && !validationQuery.isEmpty()) {
				poolableConnFactory.setValidationQuery(validationQuery);
			}
			
			// 커넥션풀의 설정정보 생성
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);  // 유효 커넥션 검사 주기(5분), 놀고있는 커넥션을 풀에서 제거하는 시간기준
			poolConfig.setTestWhileIdle(true);	// 보관중인 커넥션이 유효한지 검사할지 여부
			
			//최소 유휴 커넥션설정
			//최소 유휴 커넥션:사용되지 않고 풀에 저장될 수 있는 최소 커넥션 개수
			int minIdle = getIntProperty(prop, "minIdle", 5);
			poolConfig.setMinIdle(minIdle);
			
			//최대 유휴 커넥션설정
			int maxTotal = getIntProperty(prop, "maxTotal", 50);
			poolConfig.setMaxTotal(maxTotal);

			// 커넥션풀의 설정정보를 생성.  팩토리와 커넥션풀 설정을 파라미터로 받음.
			GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnFactory, poolConfig);
			// 폴러블커넥션팩토리에도 생성된 커넥션풀을 연결
			poolableConnFactory.setPool(connectionPool);
			
			//org.apache.commons.dbcp2패키지.PoolingDriver클래스를 불러오기
			// 커넥션풀을 제공하는 JDBC 드라이버 등록
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = (PoolingDriver)
				DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			
			//poolName을 이용해서 가져온 board값을
			//String타입의  poolName변수에 저장한다
			//여기에서는 ->String poolName="board";
			String poolName = prop.getProperty("poolName");  //poolName을 가져와 변수에 저장
			
			// 커넥션풀 드라이버에 생성된 커넥션풀을 등록
			driver.registerPool(poolName, connectionPool);
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	private int getIntProperty(Properties prop, String propName, int defaultValue) {
		String value = prop.getProperty(propName);
		if (value == null) return defaultValue;
		return Integer.parseInt(value);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	
	}

}

/* 왜 커넥션풀을 사용하는가?  DBCP가 무엇인가?(P415참고)
클라이언트에서 웹, 웹서버, DB 까지 한번 왔다갔다할 때
가장 시간이 많이 소요되는 곳은 웹서버에서 DB서버에 최초로 연결되어
Connection 객체를 생성하는 부분이다.

이 시간을 단축시킬 수 있는 것이 DBCP 이다. 
DB Connection Pool, 
즉 미리 DB서버와의 연결을 생성해놓고, 
DB서버와 통신이 필요할 때 Connection 객체를 빌려쓰는 것이다.

DBCP를 사용치 않으면 아래와 같은 과정을 거친다.
1.DB 서버 접속을 위해 JDBC 드라이버를 로드한다.
2.DB 접속 정보와 DriverManager.getConnection() Method를 통해 DB Connection 객체를 얻는다.
3.Connection 객체로 부터 쿼리를 수행하기 위한 PreparedStatement 객체를 받는다.
4.executeQuery를 수행하여 그 결과로 ResultSet 객체를 받아서 데이터를 처리한다.
   처리가 완료되면 처리에 사용된 리소스들을 close하여 반환한다.


**하지만, DBCP 를 사용하면
1.WAS가 실행되면서 미리 일정량의 DB Connection 객체를 생성하고 Pool 이라는 공간에 저장해 둔다.
2.HTTP 요청에 따라 필요할 때 Pool에서 Connection 객체를 가져다 쓰고 반환한다.
3. 이와 같은 방식으로 HTTP 요청 마다 DB Driver를 로드하고 물리적인 연결에 의한 Connection 객체를 생성하는 비용이 줄어들게 된다.


 이 때 최초 몇개의 Connection 객체를 생성할 것인가,
최소 몇개를 유지할 것인가,
최대 몇개를 유지할 것인가,
최대 동시 통신하는 Connection 은 몇개 까지 허용할 것인가
등등 을 properties 파일을 통해 설정하게 된다.


jdbcUrl = 어떤 디비에 연결되어있는가 (jdbc:mysql://localhost:3306/board?characterEncoding=utf8&amp;serverTimezone=UTC)
initialSize : 최초 시점에 getConnection() 를 통해 커넥션 풀에 채워 넣을 커넥션 개수 (default = 0)
maxTotal (1.x에서는 maxActive): 동시에 사용할 수 있는 최대 커넥션 개수 (default = 8)
maxIdle : Connection Pool에 반납할 때 최대로 유지될 수 있는 커넥션 개수 (default = 8)
minIdle : 최소한으로 유지할 커넥션 개수 (default = 0)
maxWaitMillis (1.x에서는 maxWait) : pool이 고갈되었을 경우 최대 대기 시간 (ms단위, default = -1 = 무한정)
testOnBorrow = Connection 을 얻어왔을 때 validationQuery를 던져 확인해볼까말까 설정
validationQuery = select 1 DBMS종류에 따라 달라질 수 있지만 보통 이렇게 사용 한다.
*/
