package com.spotify.application.service;

import com.spotify.application.model.Playlist;
import com.spotify.application.model.Song;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Service
public class MusicPlayerService {
    private final Map<String, Playlist> playlists = new HashMap<>();
    private String currentPlaylist = "playlist1";

    public MusicPlayerService() {
        playlists.put(currentPlaylist, new Playlist(currentPlaylist));
        loadSongsFromCsv("data/songs.csv", currentPlaylist);
    }

    public void addSong(String playlistName, String title, String artist) {
        playlists.putIfAbsent(playlistName, new Playlist(playlistName));
        playlists.get(playlistName).addSong(title, artist);
    }

    public void playSong(String title) {
        playlists.get(currentPlaylist).playSong(title);
    }

    public void loadSongsFromCsv(String relativeCsvPath, String playlistName) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(relativeCsvPath);
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + relativeCsvPath);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                Playlist playlist = playlists.computeIfAbsent(playlistName, Playlist::new);
                //playlist.getSongs().clear(); // Clear existing songs in the playlist
                String line;
                int count = 0; // Count lines read
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        playlist.addSong(parts[0].trim(), parts[1].trim());
                        count++;
                    } else {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
                System.out.println("Loaded " + count + " songs into " + playlistName);
            }
        } catch (Exception e) {
            System.err.println("Error loading songs: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void switchPlaylist(String playlistName) {
        playlists.putIfAbsent(playlistName, new Playlist(playlistName));
        currentPlaylist = playlistName;
    }

    public List<Song> getTopSongs() {
        return playlists.get(currentPlaylist).getTopSongs();
    }

    public List<Song> getTopSongsByDate(LocalDate date) {
        return playlists.get(currentPlaylist).getTopSongsByDate(date);
    }

    public List<Song> getTopSongsByArtist(String artist) {
        return playlists.get(currentPlaylist).getTopSongsByArtist(artist);
    }

    public List<String> getAllPlaylists() {
        return new ArrayList<>(playlists.keySet());
    }
}
