package de.devcubehd.gamelib.features.sqlFeature;

import de.devcubehd.gamelib.features.Feature;

public class SqlFeature extends Feature {
	
    private SqlSettings settings;
    private SqlManager sqlManager;

    public void setSettings(SqlSettings settings) {
        this.settings = settings;
    }
    
    public SqlManager getManager() {
    	return sqlManager;
    }

    @Override
    public boolean construct() {
        sqlManager = new SqlManager(settings, this);
        return true;
    }

    @Override
    public void destruct() {
    	sqlManager.disable();
    }

    void setDisabled() {
        enabled = false;
    }
    
}
