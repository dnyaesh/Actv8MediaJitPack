package com.actv8.k2annex.actv8mediaplayer.Model;

/**
 * Created by neoforce-01 on 1/29/2019.
 */

public class Server
{
    int id;
    String name;
    String baseUrl;
    String apiKey;
    String userDataUrl;
    boolean isSelected;
    String audioTriggerUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUserDataUrl() {
        return userDataUrl;
    }

    public void setUserDataUrl(String userDataUrl) {
        this.userDataUrl = userDataUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAudioTriggerUrl() {
        return audioTriggerUrl;
    }

    public void setAudioTriggerUrl(String audioTriggerUrl) {
        this.audioTriggerUrl = audioTriggerUrl;
    }
}
