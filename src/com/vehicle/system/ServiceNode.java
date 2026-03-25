package com.vehicle.system;

public class ServiceNode {
    Service service;
    ServiceNode next;

    public ServiceNode(Service service) {
        this.service = service;
        this.next = null;
    }
}