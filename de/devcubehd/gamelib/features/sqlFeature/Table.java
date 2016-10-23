package de.devcubehd.gamelib.features.sqlFeature;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.devcubehd.gamelib.features.sqlFeature.Operators.DataTypes;

public class Table 
{
	Database dbase;
	private String tablename;
	
	public Table(Database dbase, String tablename) {
		this.dbase = dbase;
		this.tablename = tablename;
	}
	
	public boolean exists() {
		String qry = "DESCRIBE " + getTablename();
		try {
			dbase.queryUpdate(qry);
			return true;
		}  catch (SQLException e) {
			return false;
		}
	}
	
	public void create(String[] columns, DataTypes[] types) throws SQLException {
		String vals = getTableValues(columns, types);
		String qry = "CREATE TABLE " + getTablename() + " (" + vals + ")";
		dbase.queryUpdate(qry);
	}
	
	public void clear() throws SQLException {
		String qry = "TRUNCATE " + getTablename();
		dbase.queryUpdate(qry);
	}
	
	public void delete() throws SQLException {
		String qry = "DROP TABLE " + getTablename();
		dbase.queryUpdate(qry);
	}
	
	public void delete(String row, Object search) throws SQLException  {
		String qry = "DELETE FROM " + getTablename() + " WHERE " + row + "='" + search + "'";
		dbase.queryUpdate(qry);
	}
	
	public boolean contains(String row, Object search) throws SQLException {
		
		boolean out;
		String qry = "SELECT * FROM " + getTablename() + " WHERE " + row + "='" + search + "'";
		ResultSet rs = dbase.getQuery(qry);
		
		if(rs.next()) {
			out = true;
		} else {
			out = false;
		}
		
		rs.close();
		return out;
		
	}
	
	public int size() throws SQLException {
		
		int out = 0; 
		ResultSet rs = dbase.getQuery("SELECT COUNT(*) FROM  " + getTablename());
        
        while(rs.next()) {
        	out = rs.getInt(1);
        }
        
        rs.close();
		return out;
		
	}
	
	public Object get(String column, String row, Object search) throws SQLException {
		
		Object out = null;
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " WHERE " + row + "='" + search + "'");
        
        while(rs.next()) {
        	out = rs.getObject(1);
        }
        
        rs.close();
		return out;
		
	}
	
	public String getString(String column, String row, Object search) throws SQLException {
		
		String out = null; 
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " WHERE " + row + "='" + search + "'");
        
        while(rs.next()) {
        	out = rs.getString(1);
        }
        
        rs.close();
		return out;
		
	}
	
	public boolean getBoolean(String column, String row, Object search) throws SQLException {
		
		boolean out = false; 
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " WHERE " + row + "='" + search + "'");
        
        while(rs.next()) {
        	out = rs.getBoolean(1);
        }
        
        rs.close();
		return out;
		
	}
	
	public int getInt(String column, String row, Object search) throws SQLException {
		
		int out = 0; 
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " WHERE " + row + "='" + search + "'");
        
        while(rs.next()) {
        	out = rs.getInt(1);
        }
        
        rs.close();
		return out;
		
	}
	
	public double getDouble(String column, String row, Object search) throws SQLException {
		
		double out = 0; 
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " WHERE " + row + "='" + search + "'");
        
        while(rs.next()) {
        	out = rs.getDouble(1);
        }
        
        rs.close();
		return out;
		
	}
	
	public long getLong(String column, String row, Object search) throws SQLException {
		
		long out = 0; 
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " WHERE " + row + "='" + search + "'");
        
        while(rs.next()) {
        	out = rs.getLong(1);
        }
        
        rs.close();
		return out;
		
	}
	
	public float getFloat(String column, String row, Object search) throws SQLException {
		
		float out = 0; 
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " WHERE " + row + "='" + search + "'");
        
        while(rs.next()) {
        	out = rs.getFloat(1);
        }
        
        rs.close();
		return out;
		
	}
	
	public List<Object> getSortedList(String column, String sort, int size) throws SQLException {
		
		List<Object> out = new ArrayList<>();
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " ORDER BY " + sort + " DESC LIMIT " + size);
		
		while(rs.next()) {
			out.add(rs.getObject(column));
		}
		
		rs.close();
		return out;
		
	}
	
	public List<Object> getSortedList(String column, String sort) throws SQLException {
		
		List<Object> out = new ArrayList<>();
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " ORDER BY " + sort + " DESC");
		
		while(rs.next()) {
			out.add(rs.getObject(column));
		}
		
		rs.close();
		return out;
		
	}
	
	
	public Map<Integer, Object> getSortedMap(String column, String sort, int size) throws SQLException {
		
		Map<Integer, Object> out = new HashMap<>();
		ResultSet rs = dbase.getQuery("SELECT " + column + " FROM " + getTablename() + " ORDER BY " + sort + " DESC LIMIT " + size);
		
		int order = 0;
		while(rs.next()) {
			order++;
			out.put(order, rs.getObject(column));
		}
		
		rs.close();
		return out;
		
	}
	
	public void insertEntry(String[] columns, Object[] values) throws SQLException {
		String qry = "INSERT INTO " + getTablename() + " (" + getColumnValues(columns) + ") VALUES (" + getEntryValues(values) + ")";
		dbase.queryUpdate(qry);
	}
	
	public int getLastInsertID() throws SQLException {
		
		Integer out = null;
		ResultSet rs = dbase.getQuery("SELECT LAST_INSERT_ID()");
        
        while(rs.next()) {
        	out = rs.getInt(1);
        }
        
        rs.close();
		return out;
		
	}
	
	public void updateEntry(String[] columns, Object[] values, String row, String search) throws SQLException {
		String qry = "UPDATE " + getTablename() + " SET " + getUpdateValues(columns, values) + " WHERE " + row + "='" + search + "'";
		dbase.queryUpdate(qry);
	}	
	
	public String getTablename() {
		return tablename;
	}
	
	private String getTableValues(String[] columns, DataTypes[] types) {
		
		String vals = "";
		
		for (int position = 0; position < columns.length; position++) {
			
			vals = vals + columns[position] + " " + types[position];
			
			if (position < columns.length - 1) {
				vals = vals + ", ";
			}

		}
		
		return vals;
		
	}
	
	private String getUpdateValues(String[] columns, Object[] values) {
		
		String vals = "";
		
		for (int position = 0; position < columns.length; position++) {
			
			vals = vals + columns[position] + "='" + values[position] + "'";
			
			if (position < columns.length - 1) {
				vals = vals + ", ";
			}

		}
		
		return vals;
		
	}
	
	private String getEntryValues(Object[] values) {
		
		String out = "";
		
		for(Object v : values) {
			
			if(out.length() == 0) {
				out = (out + "'" + v + "'");
	    	} else {
	    		out = (out + ", " + "'" + v + "'");
	    	}
			
		}
		
		return out;
		
	}
	
	private String getColumnValues(String[] columns) {
		
		String out = "";
		
		for(String s : columns) {
			
			if(out.length() == 0) {
				out = (out + "`" + s + "`");
	    	} else {
	    		out = (out + ", " + "`" + s + "`");
	    	}
			
		}
		
		return out;
		
	}
}