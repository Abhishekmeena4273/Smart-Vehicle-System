package com.vehicle.system;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize All Managers
        VehicleManager vehicleMgr = new VehicleManager();
        ServiceSchedule serviceMgr = new ServiceSchedule();
        LogManager logMgr = new LogManager();
        InventoryManager inventoryMgr = new InventoryManager();
        RouteManager routeMgr = new RouteManager();

        // LOAD ALL DATA AT STARTUP
        FileHandler.loadAllData(vehicleMgr, serviceMgr, logMgr, inventoryMgr, routeMgr);

        System.out.println("🚗 Welcome to Smart Vehicle Management System (Final Build)");

        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Vehicle Management (Hash Table)");
            System.out.println("2. Service Scheduling (Linked List)");
            System.out.println("3. Activity Logs (Queue)");
            System.out.println("4. Spare Parts Inventory (BST)");
            System.out.println("5. Route & Fleet (Graph)");
            System.out.println("6. Save & Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 6) {
                FileHandler.saveAllData(vehicleMgr, serviceMgr, logMgr, inventoryMgr, routeMgr);
                break;
            }

            switch (choice) {
                // Pass logMgr to all modules
                case 1: vehicleModule(scanner, vehicleMgr, logMgr); break;
                case 2: serviceModule(scanner, serviceMgr, logMgr); break;
                case 3: logModule(scanner, logMgr); break;
                case 4: inventoryModule(scanner, inventoryMgr, logMgr); break;
                case 5: routeModule(scanner, routeMgr, logMgr); break;
                default: System.out.println("⚠️ Invalid option.");
            }
        }
        scanner.close();
        System.out.println("👋 System Closed. Goodbye!");
    }

    // --- MODULE 1: VEHICLES ---
    private static void vehicleModule(Scanner scanner, VehicleManager mgr, LogManager logMgr) {
        while (true) {
            System.out.println("\n--- Vehicle Management ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. View All Vehicles");
            System.out.println("3. Search Vehicle");
            System.out.println("4. Delete Vehicle");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 5) break;

            if (choice == 1) {
                System.out.print("Reg Number: "); String reg = scanner.nextLine();
                System.out.print("Owner Name: "); String owner = scanner.nextLine();
                System.out.print("Model: "); String model = scanner.nextLine();
                System.out.print("Year: "); int year = scanner.nextInt(); scanner.nextLine();
                mgr.addVehicle(new Vehicle(reg, owner, model, year));
                // ✅ AUTO LOG
                logMgr.addLog(new ActivityLog(java.time.LocalDateTime.now().toString(), reg, "Vehicle Added"));
                mgr.displayAllVehicles(); 
            } else if (choice == 2) {
                mgr.displayAllVehicles();
            } else if (choice == 3) {
                System.out.print("Enter Reg Number: "); String reg = scanner.nextLine();
                Vehicle v = mgr.searchVehicle(reg);
                System.out.println(v != null ? "✅ Found: " + v : "❌ Not Found");
            } else if (choice == 4) {
                System.out.print("Enter Reg Number to Delete: "); String reg = scanner.nextLine();
                mgr.deleteVehicle(reg);
                // ✅ AUTO LOG
                logMgr.addLog(new ActivityLog(java.time.LocalDateTime.now().toString(), reg, "Vehicle Deleted"));
                mgr.displayAllVehicles(); 
            } else {
                System.out.println("⚠️ Invalid option.");
            }
        }
    }

    // --- MODULE 2: SERVICES ---
    private static void serviceModule(Scanner scanner, ServiceSchedule mgr, LogManager logMgr) {
        while (true) {
            System.out.println("\n--- Service Scheduling ---");
            System.out.println("1. Add Service");
            System.out.println("2. View All Services");
            System.out.println("3. Remove Service");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 4) break;

            if (choice == 1) {
                System.out.print("Service ID: "); String id = scanner.nextLine();
                System.out.print("Vehicle Reg: "); String reg = scanner.nextLine();
                System.out.print("Service Type: "); String type = scanner.nextLine();
                System.out.print("Date (YYYY-MM-DD): "); String date = scanner.nextLine();
                mgr.addService(new Service(id, reg, type, date));
                // ✅ AUTO LOG
                logMgr.addLog(new ActivityLog(java.time.LocalDateTime.now().toString(), reg, "Service Added: " + type));
                mgr.displayServices(); 
            } else if (choice == 2) {
                mgr.displayServices();
            } else if (choice == 3) {
                System.out.print("Enter Vehicle Reg to Remove Service: "); String reg = scanner.nextLine();
                mgr.removeService(reg);
                // ✅ AUTO LOG
                logMgr.addLog(new ActivityLog(java.time.LocalDateTime.now().toString(), reg, "Service Removed"));
                mgr.displayServices(); 
            } else {
                System.out.println("⚠️ Invalid option.");
            }
        }
    }

    // --- MODULE 3: LOGS ---
    private static void logModule(Scanner scanner, LogManager mgr) {
        while (true) {
            System.out.println("\n--- Activity Logs ---");
            System.out.println("1. Add Manual Log");
            System.out.println("2. View All Logs");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 3) break;

            if (choice == 1) {
                System.out.print("Vehicle Reg: "); String reg = scanner.nextLine();
                System.out.print("Activity: "); String act = scanner.nextLine();
                String time = java.time.LocalDateTime.now().toString();
                mgr.addLog(new ActivityLog(time, reg, act));
                mgr.viewLogs(); 
            } else if (choice == 2) {
                mgr.viewLogs();
            } else {
                System.out.println("⚠️ Invalid option.");
            }
        }
    }

    // --- MODULE 4: INVENTORY ---
    private static void inventoryModule(Scanner scanner, InventoryManager mgr, LogManager logMgr) {
        while (true) {
            System.out.println("\n--- Spare Parts Inventory ---");
            System.out.println("1. Add Part");
            System.out.println("2. Search Part");
            System.out.println("3. Delete Part");
            System.out.println("4. View All Inventory (Sorted)");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 5) break;

            if (choice == 1) {
                System.out.print("Part ID: "); String id = scanner.nextLine();
                System.out.print("Name: "); String name = scanner.nextLine();
                System.out.print("Qty: "); int qty = scanner.nextInt(); scanner.nextLine();
                System.out.print("Price: "); double price = scanner.nextDouble(); scanner.nextLine();
                mgr.addPart(new Part(id, name, qty, price));
                // ✅ AUTO LOG
                logMgr.addLog(new ActivityLog(java.time.LocalDateTime.now().toString(), "INV", "Part Added: " + id));
                mgr.displayInventory(); 
            } else if (choice == 2) {
                System.out.print("Search ID: "); String searchId = scanner.nextLine();
                Part p = mgr.searchPart(searchId);
                System.out.println(p != null ? p : "❌ Not Found");
            } else if (choice == 3) {
                System.out.print("Delete Part ID: "); String delId = scanner.nextLine();
                mgr.deletePart(delId);
                // ✅ AUTO LOG
                logMgr.addLog(new ActivityLog(java.time.LocalDateTime.now().toString(), "INV", "Part Deleted: " + delId));
                mgr.displayInventory();
            } else if (choice == 4) {
                mgr.displayInventory();
            } else {
                System.out.println("⚠️ Invalid option.");
            }
        }
    }

    // --- MODULE 5: ROUTES ---
    private static void routeModule(Scanner scanner, RouteManager mgr, LogManager logMgr) {
        while (true) {
            System.out.println("\n--- Route & Fleet Management ---");
            System.out.println("1. Register Location");
            System.out.println("2. Add Road Connection");
            System.out.println("3. Find Shortest Path");
            System.out.println("4. View Network");
            System.out.println("5. View All Locations");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 6) break;

            if (choice == 1) {
                // Register a new location
                System.out.print("Location ID (e.g., L1): "); String id = scanner.nextLine();
                System.out.print("Location Name (e.g., Main Depot): "); String name = scanner.nextLine();
                mgr.registerLocation(id, name);
                // ✅ AUTO LOG
                logMgr.addLog(new ActivityLog(java.time.LocalDateTime.now().toString(), "ROUTE", "Location Registered: " + name));
            } else if (choice == 2) {
                // Add road between existing locations
                System.out.print("From Location ID: "); String from = scanner.nextLine();
                System.out.print("To Location ID: "); String to = scanner.nextLine();
                System.out.print("Distance (km): "); double dist = scanner.nextDouble(); scanner.nextLine();
                mgr.addRoadById(from, to, dist);
                // ✅ AUTO LOG
                logMgr.addLog(new ActivityLog(java.time.LocalDateTime.now().toString(), "ROUTE", "Road Added: " + from + "->" + to));
            } else if (choice == 3) {
                System.out.print("Start Location ID: "); String startId = scanner.nextLine();
                System.out.print("End Location ID: "); String endId = scanner.nextLine();
                Location start = mgr.getLocationById(startId);
                Location end = mgr.getLocationById(endId);
                if (start != null && end != null) {
                    mgr.findShortestPath(start, end);
                } else {
                    System.out.println("❌ Location not found.");
                }
            } else if (choice == 4) {
                mgr.displayNetwork();
            } else if (choice == 5) {
                mgr.displayLocations();
            } else {
                System.out.println("⚠️ Invalid option.");
            }
        }
    }
}