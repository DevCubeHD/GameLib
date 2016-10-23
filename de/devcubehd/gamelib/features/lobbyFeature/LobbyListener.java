package de.devcubehd.gamelib.features.lobbyFeature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import de.devcubehd.gamelib.GameLib;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PlayerConnection;

public class LobbyListener implements Listener {
	
    private final LobbySettings settings;
    private static Location spawn;
    private CountdownScheduler countdown;

    public static Location getSpawn() {
    	return spawn;
    }
    
    public LobbyListener(LobbySettings settings,CountdownScheduler countdown) {
    	
    	this.countdown = countdown;
        this.settings = settings;
        spawn = settings.getLocation();
        
        while (spawn.getBlock().getType() != Material.AIR) {
            spawn = spawn.add(0, 1, 0);
        }
        
    }

    public void enable() {
    	
        Bukkit.getPluginManager().registerEvents(this, GameLib.getInstance());
       
        Bukkit.getOnlinePlayers().forEach(this::resetPlayer);
        
        Bukkit.getWorld("lobby").getEntities().forEach(entity->{
        	if(!entity.getType().equals(EntityType.PLAYER)) entity.remove();
        });
        
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
    	
    	Player player = e.getPlayer();
    	    	
    	e.setJoinMessage("");
    	
    	if (Bukkit.getOnlinePlayers().size() > settings.getMaxPlayers()) {
    		 
    		if (!player.hasPermission(GameLib.getFeatureManager() + ".fulljoin")) {
            	 
    			player.kickPlayer("§7Dieser Server ist leider schon voll!");
    			return;
                 
    		} else {
            	 
    			List<Player> possible = new ArrayList<Player>();
          		
    			for(Player t : Bukkit.getOnlinePlayers()) {
          			
    				if(!t.getName().equals(player.getName())) {
          				
    					if(!t.hasPermission(GameLib.getFeatureManager() + ".fulljoin")) {
    						possible.add(t);
    					}
            			 
    				}
          			
    			}
    			
    			if(possible.size() > 0) {
            		 
    				Collections.shuffle(possible);
    				possible.get(0).kickPlayer("§7Du wurdest gekickt um Platz für ein Team-Mitglied zu machen.");
    				return;
            	 
    			} else {
            		 
    				player.kickPlayer("§7Dieser Server ist leider schon voll!");
    				return;
                     
    			}
            		 
    		}
             
    	}
         
    	if(player.hasPermission(GameLib.getFeatureManager() + ".joinstart")) {
    		countdown.time = 60;
    		settings.setTimeToStart(60);
    	}
         
    	resetPlayer(player);
         
    	player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100);

    	sendTitle(player, 0, 60, 40, "§2" + GameLib.getGameName(), "§7" + GameLib.getGameDescription());

    	Bukkit.broadcastMessage("§8» §2" + player.getName() + " §7hat das Spiel betreten §8(§7" + Bukkit.getOnlinePlayers().size() + "§8/§7" + settings.getMaxPlayers() + "§8)");
                         
    	for (Player all : Bukkit.getOnlinePlayers()) player.showPlayer(all);
         
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
    	
    	e.setQuitMessage("");
    	
    	if(Bukkit.getOnlinePlayers().size()-1 < settings.getMaxPlayers()) 
    		Bukkit.broadcastMessage("§8» §2" + e.getPlayer().getName() + " §7hat das Spiel verlassen §8(§7" + (Bukkit.getOnlinePlayers().size()-1) + "§8/§7" + settings.getMaxPlayers() + "§8)");
    	
    }

    public void resetPlayer(Player player) {
    	
        ((CraftPlayer) player).getHandle().reset();

        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(false);
        player.setFlying(false);

        player.setExp(0);
        player.setLevel(0);
        player.closeInventory();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getActivePotionEffects().clear();
        player.setWalkSpeed(0.2f);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().setHeldItemSlot(0);

        player.setCompassTarget(spawn);

        player.teleport(spawn);
  
        setItems(player);
        
    }
    
    public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
	
    	PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
	    
	    PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
	    connection.sendPacket(packetPlayOutTimes);
	    
	    if (subtitle != null) {
	    	
	      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
	      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
	      connection.sendPacket(packetPlayOutSubTitle);
	      
	    } 
	    
	    if (title != null) {
	    	
	      IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
	      PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
	      connection.sendPacket(packetPlayOutTitle);
	      
	    }
	    
	}

    public void setItems(Player p) {
    	
        PlayerInventory inv = p.getInventory();

        ItemStack leaveItem = new ItemStack(Material.RECORD_4);
        ItemMeta meta = leaveItem.getItemMeta();
        meta.setLore(Collections.emptyList());
        meta.setDisplayName("§7» §cVerlassen §7«");
        leaveItem.setItemMeta(meta);
        inv.setItem(8, leaveItem);
        
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	
            if(e.getItem() == null) return; 
            
            if (e.getItem().getType() == Material.NETHER_STAR) {
                e.getPlayer().kickPlayer("lobby");
                return;
            }
            
        }

        e.setCancelled(true);
        
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
    	
        if (!e.getEntityType().equals(EntityType.PLAYER)) {
            return;
        }
        
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID) || e.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION) || e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) {
            e.getEntity().teleport(spawn);
        }
                
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
    	
        e.setDeathMessage("");
        e.setKeepLevel(false);
        e.setNewExp(0);
        e.setNewLevel(0);
        e.setNewTotalExp(0);
        e.setDroppedExp(0);

        Player p = e.getEntity();
        p.setFlying(false);
        p.setAllowFlight(false);
        
    }

    @EventHandler
    public void onAttach(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo().getBlock().getType() == Material.PORTAL) e.getPlayer().kickPlayer("lobby");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(spawn);
        setItems(e.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        e.getWhoClicked().closeInventory();
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        e.getItemDrop().remove();
        setItems(e.getPlayer());
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        e.setCancelled(true);
    }

    public void disable() {
        HandlerList.unregisterAll(this);
    }
    
}
