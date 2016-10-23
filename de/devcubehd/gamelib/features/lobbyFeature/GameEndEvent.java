package de.devcubehd.gamelib.features.lobbyFeature;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class GameEndEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}