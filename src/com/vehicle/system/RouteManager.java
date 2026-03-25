package com.vehicle.system;

import java.util.*;
import java.io.PrintWriter;
import java.util.Set;
import java.util.Map;
public class RouteManager {
    private Map<Location, List<Edge>> graph;
public Set<Location> getAllLocations() {
        return graph.keySet();
    }

    // Get all edges for a location (for FileHandler)
    public List<Edge> getEdges(Location loc) {
        return graph.getOrDefault(loc, new ArrayList<>());
    }

    // Get all edges in the graph (for FileHandler)
    public Map<Location, List<Edge>> getAllEdges() {
        return graph;
    }
    public RouteManager() {
        this.graph = new HashMap<>();
        // initializeDefaultRoutes(); // REMOVED: We want dynamic data now
    }

   public void registerLocation(String id, String name) {
        Location loc = new Location(id, name);
        graph.putIfAbsent(loc, new ArrayList<>());
        System.out.println("✅ Location registered: " + name + " (" + id + ")");
    }

    public void addLocation(Location loc) {
        graph.putIfAbsent(loc, new ArrayList<>());
    }

    public void addRoad(Location from, Location to, double distance) {
        graph.putIfAbsent(from, new ArrayList<>());
        graph.putIfAbsent(to, new ArrayList<>());
        graph.get(from).add(new Edge(to, distance));
        graph.get(to).add(new Edge(from, distance)); // Bidirectional
    }

    // Helper to find location by ID
    public Location getLocationById(String id) {
        for (Location loc : graph.keySet()) {
            if (loc.getId().equals(id)) return loc;
        }
        return null;
    }

    // Dijkstra's Algorithm
    public void findShortestPath(Location start, Location end) {
        Map<Location, Double> distances = new HashMap<>();
        Map<Location, Location> previous = new HashMap<>();
        
        // Fixed Comparator for Java Compatibility
        PriorityQueue<Location> queue = new PriorityQueue<>((loc1, loc2) -> 
            Double.compare(distances.getOrDefault(loc1, Double.MAX_VALUE), 
                           distances.getOrDefault(loc2, Double.MAX_VALUE))
        );

        for (Location loc : graph.keySet()) {
            distances.put(loc, Double.MAX_VALUE);
        }
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            if (current.equals(end)) break;

            for (Edge edge : graph.getOrDefault(current, new ArrayList<>())) {
                double newDist = distances.get(current) + edge.weight;
                if (newDist < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDist);
                    previous.put(edge.destination, current);
                    queue.add(edge.destination);
                }
            }
        }

        if (distances.get(end) == Double.MAX_VALUE) {
            System.out.println("❌ No route found.");
        } else {
            System.out.println("✅ Shortest Path Found!");
            System.out.println("Total Distance: " + distances.get(end) + " km");
            
            List<String> path = new ArrayList<>();
            Location current = end;
            while (current != null) {
                path.add(current.getName());
                current = previous.get(current);
            }
            Collections.reverse(path);
            System.out.println("Route: " + String.join(" -> ", path));
        }
    }

    public void displayNetwork() {
        if (graph.isEmpty()) {
            System.out.println("📭 No routes defined yet.");
            return;
        }
        System.out.println("--- Fleet Network ---");
        for (Location loc : graph.keySet()) {
            System.out.print(loc.getName() + " connects to: ");
            for (Edge e : graph.get(loc)) {
                System.out.print(e.destination.getName() + "(" + e.weight + "km) ");
            }
            System.out.println();
        }
    }

    // For FileHandler
    public void saveGraphToWriter(PrintWriter writer) {
        for (Location loc : graph.keySet()) {
            for (Edge edge : graph.get(loc)) {
                writer.println(loc.getId() + "," + loc.getName() + "," + edge.destination.getId() + "," + edge.weight);
            }
        }
    }
    
    // For Loading
     public void addRoadById(String fromId, String toId, double weight) {
        Location from = getLocationById(fromId);
        Location to = getLocationById(toId);
        
        if (from == null || to == null) {
            System.out.println("❌ One or both locations not registered. Please register them first.");
            return;
        }
        
        addRoad(from, to, weight);
        System.out.println("✅ Road added: " + from.getName() + " <-> " + to.getName() + " (" + weight + "km)");
    }

    // Add method to list all registered locations
    public void displayLocations() {
        if (graph.isEmpty()) {
            System.out.println("📭 No locations registered.");
            return;
        }
        System.out.println("--- Registered Locations ---");
        for (Location loc : graph.keySet()) {
            System.out.println("ID: " + loc.getId() + " | Name: " + loc.getName());
        }
    }
}