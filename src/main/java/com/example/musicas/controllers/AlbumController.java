package com.example.musicas.controllers;

import com.example.musicas.dtos.AlbumDto;
import com.example.musicas.dtos.ErrorResponse;
import com.example.musicas.models.Album;
import com.example.musicas.services.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
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
        Album album = albumService.add(albumDto);
        return new ResponseEntity<>(album, HttpStatus.CREATED);
    }

    // Buscar um álbum por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbum(@PathVariable UUID id, WebRequest request) {
        Optional<Album> albumOptional = albumService.getById(id);
        if (albumOptional.isPresent()) {
            return new ResponseEntity<>(albumOptional.get(), HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Álbum não encontrado com o ID: " + id);
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setError("Not Found");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    // Buscar todos os álbuns
    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.getAll();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    // Atualizar um álbum
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable UUID id, @RequestBody AlbumDto albumDto, WebRequest request) {
        try {
            Optional<Album> updatedAlbumOptional = albumService.update(id, albumDto);
            Album updatedAlbum = updatedAlbumOptional.orElseThrow(() -> new NoSuchElementException("Álbum não encontrado com o ID: " + id));
            return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setError("Not Found");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    // Deletar um álbum
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable UUID id, WebRequest request) {
        try {
            albumService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}