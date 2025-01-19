package com.spotify.application.model;

public class Song {
    private final String title;
    private final String artist;
    private int totalPlays;
    private int currentPlays;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.totalPlays = 0;
        this.currentPlays = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getTotalPlays() {
        return totalPlays;
    }

    public int getCurrentPlays() {
        return currentPlays;
    }

    public void play() {
        totalPlays++;
        currentPlays++;
    }

    public void resetDailyPlays() {
        currentPlays = 0;
    }

    @Override
    public String toString() {
        return title + " (" + artist + ", Total Plays: " + totalPlays + ")";
    }
}
