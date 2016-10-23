package de.devcubehd.gamelib.features;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import de.devcubehd.gamelib.GameLib;

public abstract class Feature implements Listener {
   
	protected boolean enabled;

    public boolean enable() {
      
    	if (enabled) {
            return false;
        }

        if (!construct()) {
            return false;
        }

        Bukkit.getPluginManager().registerEvents(this, GameLib.getInstance());
        enabled = true;
        return true;
        
    }

    public boolean disable() {
      
    	if (!enabled) {
            return false;
        }

        HandlerList.unregisterAll(this);
        destruct();
        enabled = false;
        return true;
        
    }

    public abstract boolean construct();

    public abstract void destruct();

    public boolean isEnabled() {
        return enabled;
    }
    
}
