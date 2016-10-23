package de.devcubehd.gamelib.features.disallowFeature;

import org.bukkit.Location;
import org.bukkit.event.block.Action;

import java.util.ArrayList;
import java.util.Collection;

public final class DisallowSettings {
    private boolean canMove;
    private Collection<Action> allowedActions;
    private boolean canTakeDamage;
    private boolean canDealDamage;
    private Location teleportStuckLocation;
    private boolean canUseInventory;
    private boolean canDropItems;
    private boolean canPickUpItems;
    private boolean canHunger;
    private boolean canUsePortal;
    private boolean doMobSpawn;

    public DisallowSettings(boolean canMove, Collection<Action> allowedActions, boolean canTakeDamage, boolean canDealDamage, Location teleportStuckLocation, boolean canUseInventory, boolean canDropItems, boolean canPickUpItems, boolean canHunger, boolean canUsePortal, boolean doMobSpawn) {
     
    	this.canMove = canMove;
        this.allowedActions = new ArrayList<>(allowedActions);
        this.canTakeDamage = canTakeDamage;
        this.canDealDamage = canDealDamage;
        this.teleportStuckLocation = teleportStuckLocation;
        this.canUseInventory = canUseInventory;
        this.canDropItems = canDropItems;
        this.canPickUpItems = canPickUpItems;
        this.canHunger = canHunger;
        this.canUsePortal = canUsePortal;
        this.doMobSpawn = doMobSpawn;
        
    }

    public boolean canMove() {
        return canMove;
    }

    public Collection<Action> getAllowedActions() {
        return allowedActions;
    }

    public boolean canTakeDamage() {
        return canTakeDamage;
    }

    public boolean canDealDamage() {
        return canDealDamage;
    }

    public Location getTeleportStuckLocation() {
        return teleportStuckLocation;
    }

    public boolean canUseInventory() {
        return canUseInventory;
    }

    public boolean canDropItems() {
        return canDropItems;
    }

    public boolean canPickUpItems() {
        return canPickUpItems;
    }

    public boolean canHunger() {
        return canHunger;
    }

    public boolean canUsePortal() {
        return canUsePortal;
    }

    public boolean doMobSpawn() {
    	return doMobSpawn;
    }
    
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void setAllowedActions(Collection<Action> allowedActions) {
        this.allowedActions = new ArrayList<>(allowedActions);
    }

    public void setCanTakeDamage(boolean canTakeDamage) {
        this.canTakeDamage = canTakeDamage;
    }

    public void setCanDealDamage(boolean canDealDamage) {
        this.canDealDamage = canDealDamage;
    }

    public void setTeleportStuckLocation(Location teleportStuckLocation) {
        this.teleportStuckLocation = teleportStuckLocation;
    }

    public void setCanUseInventory(boolean canUseInventory) {
        this.canUseInventory = canUseInventory;
    }

    public void setCanDropItems(boolean canDropItems) {
        this.canDropItems = canDropItems;
    }

    public void setCanPickUpItems(boolean canPickUpItems) {
        this.canPickUpItems = canPickUpItems;
    }

    public void setCanHunger(boolean canHunger) {
        this.canHunger = canHunger;
    }

    public void setCanUsePortal(boolean canUsePortal) {
        this.canUsePortal = canUsePortal;
    }
    
    public void setDoMobSpawn(boolean doMobSpawn) {
    	this.doMobSpawn = doMobSpawn;
    }
    
}
