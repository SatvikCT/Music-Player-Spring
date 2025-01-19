package com.spotify.application.model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Playlist {
    private final String name;
    private final List<Song> songs = new ArrayList<>();
    private final Map<LocalDate, List<Song>> dateTop = new HashMap<>();
    private final Map<String, List<Song>> artistTop = new HashMap<>();
    private final List<Song> allTop = new ArrayList<>();
    private LocalDate currentDate = LocalDate.now();

    public Playlist(String name) {
        this.name = name;
        initializeTopLists();
    }

    private void initializeTopLists() {
        dateTop.put(currentDate, new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            dateTop.get(currentDate).add(new Song("null", "null"));
            allTop.add(new Song("null", "null"));
        }
    }

    public String getName() {
        return name;
    }

    public void addSong(String title, String artist) {
        Song song = new Song(title, artist);
        songs.add(song);

        artistTop.putIfAbsent(artist, new ArrayList<>());
        artistTop.get(artist).add(song);
    }

    public void playSong(String title) {
        Optional<Song> song = songs.stream().filter(s -> s.getTitle().equalsIgnoreCase(title)).findFirst();
        if (song.isPresent()) {
            song.get().play();
            adjustTopLists(song.get());
            System.out.println("Playing song: " + song.get().getTitle());
        } else {
            System.out.println("Song not found: " + title);
        }
    }

    public List<Song> getTopSongs() {
        return new ArrayList<>(allTop);
    }

    public List<Song> getTopSongsByDate(LocalDate date) {
        return new ArrayList<>(dateTop.getOrDefault(date, new ArrayList<>()));
    }

    public List<Song> getTopSongsByArtist(String artist) {
        return new ArrayList<>(artistTop.getOrDefault(artist, new ArrayList<>()));
    }

    private void adjustTopLists(Song song) {
        adjustList(allTop, song);
        adjustList(dateTop.computeIfAbsent(currentDate, k -> new ArrayList<>()), song);
        adjustList(artistTop.computeIfAbsent(song.getArtist(), k -> new ArrayList<>()), song);
    }

    private void adjustList(List<Song> list, Song song) {
        list.remove(song);

        int idx = list.size() - 1;
        while (idx >= 0 && song.getTotalPlays() > list.get(idx).getTotalPlays()) {
            idx--;
        }

        list.add(idx + 1, song);

        if (list.size() > 10) {
            list.remove(10);
        }
    }

    public void nextDay() {
        songs.forEach(Song::resetDailyPlays);
        currentDate = currentDate.plusDays(1);
        dateTop.put(currentDate, new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            dateTop.get(currentDate).add(new Song("null", "null"));
        }
    }
}
