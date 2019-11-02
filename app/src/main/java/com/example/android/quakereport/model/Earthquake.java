package com.example.android.quakereport.model;

import java.net.URI;
import java.net.URL;
import java.util.Date;

public class Earthquake {
    private double magnitude;
    private String location;
    private Long mTimeInMilliseconds;
    private String url;

    public Earthquake(double magnitude, String location, Long mTimeInMilliseconds, String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.mTimeInMilliseconds = mTimeInMilliseconds;
        this.url = url;
    }

    public double getMgnitude() {
        return magnitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getMTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public void setMTimeInMilliseconds(Long time) {
        this.mTimeInMilliseconds = time;
    }
}
