package de.devcubehd.gamelib.features.disallowFeature;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import de.devcubehd.gamelib.GameLib;

public class DisallowListener implements Listener {
	
    private DisallowSettings settings;

    public DisallowListener(DisallowSettings settings) {
    	
        this.settings = settings;
        Bukkit.getPluginManager().registerEvents(this, GameLib.getInstance());
        
    }
    
    public void disable() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (!settings.getAllowedActions().contains(e.getAction())) {
            e.setCancelled(true);
        }
        
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamage(EntityDamageEvent e) {

        if (!e.getEntityType().equals(EntityType.PLAYER)) {
            return;
        }

        if (!settings.canTakeDamage()) {
            e.setCancelled(true);
        }
        
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerStuck(EntityDamageEvent e) {
    	
        if (!e.getEntityType().equals(EntityType.PLAYER)) {
            return;
        }

        if (settings.getTeleportStuckLocation() != null && (
                e.getCause().equals(EntityDamageEvent.DamageCause.VOID) ||
                e.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION) ||
                e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING))) {
            e.getEntity().teleport(settings.getTeleportStuckLocation());
        }
        
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
    	
        if (!e.getDamager().getType().equals(EntityType.PLAYER)) {
            return;
        }

        if (!settings.canDealDamage()) {
            e.setCancelled(true);
        }
        
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent e) {
        if (!settings.canMove()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent e) {
        if (!settings.canUseInventory()) {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDrop(PlayerDropItemEvent e) {
        if (!settings.canDropItems()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickUp(PlayerPickupItemEvent e) {
        if (!settings.canPickUpItems()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFoodLevel(FoodLevelChangeEvent e) {
        if (!settings.canHunger()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPortal(PlayerPortalEvent e) {
        if (!settings.canUsePortal()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onMobSpawn(EntitySpawnEvent e) {
        if (!e.getEntityType().equals(EntityType.PLAYER) && settings.doMobSpawn()) {
            e.setCancelled(true);
        }
    }

    public void setSettings(DisallowSettings settings) {
        this.settings = settings;
    }
}
