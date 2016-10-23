package de.devcubehd.gamelib.features.mapFeature;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import de.devcubehd.gamelib.GameLib;
import de.devcubehd.gamelib.features.sqlFeature.Database;
import de.devcubehd.gamelib.features.sqlFeature.SqlFeature;

public class MapManager {
	
	private List<Location> spawns;
	
	private int mapid;
	
	private String mapname;
	
	private String mapbuilder;
	
	private String mapfolder;
	
	private World map;
	
	public MapManager() {
		
		spawns = new ArrayList<>();
		
		Database db = GameLib.getFeatureManager().getFeature(SqlFeature.class).getManager().getDatabase();
		
		if(!(db.getTable("Maps").exists() && db.getTable("Spawns").exists())) {
			
			try {
				
				db.queryUpdate("CREATE TABLE IF NOT EXISTS Maps (ID INT(2) PRIMARY KEY AUTO_INCREMENT, Name varchar(20), Builder varchar(20), Folder varchar(20))");
				db.queryUpdate("CREATE TABLE IF NOT EXISTS Spawns (ID INT(2) PRIMARY KEY AUTO_INCREMENT, Location varchar(40))");
			
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}	
			
		}
		
		try {
			
			List<Object> list = db.getTable("Maps").getSortedList("ID", "ID");
			
			if(list.size() > 0) {
				
				Collections.shuffle(list);
				
				mapid = (int) list.get(0);
				
				mapname = db.getTable("Maps").getString("Name", "ID", mapid);
				
				mapbuilder = db.getTable("Maps").getString("Builder", "ID", mapid);
				
				mapfolder = db.getTable("Maps").getString("Folder", "ID", mapid);
				
				map = Bukkit.getServer().createWorld(new WorldCreator(mapfolder));
				
				resetWorld(map);
				
				for(Object obj : db.getTable("Spawns").getSortedList("Location", "ID")) {
					
					if(((String) obj).startsWith(mapfolder)) spawns.add(decodeLoc((String) obj));
					
				}
				
			} else {
				
				System.out.println("No maps created. Create some with /setup command.");
				
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public int getMapID() {
		return mapid;
	}
	
	public String getMapName() {
		return mapname;
	}
	
	public String getMapBuilder() {
		return mapbuilder;
	}
	
	public String getMapFolder() {
		return mapfolder;
	}
	
	public World getMap() {
		return map;
	}
	
	public List<Location> getSpawns() {
		return spawns;
	}
	
	public String encodeLoc(Location loc) {
		
		String out = "";
		
		out = loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
		
		return out;
		
	}
	
	public Location decodeLoc(String loc) {
		
		Location out = null;
		
		out = new Location(Bukkit.getWorld(loc.split(";")[0]), Double.valueOf(loc.split(";")[1]), Double.valueOf(loc.split(";")[2]), Double.valueOf(loc.split(";")[3]));
		
		out.setYaw(Float.valueOf(loc.split(";")[4]));
		
		out.setPitch(Float.valueOf(loc.split(";")[5]));
		
		return out;
		
	}
	
	public void resetWorld(World w) {
		
		w.getEntities().forEach(en->en.remove());
		
        w.setPVP(true);
        w.setStorm(false);
        w.setThundering(false);
        w.setWeatherDuration(0);
        w.setMonsterSpawnLimit(0);
        w.setTime(9000);
        w.setDifficulty(Difficulty.NORMAL);
        w.setGameRuleValue("doDaylightCycle", "false");
        w.setGameRuleValue("doFireTick", "false");
        w.setGameRuleValue("doMobSpawning", "false");
        w.setGameRuleValue("keepInventory", "false");
        w.setGameRuleValue("mobGriefing", "false");
        w.setGameRuleValue("naturalRegeneration", "false");
        w.setAutoSave(false);
        
        Bukkit.setDefaultGameMode(GameMode.ADVENTURE);
        Bukkit.setSpawnRadius(0);

        Bukkit.getScheduler().runTaskTimer(GameLib.getInstance(), () -> {
            w.setStorm(false);
            w.setThundering(false);
            w.setTime(9000);
        }, 0, 20);
		
	}
	
	public void disable() {
		
	}

}
