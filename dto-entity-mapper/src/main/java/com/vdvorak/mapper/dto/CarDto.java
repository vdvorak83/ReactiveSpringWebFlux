package com.vdvorak.mapper.dto;

import java.util.Date;


public class CarDto {
    private long id;
    private String make ;
    private int numOfSeats;
    private Date realeseDate;
    private EngineDto engineDto;

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

    public Date getRealeseDate() {
        return realeseDate;
    }

    public void setRealeseDate(Date realeseDate) {
        this.realeseDate = realeseDate;
    }

    public EngineDto getEngineDto() {
        return engineDto;
    }

    public void setEngineDto(EngineDto engineDto) {
        this.engineDto = engineDto;
    }
}
