package de.devcubehd.gamelib.features.disallowFeature;

import org.bukkit.Location;
import org.bukkit.event.block.Action;

import java.util.*;

public final class DisallowSettingsBuilder {
	
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

    private DisallowSettingsBuilder() {
        allowedActions = new ArrayList<>();
    }

    public static DisallowSettingsBuilder defaultAllowBuilder() {
    	
        DisallowSettingsBuilder builder = new DisallowSettingsBuilder();
        
        builder.allowedActions = Arrays.asList(Action.values());
        builder.canMove = true;
        builder.canTakeDamage = true;
        builder.canDealDamage = true;
        builder.canUseInventory = true;
        builder.canDropItems = true;
        builder.canPickUpItems = true;
        builder.canHunger = true;
        builder.canUsePortal = true;
        builder.doMobSpawn = true;
        
        return builder;
        
    }

    public static DisallowSettingsBuilder defaultDisallowBuilder() {
        return new DisallowSettingsBuilder();
    }

    public DisallowSettingsBuilder allowMove() {
        canMove = true;
        return this;
    }

    public DisallowSettingsBuilder disallowMove() {
        canMove = false;
        return this;
    }

    public DisallowSettingsBuilder allowAllInteraction() {
        allowedActions = Arrays.asList(Action.values());
        return this;
    }

    public DisallowSettingsBuilder disallowAllInteraction() {
        allowedActions = Collections.emptyList();
        return this;
    }

    public DisallowSettingsBuilder allowInteraction(Action action) {
        Set<Action> actionSet = new HashSet<>(allowedActions);
        actionSet.add(action);
        allowedActions = actionSet;
        return this;
    }

    public DisallowSettingsBuilder disallowInteraction(Action action) {
        Set<Action> actionSet = new HashSet<>(allowedActions);
        actionSet.remove(action);
        allowedActions = actionSet;
        return this;
    }

    public DisallowSettingsBuilder allowDamage() {
        canTakeDamage = true;
        canDealDamage = true;
        return this;
    }

    public DisallowSettingsBuilder disallowDamage() {
        canTakeDamage = false;
        canDealDamage = false;
        return this;
    }

    public DisallowSettingsBuilder allowMobSpawn() {
    	doMobSpawn = true;
        return this;
    }

    public DisallowSettingsBuilder disallowMobSpawn() {
        doMobSpawn = false;
        return this;
    }
    
    public DisallowSettingsBuilder allowDealDamage() {
        canDealDamage = true;
        return this;
    }

    public DisallowSettingsBuilder disallowDealDamage() {
        canDealDamage = false;
        return this;
    }

    public DisallowSettingsBuilder allowTakeDamage() {
        canTakeDamage = true;
        return this;
    }

    public DisallowSettingsBuilder disallowTakeDamage() {
        canTakeDamage = false;
        return this;
    }

    public DisallowSettingsBuilder allowItems() {
        canUseInventory = true;
        canPickUpItems = true;
        canDropItems = true;
        return this;
    }

    public DisallowSettingsBuilder disallowItems() {
        canUseInventory = false;
        canPickUpItems = false;
        canDropItems = false;
        return this;
    }

    public DisallowSettingsBuilder allowUseInventory() {
        canUseInventory = true;
        return this;
    }

    public DisallowSettingsBuilder disallowUseInventory() {
        canUseInventory = false;
        return this;
    }

    public DisallowSettingsBuilder allowItemWorldInteraction() {
        canPickUpItems = true;
        canDropItems = true;
        return this;
    }

    public DisallowSettingsBuilder disallowItemWorldInteraction() {
        canPickUpItems = false;
        canDropItems = false;
        return this;
    }

    public DisallowSettingsBuilder allowPickUpItems() {
        canPickUpItems = true;
        return this;
    }

    public DisallowSettingsBuilder disallowPickUpItems() {
        canPickUpItems = false;
        return this;
    }

    public DisallowSettingsBuilder allowDropItems() {
        canDropItems = true;
        return this;
    }

    public DisallowSettingsBuilder disallowDropItems() {
        canDropItems = false;
        return this;
    }

    public DisallowSettingsBuilder allowHunger() {
        canHunger = true;
        return this;
    }

    public DisallowSettingsBuilder disallowHunger() {
        canHunger = false;
        return this;
    }

    public DisallowSettingsBuilder allowPortalUse() {
        canUsePortal = true;
        return this;
    }

    public DisallowSettingsBuilder disallowPortalUse() {
        canUsePortal = false;
        return this;
    }

    public DisallowSettingsBuilder teleportOnStuck(Location target) {
        teleportStuckLocation = target;
        return this;
    }

    public DisallowSettingsBuilder disableTeleportOnStuck() {
        teleportStuckLocation = null;
        return this;
    }

    public DisallowSettings build() {
        return new DisallowSettings(canMove, allowedActions, canTakeDamage, canDealDamage, teleportStuckLocation,
                canUseInventory, canDropItems, canPickUpItems, canHunger, canUsePortal, doMobSpawn);
    }
}
