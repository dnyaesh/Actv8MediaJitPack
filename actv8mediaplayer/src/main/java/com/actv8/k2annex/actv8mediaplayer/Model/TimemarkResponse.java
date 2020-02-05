package com.actv8.k2annex.actv8mediaplayer.Model;

import java.util.ArrayList;

public class TimemarkResponse
{
    String adId;
    ArrayList<Timemark> timemarks;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public ArrayList<Timemark> getTimemarks() {
        return timemarks;
    }

    public void setTimemarks(ArrayList<Timemark> timemarks) {
        this.timemarks = timemarks;
    }
}
