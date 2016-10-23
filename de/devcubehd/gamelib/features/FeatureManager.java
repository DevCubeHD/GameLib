package de.devcubehd.gamelib.features;

import org.bukkit.event.Listener;
import java.util.HashSet;
import java.util.Set;

public class FeatureManager implements Listener {
	
    private Set<Feature> features;

    public FeatureManager() {
    	
        features = new HashSet<>();

    }

    @SuppressWarnings("unchecked")
    public <T extends Feature> T getFeature(Class<T> clazz) {
    	
        for (Feature feature : features) {
        	
            if (clazz.isAssignableFrom(feature.getClass())) {
            	
                return (T) feature;
                
            }
            
        }

        try {
        	
            T feature = clazz.newInstance();
            features.add(feature);
            return feature;
            
        } catch (InstantiationException | IllegalAccessException e) {
        	
            e.printStackTrace();
            
        }

        throw new IllegalArgumentException("Invalid constructor");
        
    }

}
