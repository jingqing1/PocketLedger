package com.example.pocketledger.dataclass;

public class Bill {
    private int id;
    private String projectName;
    private double amount;
    private String entryTime;
    private String category;

    public Bill(int id, String projectName, double amount, String entryTime, String category) {
        this.id = id;
        this.projectName = projectName;
        this.amount = amount;
        this.entryTime = entryTime;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public double getAmount() {
        return amount;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public String getCategory() {
        return category;
    }
}
