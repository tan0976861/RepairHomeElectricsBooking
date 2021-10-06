package com.example.repairhomeelectricbooking.dto;

public class LocationApp {
    public String  latitude,longtitude;

    public LocationApp() {
    }

    public LocationApp(String latitude, String longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public String toString() {
        return "LocationApp{" +
                "latitude='" + latitude + '\'' +
                ", longtitude='" + longtitude + '\'' +
                '}';
    }
}
