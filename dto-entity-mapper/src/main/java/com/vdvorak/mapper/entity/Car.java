package com.vdvorak.mapper.entity;

import java.util.Date;


public class Car {
    private long id;
    private String make ;
    private int numOfSeats;
    private Date releaseDate;
    private Engine source;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Engine getSource() {
        return source;
    }

    public void setSource(Engine source) {
        this.source = source;
    }
}
