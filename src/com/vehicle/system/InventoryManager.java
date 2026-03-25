package com.vehicle.system;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private PartNode root;

    public InventoryManager() {
        this.root = null;
    }

    // 1. INSERT (Into BST)
    public void addPart(Part part) {
        root = insertRec(root, part);
        System.out.println("✅ Part added to inventory!");
    }

    private PartNode insertRec(PartNode root, Part part) {
        if (root == null) {
            root = new PartNode(part);
            return root;
        }

        // Compare by PartID (String comparison)
        if (part.getPartId().compareTo(root.part.getPartId()) < 0) {
            root.left = insertRec(root.left, part);
        } else if (part.getPartId().compareTo(root.part.getPartId()) > 0) {
            root.right = insertRec(root.right, part);
        } else {
            // ID already exists, update quantity instead
            root.part.setQuantity(root.part.getQuantity() + part.getQuantity());
            System.out.println("⚠️ Part ID exists. Quantity updated.");
        }
        return root;
    }

    // 2. SEARCH (In BST)
    public Part searchPart(String partId) {
        return searchRec(root, partId);
    }

    private Part searchRec(PartNode root, String partId) {
        if (root == null || root.part.getPartId().equals(partId)) {
            return root != null ? root.part : null;
        }

        if (partId.compareTo(root.part.getPartId()) < 0) {
            return searchRec(root.left, partId);
        } else {
            return searchRec(root.right, partId);
        }
    }

    // 3. DISPLAY (Inorder Traversal = Sorted Output)
    public void displayInventory() {
        if (root == null) {
            System.out.println("📭 Inventory is empty.");
            return;
        }
        System.out.println("--- Sorted Inventory (by ID) ---");
        inorderRec(root);
    }

    private void inorderRec(PartNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.part);
            inorderRec(root.right);
        }
    }
    public List<Part> getAllParts() {
        List<Part> list = new ArrayList<>();
        collectParts(root, list);
        return list;
    }

    private void collectParts(PartNode root, List<Part> list) {
        if (root != null) {
            collectParts(root.left, list);
            list.add(root.part);
            collectParts(root.right, list);
        }
    }
    public void deletePart(String partId) {
        root = deleteRec(root, partId);
    }

    private PartNode deleteRec(PartNode root, String partId) {
        if (root == null) return null;

        if (partId.compareTo(root.part.getPartId()) < 0) {
            root.left = deleteRec(root.left, partId);
        } else if (partId.compareTo(root.part.getPartId()) > 0) {
            root.right = deleteRec(root.right, partId);
        } else {
            // Node found
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            // Two children: Get inorder successor
            root.part = minValue(root.right);
            root.right = deleteRec(root.right, root.part.getPartId());
        }
        return root;
    }

    private Part minValue(PartNode root) {
        Part minVal = root.part;
        while (root.left != null) {
            minVal = root.left.part;
            root = root.left;
        }
        return minVal;
    }
}