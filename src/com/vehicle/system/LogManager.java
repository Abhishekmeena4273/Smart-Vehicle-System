package com.vehicle.system;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private Queue<ActivityLog> activityQueue;

    public LogManager() {
        this.activityQueue = new LinkedList<>();
    }

    // 1. ADD (Enqueue)
    public void addLog(ActivityLog log) {
        activityQueue.offer(log);
        System.out.println("✅ Log recorded.");
    }

    // 2. VIEW (Display without removing)
    public void viewLogs() {
        if (activityQueue.isEmpty()) {
            System.out.println("📭 No activity logs.");
            return;
        }
        int count = 1;
        for (ActivityLog log : activityQueue) {
            System.out.println(count + ". " + log);
            count++;
        }
    }

    // 3. PROCESS (Dequeue - Remove oldest)
    public void processOldestLog() {
        ActivityLog log = activityQueue.poll();
        if (log != null) {
            System.out.println("✅ Processed: " + log);
        } else {
            System.out.println("❌ No logs to process.");
        }
    }

    // 4. GET ALL (For FileHandler - CRITICAL FIX)
    public List<ActivityLog> getAllLogs() {
        List<ActivityLog> allLogs = new ArrayList<>();
        for (ActivityLog log : activityQueue) {
            allLogs.add(log);
        }
        return allLogs;
    }

    // 5. GET COUNT (For debugging)
    public int getLogCount() {
        return activityQueue.size();
    }

    // 6. CLEAR (Optional - for testing)
    public void clearLogs() {
        activityQueue.clear();
    }
}