package de.devcubehd.gamelib.features.lobbyFeature;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.devcubehd.gamelib.GameLib;
import de.devcubehd.gamelib.features.mapFeature.MapFeature;

public class LobbyManager implements CommandExecutor {
	
    private LobbySettings settings;
    private LobbyListener listener;
    private LobbyFeature feature;
    private CountdownScheduler countdown;

    public LobbyManager(LobbySettings settings, LobbyFeature lobbyFeature) {
    	
        this.settings = settings;
        this.feature = lobbyFeature;
        
    	GameLib.getInstance().getCommand("start").setExecutor(this);
    	GameLib.getInstance().getCommand("extendtime").setExecutor(this);
    	GameLib.getInstance().getCommand("forcetimer").setExecutor(this);

    	GameLib.getFeatureManager().getFeature(MapFeature.class).getManager().resetWorld(settings.getWorld());
    	
    	countdown =  new CountdownScheduler(this);

        listener = new LobbyListener(settings,countdown);
        listener.enable();

        countdown = new CountdownScheduler(this);
        
    }

    public LobbySettings getSettings() {
        return settings;
    }
    
    public LobbyListener getListener() {
        return listener;
    }

    public void disable() {
    	
    	GameLib.getInstance().getCommand("start").setExecutor(null);
    	GameLib.getInstance().getCommand("extendtime").setExecutor(null);
    	GameLib.getInstance().getCommand("forcetimer").setExecutor(null);

        countdown.stop();
        listener.disable();
        feature.setDisabled();
        
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias,String[] args) {
		
		if(cmd.getName().equals("start")) {
			
			if(!sender.hasPermission(GameLib.getGameName() + ".forcestart")) {
				sender.sendMessage(GameLib.getGamePrefix() + "Du hast nicht genügend Rechte um das zu tun.");
				return false;
			}
			
			countdown.startGame();
			sender.sendMessage(GameLib.getGamePrefix() + "Du hast das Spiel gestartet!");
			
			return true;
			
		}
		
		if(cmd.getName().equals("extendtime")) {
			
			if(!sender.hasPermission(GameLib.getGameName() + ".extendtime")) {
				sender.sendMessage(GameLib.getGamePrefix() + "Du hast nicht genügend Rechte um das zu tun.");
				return false;
			}
			
			int time = 60;
			
			if(args.length == 1) {
				
				try {
					
					time = Integer.valueOf(args[0]);
					
				} catch(NumberFormatException e) {
					
					sender.sendMessage(GameLib.getGamePrefix() + "Es wurde eine invalide Zahl eingegeben!");
					return false;
					
				}
				
			}
			
        	countdown.time = time;
        	settings.setTimeToStart(time);
			sender.sendMessage(GameLib.getGamePrefix() + "Der Countdown wurde auf " + time + " Sekunden verlängert!");
			
			return true;
			
		}
		
		if(cmd.getName().equals("forcetimer")) {
			
			if(!sender.hasPermission(GameLib.getGameName() + ".forcetimer")) {
				sender.sendMessage(GameLib.getGamePrefix() + "Du hast nicht genügend Rechte um das zu tun.");
				return false;
			}
			
			countdown.forceStart();
			sender.sendMessage(GameLib.getGamePrefix() + "Du hast den Countdown gestartet!");
			return true;
			
		}
		
		return false;
		
	}
	
}
