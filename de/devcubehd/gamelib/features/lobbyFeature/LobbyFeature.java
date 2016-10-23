package de.devcubehd.gamelib.features.lobbyFeature;

import de.devcubehd.gamelib.features.Feature;

public class LobbyFeature extends Feature {
	
    private LobbySettings settings;
    private LobbyManager lobbyManager;

    public void setSettings(LobbySettings settings) {
        this.settings = settings;
    }
    
    public LobbyManager getManager() {
    	return lobbyManager;
    }

    @Override
    public boolean construct() {
        lobbyManager = new LobbyManager(settings, this);
        return true;
    }

    @Override
    public void destruct() {
        lobbyManager.disable();
    }

    void setDisabled() {
        enabled = false;
    }
    
}
