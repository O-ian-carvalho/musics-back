package com.example.musicas.controllers;

import com.example.musicas.dtos.GeneroDto;
import com.example.musicas.models.Genero;
import com.example.musicas.services.GeneroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Genero> create(@RequestBody GeneroDto generoDto) {
        Genero genero = generoService.add(generoDto);
        if (genero != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(genero);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<List<Genero>> getAll() {
        return ResponseEntity.ok(generoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> getById(@PathVariable UUID id) {
        Optional<Genero> genero = generoService.get(id);
        return genero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> update(@PathVariable UUID id, @RequestBody GeneroDto generoDto) {
        Genero generoAtualizado = generoService.update(id, generoDto);
        if (generoAtualizado != null) {
            return ResponseEntity.ok(generoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = generoService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
