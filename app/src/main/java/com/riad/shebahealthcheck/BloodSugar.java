package com.riad.shebahealthcheck;


public class BloodSugar {
    private int id;
    private String date;
    private float bloodSugarLevel;

    public BloodSugar(int id, String date, float bloodSugarLevel) {
        this.id = id;
        this.date = date;
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public float getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBloodSugarLevel(float bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }
}
