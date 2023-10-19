package com.fidelity.india.secondary.assessment.model;

import java.util.Objects;

public class Marina {

    private String marina;
    private String country;
    private String vesselName;
    private double dailyRate;
    private int billableNights;
    private String currentBalance;

    public Marina() {
    }

    public Marina(String marina, String country, String vesselName, double dailyRate, int billableNights, String currentBalance) {
        this.marina = marina;
        this.country = country;
        this.vesselName = vesselName;
        this.dailyRate = dailyRate;
        this.billableNights = billableNights;
        this.currentBalance = currentBalance;
    }

    public String getMarina() {
        return marina;
    }

    public void setMarina(String marina) {
        this.marina = marina;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getBillableNights() {
        return billableNights;
    }

    public void setBillableNights(int billableNights) {
        this.billableNights = billableNights;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marina marina1)) return false;
        return Double.compare(getDailyRate(), marina1.getDailyRate()) == 0 && getBillableNights() == marina1.getBillableNights() && Objects.equals(getMarina(), marina1.getMarina()) && Objects.equals(getCountry(), marina1.getCountry()) && Objects.equals(getVesselName(), marina1.getVesselName()) && Objects.equals(getCurrentBalance(), marina1.getCurrentBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMarina(), getCountry(), getVesselName(), getDailyRate(), getBillableNights(), getCurrentBalance());
    }

    @Override
    public String toString() {
        return "Marina{" +
                "marina='" + marina + '\'' +
                ", country='" + country + '\'' +
                ", vesselName='" + vesselName + '\'' +
                ", dailyRate=" + dailyRate +
                ", billableNights=" + billableNights +
                ", currentBalance='" + currentBalance + '\'' +
                '}';
    }

}