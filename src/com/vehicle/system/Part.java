package com.vehicle.system;

public class Part {
    private String partId;
    private String name;
    private int quantity;
    private double price;

    public Part(String partId, String name, int quantity, double price) {
        this.partId = partId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getPartId() { return partId; }
    public String getName() { return name; }      // ✅ Added
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }    // ✅ Added

    // Setters
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Part [ID: " + partId + ", Name: " + name + 
               ", Qty: " + quantity + ", Price: $" + price + "]";
    }
}