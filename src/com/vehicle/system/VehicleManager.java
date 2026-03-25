package com.vehicle.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleManager {
    // This HashMap ACTS as our Hash Table
    // Key = Registration Number (String), Value = Vehicle Object
    private Map<String, Vehicle> vehicleDatabase;
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicleDatabase.values());
    }
    public VehicleManager() {
        this.vehicleDatabase = new HashMap<>();
    }

    // 1. INSERT (Add Vehicle)
    public void addVehicle(Vehicle vehicle) {
        vehicleDatabase.put(vehicle.getRegistrationNumber(), vehicle);
        System.out.println("✅ Vehicle added successfully!");
    }

    // 2. SEARCH (Find Vehicle)
    public Vehicle searchVehicle(String regNumber) {
        return vehicleDatabase.get(regNumber);
    }

    // 3. UPDATE (Update Details)
    public void updateVehicle(String regNumber, String newOwner, String newModel, int newYear) {
        Vehicle vehicle = vehicleDatabase.get(regNumber);
        if (vehicle != null) {
            vehicle.setOwnerName(newOwner);
            vehicle.setModel(newModel);
            vehicle.setYear(newYear);
            System.out.println("✅ Vehicle updated successfully!");
        } else {
            System.out.println("❌ Vehicle not found!");
        }
    }

    // 4. DELETE (Remove Vehicle)
    public void deleteVehicle(String regNumber) {
        if (vehicleDatabase.remove(regNumber) != null) {
            System.out.println("✅ Vehicle deleted successfully!");
        } else {
            System.out.println("❌ Vehicle not found!");
        }
    }

    // Helper: Show all vehicles (for testing)
    public void displayAllVehicles() {
        if (vehicleDatabase.isEmpty()) {
            System.out.println("📭 No vehicles in the system.");
            return;
        }
        for (Vehicle v : vehicleDatabase.values()) {
            System.out.println(v);
        }
    }
}