package de.devcubehd.gamelib.features.lobbyFeature;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * This class can be used to configure the {@link LobbyFeature} using {@link LobbyFeature#setSettings(LobbySettings)}.
 * It provides various options for fine-tuning your game lobby!
 */
public final class LobbySettings {
	
    private int minPlayers;
    private int maxPlayers;
    private int timeToStart;
    private Location spawn;
    private World world;

    /**
     * Constructs a LobbySettings object which allows you to configure the {@link LobbyFeature}.
     * @param minPlayers The minimum amount of players required for the countdown to start.
     * @param maxPlayers The maximum amount of players in the lobby at one time.
     * @param timeToStart The length of the countdown, which starts when at least {@link #getMinPlayers()} players are
     *                    in the lobby.
     * @param world The lobby world.
     */
    public LobbySettings(int minPlayers, int maxPlayers, int timeToStart, String world, Location spawn) {
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.timeToStart = timeToStart;
        this.world = Bukkit.getWorld(world);
        this.spawn = spawn;
    }

    /**
     * Returns the minimum amount of players required to start the countdown.
     * @return The minimum amount of players required to start the countdown.
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Returns the maximum amount of players that are allowed to join the lobby.
     * @return The maximum amount of players that are allowed to join the lobby.
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Returns the countdown length (in seconds) which starts when the amount of players specified by
     * {@link #getMinPlayers()} is reached.
     * @return The length of the countdown, in seconds.
     */
    public int getTimeToStart() {
        return timeToStart;
    }
    
    public void setTimeToStart(int timeToStart) {
    	this.timeToStart = timeToStart;
    }
    
    /**
     * Returns the Bukkit world of the lobby.
     * @return the Bukkit world of the lobby.
     */
    public World getWorld() {
        return world;
    }
    
    /**
     * Returns the spawn location in the lobby.
     * @return the spawn location in the lobby.
     */
    public Location getLocation() {
        return spawn;
    }
    
}
