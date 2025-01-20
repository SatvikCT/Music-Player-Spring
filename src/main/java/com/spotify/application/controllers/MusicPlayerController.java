package com.spotify.application.controllers;

import com.spotify.application.model.Song;
import com.spotify.application.service.MusicPlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicPlayerController {
    private final MusicPlayerService service;

    public MusicPlayerController(MusicPlayerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCurrentPlaylistToCsv(@RequestParam String fileName) {
        try {
            service.saveCurrentPlaylistToCsv(fileName);
            return ResponseEntity.ok("Playlist saved successfully as " + fileName + ".csv");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving playlist: " + e.getMessage());
        }
    }

    @GetMapping("/playlists")
    public ResponseEntity<List<String>> getAllPlaylists() {
        List<String> playlists = service.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @PostMapping("/addSong")
    public ResponseEntity<String> addSong(@RequestParam String playlistName, @RequestParam String title, @RequestParam String artist) {
        service.addSong(playlistName, title, artist);
        return ResponseEntity.ok("Song added successfully to " + playlistName);
    }

    @PostMapping("/playSong")
    public ResponseEntity<String> playSong(@RequestParam String title) {
        try {
            service.playSong(title);
            return ResponseEntity.ok("Playing song: " + title);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Song not found: " + title);
        }
    }

    @PostMapping("/switchPlaylist")
    public ResponseEntity<String> switchPlaylist(@RequestParam String playlistName) {
        service.switchPlaylist(playlistName);
        return ResponseEntity.ok("Switched to playlist: " + playlistName);
    }

    @GetMapping("/topSongs")
    public ResponseEntity<List<Song>> getTopSongs() {
        List<Song> topSongs = service.getTopSongs();
        return ResponseEntity.ok(topSongs);
    }

    @GetMapping("/topSongsByDate")
    public ResponseEntity<List<Song>> getTopSongsByDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Song> topSongs = service.getTopSongsByDate(localDate);
        return ResponseEntity.ok(topSongs);
    }

    @GetMapping("/topSongsByArtist")
    public ResponseEntity<List<Song>> getTopSongsByArtist(@RequestParam String artist) {
        List<Song> topSongs = service.getTopSongsByArtist(artist);
        return ResponseEntity.ok(topSongs);
    }
}