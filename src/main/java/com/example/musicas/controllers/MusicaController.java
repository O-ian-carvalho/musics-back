package com.example.musicas.controllers;

import com.example.musicas.dtos.MusicaDto;
import com.example.musicas.dtos.MusicaResponseDto;
import com.example.musicas.services.MusicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    private final MusicaService musicaService;

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    @PostMapping
    public ResponseEntity<MusicaResponseDto> create(@RequestBody MusicaDto musicaDto) {
        return ResponseEntity.ok(musicaService.add(musicaDto));
    }

    @GetMapping
    public ResponseEntity<List<MusicaResponseDto>> getAll() {
        return ResponseEntity.ok(musicaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaResponseDto> getById(@PathVariable UUID id) {
        return musicaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicaResponseDto> update(@PathVariable UUID id, @RequestBody MusicaDto musicaDto) {
        return musicaService.update(id, musicaDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (musicaService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
