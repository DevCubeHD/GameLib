package de.devcubehd.gamelib.features.lobbyFeature;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.devcubehd.gamelib.GameLib;

import java.util.Arrays;
import java.util.List;

public class CountdownScheduler implements Runnable {
	
    private LobbyManager manager;

    private BukkitTask task;
    public int time;
    private boolean forceStart = false;

    private boolean disabled = false;

    public CountdownScheduler(LobbyManager manager) {
    	
        this.manager = manager;

        time = manager.getSettings().getTimeToStart();

        task = Bukkit.getScheduler().runTaskTimer(GameLib.getInstance(), this, 0, 20);
        
    }

    boolean paused = true;
    
    @Override
    public void run() {
    	
        if (Bukkit.getOnlinePlayers().size() < manager.getSettings().getMinPlayers() && !forceStart) {
        	
            time = manager.getSettings().getTimeToStart();

            if (!paused) {

                Bukkit.broadcastMessage(GameLib.getGamePrefix() + "Es sind nicht genügend Spieler verfügbar! Es werden insgesamt §8" + manager.getSettings().getMinPlayers() + " Spieler §7benötigt.");

                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 1, 2);
                }
                
                paused = true;

                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.setExp(0);
                    p.setLevel(0);
                }
                
            }
            
        } else {
        	
            time--;
            paused = false;

            float percentage = time / (float) manager.getSettings().getTimeToStart();
            
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setExp(percentage);
                p.setLevel(time);
            }

            List<Integer> steps = Arrays.asList(60, 30, 15, 10, 5, 4, 3, 2, 1);
            
            if (steps.contains(time)) {
            	
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 2);
                }

                Bukkit.broadcastMessage(GameLib.getGamePrefix() + "Das Spiel startet in §8" + time + "§7 Sekunden...");
            }
            
        }

        if (time <= 0) {
            startGame();
        }
        
    }

    public void startGame() {
    	
        for (Player p : Bukkit.getOnlinePlayers()) 
        	p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 0, 0);
        
        stop();

        for (Player p : Bukkit.getOnlinePlayers()) {
        	
            ((CraftPlayer) p).getHandle().reset();
            p.setGameMode(GameMode.ADVENTURE);
            p.setExp(0);
            p.setLevel(0);
            p.closeInventory();
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.getActivePotionEffects().clear();
            p.setMaxHealth(20);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setWalkSpeed(0.2f);
            p.setVelocity(new Vector());
            p.setAllowFlight(false);
            p.setFlying(false);
            
        }

        Bukkit.getPluginManager().callEvent(new GameStartEvent());
        
    }

    public void stop() {
    	
        if (!disabled) {
        	
            disabled = true;

            task.cancel();
            manager.disable();
            
        }
        
    }
    
    public void forceStart() {
    	forceStart = true;
    }
    
}
