package fr.jee.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.jee.model.Plat;

public class JdbcDao {
	private static List<Plat> plats = new ArrayList<Plat>();

	private static String platsStmt = "SELECT id, name, tarif FROM plat";

	public static Connection getConnection() {
		// Allocate a database 'Connection' object
		// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant?useSSL=false", "root", "1234");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static List<Plat> getPlats() {
		try {

			// Allocate a 'Statement' object in the Connection
			Statement stmt = JdbcDao.getConnection().createStatement();

			// Execute a SQL SELECT query, the query result is returned in a 'ResultSet' object.
			ResultSet rs = stmt.executeQuery(platsStmt);

			// Process the ResultSet by scrolling the cursor forward via next()
			int rowCount = 0;
			while (rs.next()) { 
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int tarif = rs.getInt("tarif");
				System.out.println(id + ", " + name + ", " + tarif);

				plats.add(new Plat(id, name, tarif));
				++rowCount;
			}
			System.out.println("Total number of records = " + rowCount);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcDao.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return plats;
	}
}
