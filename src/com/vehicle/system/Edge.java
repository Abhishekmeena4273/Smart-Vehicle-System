package com.vehicle.system;

public class Edge {
    Location destination;
    double weight; // Distance or Time

    public Edge(Location destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }
}