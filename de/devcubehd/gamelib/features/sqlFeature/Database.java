package de.devcubehd.gamelib.features.sqlFeature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	private static Database Instance;
	
	private String Host;
	private int Port;
	private String Data;
	private String User;
	private String Pass;
	private boolean autoReconnect;
	
	private Connection connect;
	
	public Database(String address, int port, String database, String username, String password, boolean autoReconnect) {
		Instance = this;
		this.Host = address;
		this.Port = port;
		this.Data = database;
		this.User = username;
		this.Pass = password;
		this.autoReconnect = autoReconnect;
	}
	
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://" + Host + ":" + Port + "/" + Data + "?user=" + User + "&password=" + Pass + "&autoReconnect=" + autoReconnect);
		this.connect = con;
		return connect;
	}
	
	public Connection getConnection() {
		return this.connect;
	}
	
	public boolean hasConnection() {
		try {
			return this.connect != null || this.connect.isValid(1);
		} catch (SQLException e) {
			return false;
		}
	}
	
	public void reconnect() throws ClassNotFoundException, SQLException {
		if(connect == null || connect.isClosed()) {
			openConnection();
		} else {
			return;
		}
	}
	
	public Table getTable(String tablename) {
		return new Table(this, tablename);
	}
	
	public void queryUpdate(String query) throws SQLException {
		Connection con = this.connect;
	    PreparedStatement st = null;
	    st = con.prepareStatement(query);
	    st.executeUpdate();
	    closeRessources(null, st);
	}
	
	public ResultSet getQuery(String query) throws SQLException {
		ResultSet rs = null;
		PreparedStatement stmt = connect.prepareStatement(query);
		rs = stmt.executeQuery(query);
		return rs;
	}
	
	public void closeRessources(ResultSet rs, PreparedStatement st) throws SQLException {
		if(rs != null) {
			rs.close();
		}
		if(st != null) {
			st.close();
		}
	}
	
	public void closeConnection() throws SQLException {
		this.connect.close();
		this.connect = null;
	}
	
	public static Database getInstance() {
		return Instance;
	}
}