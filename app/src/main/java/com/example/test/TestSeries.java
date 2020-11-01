package com.example.test;

public class TestSeries {
    private final int seriesId;
    private final String seriesName;
    private final int questions;
    private final int marks;
    private final int time;
    private final String date;
    private final int testId;
    private final String physics;
    private final String che;
    private final String biology;

    public TestSeries(int seriesId, String seriesName, int questions, int marks, int time, String date, int testId, String physics, String che, String biology) {
        this.seriesId = seriesId;
        this.seriesName = seriesName;
        this.questions = questions;
        this.marks = marks;
        this.time = time;
        this.date = date;
        this.testId = testId;
        this.physics = physics;
        this.che = che;
        this.biology = biology;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public int getQuestions() {
        return questions;
    }

    public int getMarks() {
        return marks;
    }

    public int getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public int getTestId() {
        return testId;
    }

    public String getPhysics() {
        return physics;
    }

    public String getChe() {
        return che;
    }

    public String getBiology() {
        return biology;
    }
}
