package com.example.musicas.controllers;

import com.example.musicas.dtos.GeneroDto;
import com.example.musicas.dtos.ErrorResponse;
import com.example.musicas.models.Genero;
import com.example.musicas.services.GeneroService;
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
@RequestMapping("/generos")
public class GeneroController {
    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GeneroDto generoDto, WebRequest request) {
        try {
            Genero genero = generoService.add(generoDto);
            return new ResponseEntity<>(genero, HttpStatus.CREATED);
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

    @GetMapping
    public ResponseEntity<List<Genero>> getAll() {
        return ResponseEntity.ok(generoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id, WebRequest request) {
        try {
            Optional<Genero> genero = generoService.get(id);
            if (genero.isPresent()) {
                return new ResponseEntity<>(genero.get(), HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Gênero não encontrado com o ID: " + id);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody GeneroDto generoDto, WebRequest request) {
        try {
            Genero generoAtualizado = generoService.update(id, generoDto);
            if (generoAtualizado != null) {
                return new ResponseEntity<>(generoAtualizado, HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Gênero não encontrado com o ID: " + id);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, WebRequest request) {
        try {
            boolean deleted = generoService.delete(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Gênero não encontrado com o ID: " + id);
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