package com.example.smartdecorate.Model;

public class WaterValveModel {

    private int id;
    private int activeNow;
    private int intensity;
    private String from;
    private String until;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActiveNow() {
        return activeNow;
    }

    public void setActiveNow(int activeNow) {
        this.activeNow = activeNow;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }
}
