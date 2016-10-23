package de.devcubehd.gamelib.features.sqlFeature;

import java.sql.SQLException;

import org.bukkit.scheduler.BukkitRunnable;

import de.devcubehd.gamelib.GameLib;

public class SqlManager {
	
    private SqlSettings settings;
    private SqlFeature feature;
    private Database db;

    public SqlManager(SqlSettings settings, SqlFeature sqlFeature) {
    	
        this.settings = settings;
        this.feature = sqlFeature;
        
        db = new Database(settings.getHost(), settings.getPort(), settings.getDatabase(), settings.getUsername(), settings.getPassword(), true);
		
		try {
			
			db.openConnection();
			
		} catch (ClassNotFoundException | SQLException e1) {
			
			e1.printStackTrace();
			
		}
				
		new BukkitRunnable() {
			
			public void run() {
				
				try {
					
					db.getQuery("DESCRIBE " + settings.getTable());
					
				} catch (SQLException e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		}.runTaskTimer(GameLib.getInstance(), 20 * 60 * 5, 20 * 60 * 5);
        
    }

    public SqlSettings getSettings() {
        return settings;
    }

    public void disable() {
    	
    	try {
    		
			db.closeConnection();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
    	
        feature.setDisabled();
        
    }
    
    public Table getTable() {
		return db.getTable(settings.getTable());
	}
    
    public Table getTable(String table) {
		return db.getTable(table);
	}
	
	public Database getDatabase() {
		return db;
	}
    
}