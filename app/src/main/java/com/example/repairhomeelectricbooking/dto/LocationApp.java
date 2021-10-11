package com.example.repairhomeelectricbooking.dto;

public class LocationApp {
    public double  latitude,longtitude;

    public LocationApp() {
    }

    public LocationApp(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
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
