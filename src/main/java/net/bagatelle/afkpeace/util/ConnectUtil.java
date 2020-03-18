package net.bagatelle.afkpeace.util;

public class ConnectUtil {

    private boolean autoReconnectActive = false;
    
    public void connectToServer(String serverAddress) {
        if(serverAddress != null) {
        } else {
            // TODO PUT CODE HERE TO HANDLE NOT HAVING A SERVER NAME
        }
    }

    public void setAutoReconnectActive(boolean setpoint) {
        autoReconnectActive = setpoint;
    }

    public boolean getAutoReconnectActive() {
        return autoReconnectActive;
    }

}