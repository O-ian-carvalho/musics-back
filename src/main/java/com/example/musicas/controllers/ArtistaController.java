package com.example.musicas.controllers;

import com.example.musicas.dtos.ArtistaDto;
import com.example.musicas.dtos.ErrorResponse;
import com.example.musicas.models.Artista;
import com.example.musicas.services.ArtistaService;
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
@RequestMapping("/artistas")
@RestController
public class ArtistaController {
    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    // 🔹 Criar um novo artista
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ArtistaDto artistaDto, WebRequest request) {
        try {
            Artista artista = artistaService.add(artistaDto);
            return new ResponseEntity<>(artista, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // 🔹 Buscar todos os artistas
    @GetMapping
    public ResponseEntity<List<Artista>> getAll() {
        return ResponseEntity.ok(artistaService.getAll());
    }

    // 🔹 Buscar um artista pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id, WebRequest request) {
        try {
            Optional<Artista> artista = artistaService.get(id);
            if (artista.isPresent()) {
                return new ResponseEntity<>(artista.get(), HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Artista não encontrado com o ID: " + id);
                errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
                errorResponse.setError("Not Found");
                errorResponse.setTimestamp(LocalDateTime.now());
                errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // 🔹 Atualizar um artista pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ArtistaDto artistaDto, WebRequest request) {
        try {
            Artista artistaAtualizado = artistaService.update(id, artistaDto);
            if (artistaAtualizado != null) {
                return new ResponseEntity<>(artistaAtualizado, HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Artista não encontrado com o ID: " + id);
                errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
                errorResponse.setError("Not Found");
                errorResponse.setTimestamp(LocalDateTime.now());
                errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // 🔹 Deletar um artista pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, WebRequest request) {
        try {
            boolean deleted = artistaService.delete(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Artista não encontrado com o ID: " + id);
                errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
                errorResponse.setError("Not Found");
                errorResponse.setTimestamp(LocalDateTime.now());
                errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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
}