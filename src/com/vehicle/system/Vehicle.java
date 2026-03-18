package com.vehicle.system;

public class Vehicle {
    // Attributes (Data)
    private String registrationNumber;
    private String ownerName;
    private String model;
    private int year;

    // Constructor (To create a new Vehicle)
    public Vehicle(String registrationNumber, String ownerName, String model, int year) {
        this.registrationNumber = registrationNumber;
        this.ownerName = ownerName;
        this.model = model;
        this.year = year;
    }

    // Getters (To read data)
    public String getRegistrationNumber() { return registrationNumber; }
    public String getOwnerName() { return ownerName; }
    public String getModel() { return model; }
    public int getYear() { return year; }

    // Setters (To update data)
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }

    // To print vehicle details nicely
    @Override
    public String toString() {
        return "Vehicle [Reg: " + registrationNumber + ", Owner: " + ownerName + 
               ", Model: " + model + ", Year: " + year + "]";
    }
}