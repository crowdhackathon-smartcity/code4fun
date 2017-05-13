package com.github.bagiasn.code4fun.models.database;

/**
 * Created by kostas.bagias on 14/05/2017.
 */

public class OrganizationMarker {
    private String name;
    private double longtitude;
    private double latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
