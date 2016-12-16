package dic_other;
import java.sql.*;

public class DBForDic {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//System.out.println("driver loaded");
		
		Connection connection = DriverManager.getConnection(
			"jdbc:sqlserver://localhost:1433;DatabaseName = JavaDic", "sa", "1204fox");
		//System.out.println("database connected");
		
		Statement statement = connection.createStatement();
		
		statement.execute("");
		
		//ResultSet resultset = statement.executeQuery("select * from Student");
		//while(resultset.next())
			//System.out.println(resultset.getString(1) + "\t" +
					//resultset.getString(2) + "\t" + resultset.getString(3));
		
		connection.close();
	}
}
