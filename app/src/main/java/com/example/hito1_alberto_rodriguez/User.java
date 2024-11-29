package com.example.hito1_alberto_rodriguez;

public class User {
    private String id;
    private String username;
    private String password;
    private int score;
    private int level;
    private int pointsPerClick;
    private int upgradeCost;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String username, String password, int score, int level, int pointsPerClick, int upgradeCost) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.score = score;
        this.level = level;
        this.pointsPerClick = pointsPerClick;
        this.upgradeCost = upgradeCost;
    }

    // Getters and setters for each field
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPointsPerClick() {
        return pointsPerClick;
    }

    public void setPointsPerClick(int pointsPerClick) {
        this.pointsPerClick = pointsPerClick;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }
}