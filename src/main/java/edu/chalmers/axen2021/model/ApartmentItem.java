package edu.chalmers.axen2021.model;

import java.io.Serializable;

public class ApartmentItem implements Serializable {

    private String apartmentType;
    private double BOA;
    private int amount;

    private double hyraPerMånadLåg;
    private double krPerKvmLåg;
    private double hyraPerMånadHög;
    private double krPerKvmHög;

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
}
