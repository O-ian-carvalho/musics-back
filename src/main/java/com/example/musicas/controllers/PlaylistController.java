package com.example.musicas.controllers;

import com.example.musicas.dtos.PlaylistDto;
import com.example.musicas.models.Playlist;
import com.example.musicas.services.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistDto playlistDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playlistService.createPlaylist(playlistDto));
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable UUID id) {
        Optional<Playlist> playlist = playlistService.getPlaylistById(id);
        return playlist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable UUID id, @RequestBody PlaylistDto playlistDto) {
        return ResponseEntity.ok(playlistService.updatePlaylist(id, playlistDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable UUID id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{playlistId}/musicas/{musicaId}")
    public ResponseEntity<Playlist> addMusicaToPlaylist(@PathVariable UUID playlistId, @PathVariable UUID musicaId) {
        return ResponseEntity.ok(playlistService.addMusicaToPlaylist(playlistId, musicaId));
    }

    @DeleteMapping("/{playlistId}/musicas/{musicaId}")
    public ResponseEntity<Playlist> removeMusicaFromPlaylist(@PathVariable UUID playlistId, @PathVariable UUID musicaId) {
        return ResponseEntity.ok(playlistService.removeMusicaFromPlaylist(playlistId, musicaId));
    }
}
