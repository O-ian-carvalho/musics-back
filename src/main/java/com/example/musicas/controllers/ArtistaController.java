package com.example.musicas.controllers;

import com.example.musicas.dtos.ArtistaDto;
import com.example.musicas.models.Artista;
import com.example.musicas.services.ArtistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/artistas")
@RestController
public class ArtistaController {
    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    // 🔹 Criar um novo artista
    @PostMapping
    public ResponseEntity<Artista> create(@RequestBody ArtistaDto artistaDto) {
        Artista artista = artistaService.add(artistaDto);
        if (artista != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(artista);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 🔹 Buscar todos os artistas
    @GetMapping
    public ResponseEntity<List<Artista>> getAll() {
        return ResponseEntity.ok(artistaService.getAll());
    }

    // 🔹 Buscar um artista pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Artista> getById(@PathVariable UUID id) {
        Optional<Artista> artista = artistaService.get(id);
        return artista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Atualizar um artista pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Artista> update(@PathVariable UUID id, @RequestBody ArtistaDto artistaDto) {
        Artista artistaAtualizado = artistaService.update(id, artistaDto);
        if (artistaAtualizado != null) {
            return ResponseEntity.ok(artistaAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // 🔹 Deletar um artista pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = artistaService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
