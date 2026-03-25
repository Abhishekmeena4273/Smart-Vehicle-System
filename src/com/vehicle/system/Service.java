package com.vehicle.system;

public class Service {
    private String serviceId;
    private String vehicleReg; // Links to Vehicle
    private String serviceType; // e.g., "Oil Change", "Tire Rotation"
    private String date;
    // Add these getters
    public String getServiceId() { return serviceId; }
    public String getDate() { return date; }
    public Service(String serviceId, String vehicleReg, String serviceType, String date) {
        this.serviceId = serviceId;
        this.vehicleReg = vehicleReg;
        this.serviceType = serviceType;
        this.date = date;
    }

    public String getVehicleReg() { return vehicleReg; }
    public String getServiceType() { return serviceType; }
    
    @Override
    public String toString() {
        return "Service [ID: " + serviceId + ", Vehicle: " + vehicleReg + 
               ", Type: " + serviceType + ", Date: " + date + "]";
    }
}