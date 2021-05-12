package edu.chalmers.axen2021.model.projectdata;

import java.io.Serializable;

public class ApartmentItem implements Serializable {

    private String apartmentType;
    private double BOA;
    private int amount;

    private double rentPerMonthLow;
    private double krPerKvmLow;
    private double rentPerMonthHigh;
    private double krPerKvmHigh;
    private double fullBOA;
    private double fullBOAPercent;
    private double bidrag;

    // Not instantiable outside 'projectdata' package. Add a ApartmentItem through Project class instead.
    ApartmentItem() {}

    public String getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    public double getBOA() {
        return BOA;
    }

    public void setBOA(double BOA) {
        this.BOA = BOA;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getRentPerMonthLow() {
        return rentPerMonthLow;
    }

    public void setRentPerMonthLow(double rentPerMonthLow) {
        this.rentPerMonthLow = rentPerMonthLow;
    }

    public double getKrPerKvmLow() {
        return krPerKvmLow;
    }

    public void setKrPerKvmLow(double krPerKvmLow) {
        this.krPerKvmLow = krPerKvmLow;
    }

    public double getRentPerMonthHigh() {
        return rentPerMonthHigh;
    }

    public void setRentPerMonthHigh(double rentPerMonthHigh) {
        this.rentPerMonthHigh = rentPerMonthHigh;
    }

    public double getKrPerKvmHigh() {
        return krPerKvmHigh;
    }

    public void setKrPerKvmHigh(double krPerKvmHigh) {
        this.krPerKvmHigh = krPerKvmHigh;
    }

    public double getFullBOA() {
        return fullBOA;
    }

    public void setFullBOA(double fullBOA) {
        this.fullBOA = fullBOA;
    }

    public double getFullBOAPercent() {
        return fullBOAPercent;
    }

    public void setFullBOAPercent(double fullBOAPercent) {
        this.fullBOAPercent = fullBOAPercent;
    }

    public double getBidrag() {
        return bidrag;
    }

    public void setBidrag(double bidrag) {
        this.bidrag = bidrag;
    }
}
