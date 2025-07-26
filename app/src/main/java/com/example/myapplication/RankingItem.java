package com.example.myapplication;

public class RankingItem {
    private String rank;
    private String name;
    private String points;
    private String progress;
    private String accuracy;

    private int pointsValue;
    private int progressValue;

    public RankingItem(String name, String points, String progress, String accuracy, int pointsValue, int progressValue) {
        this.name = name;
        this.points = points;
        this.progress = progress;
        this.accuracy = accuracy;
        this.pointsValue = pointsValue;
        this.progressValue = progressValue;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getPoints() {
        return points;
    }

    public String getProgress() {
        return progress;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public int getPointsValue() {
        return pointsValue;
    }

    public int getProgressValue() {
        return progressValue;
    }
}
