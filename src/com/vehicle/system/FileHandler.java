package com.vehicle.system;

import java.io.*;
import java.util.List;

public class FileHandler {
    private static final String VEHICLE_FILE = "vehicles.csv";
    private static final String SERVICE_FILE = "services.csv";
    private static final String LOG_FILE = "logs.csv";
    private static final String INVENTORY_FILE = "inventory.csv";
    private static final String ROUTE_FILE = "routes.csv";

    public static void saveAllData(VehicleManager vm, ServiceSchedule sm, LogManager lm, InventoryManager im, RouteManager rm) {
        saveVehicles(vm);
        saveServices(sm);
        saveLogs(lm);
        saveInventory(im);
        saveRoutes(rm);
        System.out.println("💾 All data saved successfully!");
    }

    private static void saveVehicles(VehicleManager vm) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(VEHICLE_FILE))) {
            for (Vehicle v : vm.getAllVehicles()) {
                writer.println(v.getRegistrationNumber() + "," + v.getOwnerName() + "," + v.getModel() + "," + v.getYear());
            }
        } catch (IOException e) { System.out.println("❌ Error saving vehicles."); }
    }

    private static void saveServices(ServiceSchedule sm) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SERVICE_FILE))) {
            for (Service s : sm.getAllServices()) {
                // Using Getters now
                writer.println(s.getServiceId() + "," + s.getVehicleReg() + "," + s.getServiceType() + "," + s.getDate());
            }
        } catch (IOException e) { System.out.println("❌ Error saving services."); }
    }

    private static void saveLogs(LogManager lm) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE))) {
            List<ActivityLog> logs = lm.getAllLogs();
            //System.out.println("📝 Saving " + logs.size() + " logs...");
            for (ActivityLog l : logs) {
                writer.println(l.getTimestamp() + "," + l.getVehicleReg() + "," + l.getActivity());
            }
            writer.flush(); // Ensure all data is written
            //System.out.println("✅ Logs saved successfully.");
        } catch (IOException e) { 
            System.out.println("❌ Error saving logs: " + e.getMessage()); 
        }
    }

    private static void saveInventory(InventoryManager im) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(INVENTORY_FILE))) {
            for (Part p : im.getAllParts()) {
                // Using Getters now
                writer.println(p.getPartId() + "," + p.getName() + "," + p.getQuantity() + "," + p.getPrice());
            }
        } catch (IOException e) { System.out.println("❌ Error saving inventory."); }
    }

      private static void saveRoutes(RouteManager rm) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROUTE_FILE))) {
            // First save all locations
            writer.println("#LOCATIONS");
            for (Location loc : rm.getAllLocations()) {
                writer.println("LOC," + loc.getId() + "," + loc.getName());
            }
            // Then save all roads
            writer.println("#ROADS");
            for (Location loc : rm.getAllLocations()) {
                for (Edge edge : rm.getEdges(loc)) {
                    writer.println("ROAD," + loc.getId() + "," + edge.destination.getId() + "," + edge.weight);
                }
            }
           // System.out.println("💾 Routes saved successfully!");
        } catch (IOException e) { 
            System.out.println("❌ Error saving routes."); 
        }
    }


    public static void loadAllData(VehicleManager vm, ServiceSchedule sm, LogManager lm, InventoryManager im, RouteManager rm) {
        loadVehicles(vm);
        loadServices(sm);
        loadLogs(lm);
        loadInventory(im);
        loadRoutes(rm);
        System.out.println("📂 All data loaded successfully!");
    }

    private static void loadVehicles(VehicleManager vm) {
        File file = new File(VEHICLE_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    vm.addVehicle(new Vehicle(data[0], data[1], data[2], Integer.parseInt(data[3])));
                }
            }
        } catch (IOException e) { System.out.println("❌ Error loading vehicles."); }
    }

    private static void loadServices(ServiceSchedule sm) {
        File file = new File(SERVICE_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    sm.addService(new Service(data[0], data[1], data[2], data[3]));
                }
            }
        } catch (IOException e) { System.out.println("❌ Error loading services."); }
    }

     private static void loadLogs(LogManager lm) {
        File file = new File(LOG_FILE);
        if (!file.exists()) {
            System.out.println("📄 No previous log file found. Starting fresh.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", 3); // Split into max 3 parts
                if (data.length >= 3) {
                    lm.addLog(new ActivityLog(data[0], data[1], data[2]));
                    count++;
                }
            }
            System.out.println("📂 Loaded " + count + " logs from previous session.");
        } catch (IOException e) { 
            System.out.println("❌ Error loading logs: " + e.getMessage()); 
        }
    }

    private static void loadInventory(InventoryManager im) {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    im.addPart(new Part(data[0], data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3])));
                }
            }
        } catch (IOException e) { System.out.println("❌ Error loading inventory."); }
    }

    private static void loadRoutes(RouteManager rm) {
        File file = new File(ROUTE_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String mode = "";
            while ((line = reader.readLine()) != null) {
                if (line.equals("#LOCATIONS")) {
                    mode = "loc";
                    continue;
                } else if (line.equals("#ROADS")) {
                    mode = "road";
                    continue;
                }
                String[] data = line.split(",");
                if (mode.equals("loc") && data.length >= 3 && data[0].equals("LOC")) {
                    rm.registerLocation(data[1], data[2]);
                } else if (mode.equals("road") && data.length >= 4 && data[0].equals("ROAD")) {
                    rm.addRoadById(data[1], data[2], Double.parseDouble(data[3]));
                }
            }
            System.out.println("📂 Routes loaded successfully!");
        } catch (IOException e) { 
            System.out.println("❌ Error loading routes."); 
        }
    }
}