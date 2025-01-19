package com.spotify.application.controllers;

import com.spotify.application.model.Song;
import com.spotify.application.service.MusicPlayerService;
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

    @PostMapping("/add")
    public void addSong(@RequestParam String playlist, @RequestParam String title, @RequestParam String artist) {
        service.addSong(playlist, title, artist);
    }

    @PostMapping("/play")
    public void playSong(@RequestParam String title) {
        service.playSong(title);
    }

    @PostMapping("/load")
    public void loadSongsFromCsv(@RequestParam String playlist, @RequestParam String relativePath) {
        service.loadSongsFromCsv(relativePath, playlist);
    }

    @PostMapping("/switch")
    public void switchPlaylist(@RequestParam String playlist) {
        service.switchPlaylist(playlist);
    }

    @GetMapping("/top")
    public List<Song> getTopSongs() {
        return service.getTopSongs();
    }

    @GetMapping("/topByDate")
    public List<Song> getTopSongsByDate(@RequestParam String date) {
        return service.getTopSongsByDate(LocalDate.parse(date));
    }

    @GetMapping("/topByArtist")
    public List<Song> getTopSongsByArtist(@RequestParam String artist) {
        return service.getTopSongsByArtist(artist);
    }

    @GetMapping("/playlists")
    public List<String> getAllPlaylists() {
        return service.getAllPlaylists();
    }
}
