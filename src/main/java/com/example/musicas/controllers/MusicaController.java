package com.example.musicas.controllers;

import com.example.musicas.dtos.MusicaDto;
import com.example.musicas.dtos.MusicaResponseDto;
import com.example.musicas.services.MusicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/musicas")
public class MusicaController {

    private final MusicaService musicaService;
    private final ObjectMapper objectMapper; // ✅ Injeta o ObjectMapper

    public MusicaController(MusicaService musicaService, ObjectMapper objectMapper) {
        this.musicaService = musicaService;
        this.objectMapper = objectMapper; // ✅ Inicializa o ObjectMapper
    }

    @Operation()
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestPart("dados") String dadosJson,
            @RequestPart("arquivo") MultipartFile arquivo) {

        try {
            // Converter JSON para objeto MusicaDto
            MusicaDto musicaDto = objectMapper.readValue(dadosJson, MusicaDto.class);

            // Verifica se o arquivo não está vazio
            if (arquivo.isEmpty()) {
                return ResponseEntity.badRequest().body("Arquivo de áudio não pode estar vazio!");
            }

            // Salvar o arquivo e adicionar a música
            String caminhoArquivo = musicaService.saveArchive(arquivo);
            MusicaResponseDto response = musicaService.add(musicaDto, caminhoArquivo);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar a requisição: " + ex.getMessage());
        }
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


    @GetMapping("/buscar")
    public List<MusicaResponseDto> buscarMusicas(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String artista,
            @RequestParam(required = false) String album,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Integer ano) {

        return musicaService.buscarMusicas(titulo, artista, album, genero, ano);
    }
}
