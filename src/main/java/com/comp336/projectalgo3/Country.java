package com.comp336.projectalgo3;

public class Country {
    private String country;
    private double latitude;
    private double longitude;

    public Country(String country, double latitude, double longitude){
        this.country=country;
        this.latitude=latitude;
        this.longitude=longitude;
    }


    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                "}\n";
    }
}
