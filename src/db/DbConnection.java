package db;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import oracle.jdbc.pool.OracleDataSource;

public class DbConnection 
{

	public static OracleDataSource initConnection(){
		OracleDataSource oracleDS = null;
		try {
			oracleDS = new OracleDataSource();
			oracleDS.setURL("jdbc:oracle:thin:@212.33.90.213:1521:xe");
			oracleDS.setUser("bartosz_stefaniuk");
			oracleDS.setPassword("tabpass");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return oracleDS;
	}
	
 	public static Connection getConnection(OracleDataSource ods) {
 		Connection con = null;
 		try {
 			con = ods.getConnection();
 		}catch(SQLException e) {
 			JOptionPane.showMessageDialog(null, e);
 		}
		return con;
	}
    
}
