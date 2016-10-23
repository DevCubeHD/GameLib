package de.devcubehd.gamelib.features.mapFeature;

import de.devcubehd.gamelib.GameLib;
import de.devcubehd.gamelib.features.Feature;
import de.devcubehd.gamelib.features.sqlFeature.SqlFeature;

public class MapFeature extends Feature {
	
	private MapManager mapManager;
	
	public MapManager getManager() {
    	return mapManager;
    }
	
    @Override
    public boolean construct() {
    	if(!GameLib.getFeatureManager().getFeature(SqlFeature.class).isEnabled()) return false;
    	mapManager = new MapManager();
        return true;
    }

    @Override
    public void destruct() {
    	mapManager.disable();
    }

}
