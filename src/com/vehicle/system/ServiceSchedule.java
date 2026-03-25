package com.vehicle.system;

import java.util.ArrayList;
import java.util.List;

public class ServiceSchedule {
    private ServiceNode head;

    public ServiceSchedule() {
        this.head = null;
    }
        public List<Service> getAllServices() {
        List<Service> list = new ArrayList<>();
        ServiceNode current = head;
        while (current != null) {
            list.add(current.service);
            current = current.next;
        }
        return list;
    }

    // 1. INSERT (Add to end of list)
    public void addService(Service service) {
        ServiceNode newNode = new ServiceNode(service);
        if (head == null) {
            head = newNode;
        } else {
            ServiceNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        System.out.println("✅ Service appointment added!");
    }

    // 2. DISPLAY (Traverse the list)
    public void displayServices() {
        if (head == null) {
            System.out.println("📭 No service appointments.");
            return;
        }
        ServiceNode current = head;
        while (current != null) {
            System.out.println(current.service);
            current = current.next;
        }
    }

    // 3. DELETE (Remove by Vehicle Reg)
    public void removeService(String vehicleReg) {
        if (head == null) return;

        // If head needs to be removed
        if (head.service.getVehicleReg().equals(vehicleReg)) {
            head = head.next;
            System.out.println("✅ Service removed!");
            return;
        }

        // Search for the node before the one to delete
        ServiceNode current = head;
        while (current.next != null && !current.next.service.getVehicleReg().equals(vehicleReg)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next; // Skip the node
            System.out.println("✅ Service removed!");
        } else {
            System.out.println("❌ Service not found for this vehicle.");
        }
    }
}