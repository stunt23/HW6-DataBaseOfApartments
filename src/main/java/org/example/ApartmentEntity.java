package org.example;

public class ApartmentEntity {

    private Integer aprtRegionNumber;
    private String aprtAdress;
    private Double aprtArea;
    private Integer aprtNumerOfRooms;
    private Double aprtCost;

    ApartmentEntity(){};

    public Integer getAprtRegionNumber() {
        return aprtRegionNumber;
    }

    public void setAprtRegionNumber(Integer aprtRegionNumber) {
        this.aprtRegionNumber = aprtRegionNumber;
    }

    public String getAprtAdress() {
        return aprtAdress;
    }

    public void setAprtAdress(String aprtAdress) {
        this.aprtAdress = aprtAdress;
    }

    public Double getAprtArea() {
        return aprtArea;
    }

    public void setAprtArea(Double aprtArea) {
        this.aprtArea = aprtArea;
    }

    public Integer getAprtNumerOfRooms() {
        return aprtNumerOfRooms;
    }

    public void setAprtNumerOfRooms(Integer aprtNumerOfRooms) {
        this.aprtNumerOfRooms = aprtNumerOfRooms;
    }

    public Double getAprtCost() {
        return aprtCost;
    }

    public void setAprtCost(Double aprtCost) {
        this.aprtCost = aprtCost;
    }

    @Override
    public String toString() {
        return "ApartmentEntity{" +
                "aprtRegionNumber=" + aprtRegionNumber +
                ", aprtAdress='" + aprtAdress + '\'' +
                ", aprtArea=" + aprtArea +
                ", aprtNumerOfRooms=" + aprtNumerOfRooms +
                ", aprtCost=" + aprtCost +
                '}';
    }
}
