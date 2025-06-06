package com.example.musicas.controllers;

import com.example.musicas.dtos.ErrorResponse;
import com.example.musicas.dtos.PlaylistDto;
import com.example.musicas.models.Musica;
import com.example.musicas.models.Playlist;
import com.example.musicas.services.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody PlaylistDto playlistDto, WebRequest request) {
        try {
            Playlist playlist = playlistService.createPlaylist(playlistDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
        } catch (IllegalArgumentException ex) {
            return buildErrorResponse("Dados inválidos: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        } catch (Exception ex) {
            return buildErrorResponse("Erro ao criar playlist: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        }
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylistById(@PathVariable UUID id, WebRequest request) {
        try {
            Optional<Playlist> playlist = playlistService.getPlaylistById(id);
            if (playlist.isPresent()) {
                return ResponseEntity.ok(playlist.get());
            } else {
                return buildErrorResponse("Playlist não encontrada com o ID: " + id, HttpStatus.NOT_FOUND, request);
            }
        } catch (Exception ex) {
            return buildErrorResponse("Erro ao buscar playlist: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlaylist(@PathVariable UUID id, @RequestBody PlaylistDto playlistDto, WebRequest request) {
        try {
            Playlist updated = playlistService.updatePlaylist(id, playlistDto);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException ex) {
            return buildErrorResponse("Playlist não encontrada com o ID: " + id, HttpStatus.NOT_FOUND, request);
        } catch (IllegalArgumentException ex) {
            return buildErrorResponse("Dados inválidos: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        } catch (Exception ex) {
            return buildErrorResponse("Erro ao atualizar playlist: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable UUID id, WebRequest request) {
        try {
            playlistService.deletePlaylist(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return buildErrorResponse("Playlist não encontrada com o ID: " + id, HttpStatus.NOT_FOUND, request);
        } catch (Exception ex) {
            return buildErrorResponse("Erro ao deletar playlist: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        }
    }

    @PostMapping("/{playlistId}/musicas/{musicaId}")
    public ResponseEntity<?> addMusicaToPlaylist(@PathVariable UUID playlistId, @PathVariable UUID musicaId, WebRequest request) {
        try {
            Playlist playlist = playlistService.addMusicaToPlaylist(playlistId, musicaId);
            return ResponseEntity.ok(playlist);
        } catch (NoSuchElementException ex) {
            return buildErrorResponse("Playlist ou música não encontrada.", HttpStatus.NOT_FOUND, request);
        } catch (IllegalArgumentException ex) {
            return buildErrorResponse("Dados inválidos: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        } catch (Exception ex) {
            return buildErrorResponse("Erro ao adicionar música à playlist: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        }
    }

    @DeleteMapping("/{playlistId}/musicas/{musicaId}")
    public ResponseEntity<?> removeMusicaFromPlaylist(@PathVariable UUID playlistId, @PathVariable UUID musicaId, WebRequest request) {
        try {
            Playlist playlist = playlistService.removeMusicaFromPlaylist(playlistId, musicaId);
            return ResponseEntity.ok(playlist);
        } catch (NoSuchElementException ex) {
            return buildErrorResponse("Playlist ou música não encontrada.", HttpStatus.NOT_FOUND, request);
        } catch (IllegalArgumentException ex) {
            return buildErrorResponse("Dados inválidos: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        } catch (Exception ex) {
            return buildErrorResponse("Erro ao remover música da playlist: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request);
        }
    }

    // Método utilitário para criar ErrorResponse
    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setStatus(status.value());
        errorResponse.setError(status.getReasonPhrase());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(status).body(errorResponse);
    }

    @GetMapping("/{playlistId}/musicas-ordenadas")
    @Operation(
            summary = "Buscar músicas de uma playlist ordenadas",
            description = "Retorna todas as músicas de uma playlist específica ordenadas pelo critério escolhido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Músicas encontradas e ordenadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Critério de ordenação inválido"),
            @ApiResponse(responseCode = "404", description = "Playlist não encontrada")
    })
    public ResponseEntity<?> getMusicasOrdenadas(
            @Parameter(description = "ID da playlist", required = true)
            @PathVariable UUID playlistId,

            @Parameter(
                    description = "Critério de ordenação das músicas",
                    required = true,
                    schema = @Schema(
                            type = "string",
                            allowableValues = {"titulo", "artista", "duracao"},
                            example = "titulo"
                    )
            )
            @RequestParam String criterio,

            WebRequest request) {
        try {
            List<Musica> musicasOrdenadas = playlistService.getMusicasOrdenadas(playlistId, criterio);
            return ResponseEntity.ok(musicasOrdenadas);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id da playlist inválido");        }
    }
}