package com.example.musicas.controllers;


import com.example.musicas.dtos.ArtistaDto;
import com.example.musicas.models.Artista;
import com.example.musicas.services.ArtistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/artistas")
@RestController
public class ArtistaController
{
    private final ArtistaService artistaService;


    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @PostMapping
    public ResponseEntity<Artista> saveBook(@RequestBody ArtistaDto artistaDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artistaService.add(artistaDto));
    }

    @GetMapping
    public String Get()
    {
        return "oi";
    }

}
