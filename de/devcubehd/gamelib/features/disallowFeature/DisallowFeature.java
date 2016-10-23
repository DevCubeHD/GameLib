package de.devcubehd.gamelib.features.disallowFeature;

import de.devcubehd.gamelib.features.Feature;

public class DisallowFeature extends Feature {
	
    private DisallowSettings settings;
    private DisallowListener listener;

    public DisallowSettings getSettings() {
    	return settings;
    }
    
    public void setSettings(DisallowSettings settings) {
    	
        this.settings = settings;

        if (listener != null) {
            listener.setSettings(settings);
        }
        
    }

    @Override
    public boolean construct() {

        if (settings == null) {
            return false;
        }

        listener = new DisallowListener(settings);

        return true;
    }

    @Override
    public void destruct() {

        listener.disable();
        listener = null;
        
    }
    
}
