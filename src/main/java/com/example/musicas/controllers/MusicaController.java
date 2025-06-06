package com.example.musicas.controllers;

import com.example.musicas.dtos.ErrorResponse;
import com.example.musicas.dtos.MusicaDto;
import com.example.musicas.dtos.MusicaResponseDto;
import com.example.musicas.services.MusicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/musicas")
public class MusicaController {

    private final MusicaService musicaService;
    private final ObjectMapper objectMapper;

    public MusicaController(MusicaService musicaService, ObjectMapper objectMapper) {
        this.musicaService = musicaService;
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "Cadastrar uma nova música")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestPart("dados") String dadosJson,
            @RequestPart("arquivo") MultipartFile arquivo,
            WebRequest request) {

        try {
            MusicaDto musicaDto = objectMapper.readValue(dadosJson, MusicaDto.class);
            // Adiciona o arquivo ao DTO
            musicaDto = new MusicaDto(musicaDto.nome(), musicaDto.lancamento(), musicaDto.duracaoEmSegundos(),
                    musicaDto.generoId(), musicaDto.artistaId(), musicaDto.albumId());

            if (arquivo.isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Arquivo de áudio não pode estar vazio!");
                errorResponse.setStatus(400);
                errorResponse.setError("Bad Request");
                errorResponse.setTimestamp(LocalDateTime.now());
                errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
                return ResponseEntity.badRequest().body(errorResponse);
            }

            MusicaResponseDto response = musicaService.add(musicaDto, arquivo);

            // Retorna 201 Created e o objeto criado
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setStatus(400);
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (IOException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Erro ao processar o arquivo: " + ex.getMessage());
            errorResponse.setStatus(500);
            errorResponse.setError("Internal Server Error");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Erro ao processar a requisição: " + ex.getMessage());
            errorResponse.setStatus(400);
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<MusicaResponseDto>> getAll() {
        return ResponseEntity.ok(musicaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id, WebRequest request) {
        try {
            Optional<MusicaResponseDto> musica = musicaService.getById(id);
            if (musica.isPresent()) {
                return ResponseEntity.ok(musica.get());
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Música não encontrada com o ID: " + id);
                errorResponse.setStatus(404);
                errorResponse.setError("Not Found");
                errorResponse.setTimestamp(LocalDateTime.now());
                errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
                return ResponseEntity.status(404).body(errorResponse);
            }
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Erro ao buscar música: " + ex.getMessage());
            errorResponse.setStatus(400);
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody MusicaDto musicaDto, WebRequest request) {
        try {
            Optional<MusicaResponseDto> musicaAtualizada = musicaService.update(id, musicaDto);
            if (musicaAtualizada.isPresent()) {
                return ResponseEntity.ok(musicaAtualizada.get());
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Música não encontrada com o ID: " + id);
                errorResponse.setStatus(404);
                errorResponse.setError("Not Found");
                errorResponse.setTimestamp(LocalDateTime.now());
                errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
                return ResponseEntity.status(404).body(errorResponse);
            }
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setStatus(400);
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Erro ao atualizar música: " + ex.getMessage());
            errorResponse.setStatus(400);
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, WebRequest request) {
        try {
            if (musicaService.delete(id)) {
                return ResponseEntity.noContent().build();
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Música não encontrada com o ID: " + id);
                errorResponse.setStatus(404);
                errorResponse.setError("Not Found");
                errorResponse.setTimestamp(LocalDateTime.now());
                errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
                return ResponseEntity.status(404).body(errorResponse);
            }
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Erro ao deletar música: " + ex.getMessage());
            errorResponse.setStatus(400);
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarMusicas(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String artista,
            @RequestParam(required = false) String album,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Integer ano,
            WebRequest request) {
        try {
            List<MusicaResponseDto> musicas = musicaService.buscarMusicas(titulo, artista, album, genero, ano);
            return ResponseEntity.ok(musicas);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Erro ao buscar músicas: " + ex.getMessage());
            errorResponse.setStatus(400);
            errorResponse.setError("Bad Request");
            errorResponse.setTimestamp(LocalDateTime.now());
            errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    @GetMapping("/{id}/arquivo")
    @Operation(
            summary = "Download do arquivo de música",
            description = "Permite baixar o arquivo de áudio da música pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Download realizado com sucesso",
                    content = @Content(
                            mediaType = "application/octet-stream",
                            schema = @Schema(type = "string", format = "binary")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Música não encontrada")
    })
    public ResponseEntity<?> getArquivoMusica(
            @Parameter(description = "ID da música", required = true)
            @PathVariable UUID id,
            WebRequest request) {
        try {
            byte[] arquivoMusica = musicaService.getArquivoMusica(id);

            if (arquivoMusica == null) {
                return ResponseEntity.badRequest().body("Música não encontrada com o ID: " + id);
            }

            // Configura os headers para download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(arquivoMusica.length);
            headers.setContentDispositionFormData("attachment", "musica_" + id + ".mp3");

            return new ResponseEntity<>(arquivoMusica, headers, HttpStatus.OK);

        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest().body("Música não encontrada com o ID: " + id);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Erro ao buscar arquivo de música: " + ex.getMessage());
        }
    }
}