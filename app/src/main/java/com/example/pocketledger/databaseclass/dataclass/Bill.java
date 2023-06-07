package com.example.pocketledger.databaseclass.dataclass;

public class Bill {
    private int id;
    private String projectName;
    private double amount;
    private String entryTime;
    private String category;

    private boolean isFavorite;

    public Bill(int id, String projectName, double amount, String entryTime, String category,Boolean isFavorite) {
        this.id = id;
        this.projectName = projectName;
        this.amount = amount;
        this.entryTime = entryTime;
        this.category = category;
        this.isFavorite = isFavorite;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
