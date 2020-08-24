package ir.kn.dblayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHelper {

        private static final String DB_URL = "Your database url.";;
	private static final String DB_NAME = "Your database name.";
	private static final String DB_USER = "Your database user.";
	private static final String DB_PASSWORD = "Your database password.";

	private static Connection con;

	private static Connection getConnection() throws SQLException {

    if (con == null) {
			con = DriverManager.getConnection(DB_URL + "/" + DB_NAME , DB_USER, DB_PASSWORD);
			System.out.println("Connection Obtained!");
		}
		return con;
	}

	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public static ResultSet executeQuery(String query) throws SQLException {
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		stmt.execute(query);
		return stmt.getResultSet();
	}

	static int executeUpdate(String query) throws SQLException {
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		return stmt.executeUpdate(query);
	}

	static int executeUpdateAndReturnGeneratedKey(String query) throws SQLException {
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			return rs.getInt(1);
		}

		return 0;
	}
}
