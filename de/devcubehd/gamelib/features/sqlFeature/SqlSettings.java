package de.devcubehd.gamelib.features.sqlFeature;

public class SqlSettings {
	
	 private String host;
	 private int port;
	 private String database;
	 private String username;
	 private String password;
	 private String table;
	 
	 public SqlSettings(String host, int port, String database, String username, String password, String table) {
		 this.host = host;
		 this.port = port;
		 this.database = database;
		 this.username = username;
		 this.password = password;
		 this.table = table;
	 }
	 
	 public String getHost() {
		 return host;
	 }
	 
	 public int getPort() {
		 return port;
	 }
	 
	 public String getDatabase() {
		 return database;
	 }
	 
	 public String getUsername() {
		 return username;
	 }
	 
	 public String getPassword() {
		 return password;
	 }
	 
	 public String getTable() {
		 return table;
	 }
	 
}
