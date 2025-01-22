package com.riad.shebahealthcheck;


public class GlucoseRecord {
    private int id;
    private String name;
    private int bloodSugar;
    private String dateTime;

    public GlucoseRecord() {
    }

    public GlucoseRecord(String name, int bloodSugar, String dateTime) {
        this.name = name;
        this.bloodSugar = bloodSugar;
        this.dateTime = dateTime;
    }

    // Getters and Setters
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

    public int getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(int bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
