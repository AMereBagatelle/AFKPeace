package net.bagatelle.afkpeace;

public class ReconnectOnTimeout {
    private boolean isActive;

    public ReconnectOnTimeout() {
        isActive = false;
    }

    public void setActive(boolean setTo) {
        isActive = setTo;
    }

    public boolean getActive() {
        return isActive;
    }
}