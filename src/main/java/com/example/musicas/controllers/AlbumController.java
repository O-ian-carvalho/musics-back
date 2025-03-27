package com.example.musicas.controllers;

import com.example.musicas.dtos.AlbumDto;
import com.example.musicas.models.Album;
import com.example.musicas.services.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/albuns")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // Criar um novo álbum
    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody AlbumDto albumDto) {
        return ResponseEntity.ok(albumService.add(albumDto));
    }

    // Buscar um álbum por ID
    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbum(@PathVariable UUID id) {
        Optional<Album> album = albumService.getById(id);
        return album.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar todos os álbuns
    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAll());
    }

    // Atualizar um álbum
    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable UUID id, @RequestBody AlbumDto albumDto) {
        Optional<Album> updatedAlbum = albumService.update(id, albumDto);
        return updatedAlbum.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar um álbum
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable UUID id) {
        return albumService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
