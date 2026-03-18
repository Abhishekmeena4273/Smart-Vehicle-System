package com.vehicle.system;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VehicleManager manager = new VehicleManager();

        System.out.println("🚗 Welcome to Smart Vehicle Management System (Module 1 Test)");

        while (true) {
            System.out.println("\n--- Vehicle Management ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Search Vehicle");
            System.out.println("3. Update Vehicle");
            System.out.println("4. Delete Vehicle");
            System.out.println("5. Show All");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 6) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter Reg Number: ");
                    String reg = scanner.nextLine();
                    System.out.print("Enter Owner Name: ");
                    String owner = scanner.nextLine();
                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter Year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    manager.addVehicle(new Vehicle(reg, owner, model, year));
                    break;
                case 2:
                    System.out.print("Enter Reg Number to search: ");
                    String searchReg = scanner.nextLine();
                    Vehicle found = manager.searchVehicle(searchReg);
                    if (found != null) System.out.println("Found: " + found);
                    else System.out.println("❌ Not found.");
                    break;
                case 3:
                    System.out.print("Enter Reg Number to update: ");
                    String upReg = scanner.nextLine();
                    System.out.print("New Owner: ");
                    String upOwner = scanner.nextLine();
                    System.out.print("New Model: ");
                    String upModel = scanner.nextLine();
                    System.out.print("New Year: ");
                    int upYear = scanner.nextInt();
                    scanner.nextLine();
                    manager.updateVehicle(upReg, upOwner, upModel, upYear);
                    break;
                case 4:
                    System.out.print("Enter Reg Number to delete: ");
                    String delReg = scanner.nextLine();
                    manager.deleteVehicle(delReg);
                    break;
                case 5:
                    manager.displayAllVehicles();
                    break;
                default:
                    System.out.println("⚠️ Invalid option.");
            }
        }
        scanner.close();
        System.out.println("👋 System Closed.");
    }
}