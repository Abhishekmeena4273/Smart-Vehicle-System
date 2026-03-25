package com.vehicle.system;

public class ActivityLog {
    private String timestamp;
    private String vehicleReg;
    private String activity; // e.g., "Trip Started", "Fuel Added"
    // Add these getters
    public String getTimestamp() { return timestamp; }
    public String getVehicleReg() { return vehicleReg; }
    public String getActivity() { return activity; }
    public ActivityLog(String timestamp, String vehicleReg, String activity) {
        this.timestamp = timestamp;
        this.vehicleReg = vehicleReg;
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] Vehicle " + vehicleReg + ": " + activity;
    }
}