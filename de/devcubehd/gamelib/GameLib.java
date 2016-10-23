package de.devcubehd.gamelib;

import org.bukkit.plugin.java.JavaPlugin;

import de.devcubehd.gamelib.features.FeatureManager;
import net.md_5.bungee.api.ChatColor;

public class GameLib extends JavaPlugin {
	
	private FeatureManager featureManager;

    private String gameName;
    private String gameDescription;
    private String gamePrefix;
	
	public void onEnable() {
		
		featureManager = new FeatureManager();
		
	}
	
	public static GameLib getInstance() {
        return getPlugin(GameLib.class);
    }

	public static FeatureManager getFeatureManager() {
        return getInstance().featureManager;
    }
	
	public static void init(String gameName, String gameDescription) {
		
		getInstance().gameName = gameName;
        getInstance().gameDescription = gameDescription;
        getInstance().gamePrefix = ChatColor.WHITE + "» " + ChatColor.DARK_GREEN + gameName + " " + ChatColor.WHITE + "• " + ChatColor.GRAY;
        
    }
	
	public static String getGameName() {
        return getInstance().gameName;
    }

    public static String getGameDescription() {
        return getInstance().gameDescription;
    }

    public static String getGamePrefix() {
       return getInstance().gamePrefix;
    }
	
}
