package jdbc.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// p586
// web.xml 문서에서 poolName=board로 지정한다음 pool 이름으로 사용하여 Conenction을 제공하는 클래스 
public class ConnectionProvider {

	// [접근제한자] 제어자 반환타입 메서드명(매개변수)
	public static Connection getConnection() {  // static 메서드이므로  ConnectionProvider.getConnection() 으로 바로 호출가능
		
		Connection connection = null;
		
		try {
			// web.xml 파일에서 설정해준 poolName 변수로 지정한 board를 써준다.
			connection = DriverManager.getConnection("jdbc:apache:commons:dbcp:bookcommunity");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}