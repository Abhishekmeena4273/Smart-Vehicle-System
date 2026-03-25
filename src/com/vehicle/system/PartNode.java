package com.vehicle.system;

public class PartNode {
    Part part;
    PartNode left;
    PartNode right;

    public PartNode(Part part) {
        this.part = part;
        this.left = null;
        this.right = null;
    }
}