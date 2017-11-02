package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/26/17.
 */


/**
 * Created by Rafi on 06-Oct-17.
 */

public class routTime {
    String startOrBus;
    String endOrUpdown;
    boolean isRoutTime;

    public routTime(String startOrBus, String endOrUpdown, boolean isRoutTime) {
        this.startOrBus = startOrBus;
        this.endOrUpdown = endOrUpdown;
        this.isRoutTime = isRoutTime;
    }

    public String getStartOrBus() {
        return startOrBus;
    }

    public void setStartOrBus(String startOrBus) {
        this.startOrBus = startOrBus;
    }

    public String getEndOrUpdown() {
        return endOrUpdown;
    }

    public void setEndOrUpdown(String endOrUpdown) {
        this.endOrUpdown = endOrUpdown;
    }

    public boolean isRoutTime() {
        return isRoutTime;
    }

    public void setRoutTime(boolean routTime) {
        isRoutTime = routTime;
    }
}