package com.example.musicas.services;

import com.example.musicas.dtos.PlaylistDto;
import com.example.musicas.models.Musica;
import com.example.musicas.models.Playlist;
import com.example.musicas.repositories.MusicaRepository;
import com.example.musicas.repositories.PlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final MusicaRepository musicaRepository;

    public PlaylistService(PlaylistRepository playlistRepository, MusicaRepository musicaRepository) {
        this.playlistRepository = playlistRepository;
        this.musicaRepository = musicaRepository;
    }

    @Transactional
    public Playlist createPlaylist(PlaylistDto playlistDto) {
        Playlist playlist = new Playlist();
        playlist.setNome(playlistDto.nome());

        return playlistRepository.save(playlist);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistById(UUID id) {
        return playlistRepository.findById(id);
    }

    @Transactional
    public Playlist updatePlaylist(UUID id, PlaylistDto playlistDto) {
        return playlistRepository.findById(id).map(playlist -> {
            playlist.setNome(playlistDto.nome());

            return playlistRepository.save(playlist);
        }).orElseThrow(() -> new RuntimeException("Playlist não encontrada"));
    }

    @Transactional
    public void deletePlaylist(UUID id) {
        playlistRepository.deleteById(id);
    }

    @Transactional
    public Playlist addMusicaToPlaylist(UUID playlistId, UUID musicaId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist não encontrada"));

        Musica musica = musicaRepository.findById(musicaId)
                .orElseThrow(() -> new RuntimeException("Música não encontrada"));

        playlist.addMusica(musica);
        musica.getPlaylists().add(playlist); // Garante a persistência bidirecional

        return playlist;
    }

    @Transactional
    public Playlist removeMusicaFromPlaylist(UUID playlistId, UUID musicaId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist não encontrada"));

        Musica musica = musicaRepository.findById(musicaId)
                .orElseThrow(() -> new RuntimeException("Música não encontrada"));

        playlist.removeMusica(musica);
        return playlistRepository.save(playlist);
    }


    public List<Musica> getMusicasOrdenadas(UUID playlistId, String criterio) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NoSuchElementException("Playlist não encontrada com o ID: " + playlistId));

        List<Musica> musicas = new ArrayList<>(playlist.getMusicas());

        switch (criterio.toLowerCase()) {
            case "titulo" -> musicas.sort(Comparator.comparing(Musica::getNome, String.CASE_INSENSITIVE_ORDER));
            case "artista" -> musicas.sort(Comparator.comparing(m -> m.getArtista().getNome(), String.CASE_INSENSITIVE_ORDER));
            case "duracao" -> musicas.sort(Comparator.comparingInt(Musica::getDuracaoEmSegundos));
            default -> throw new IllegalArgumentException("Critério de ordenação inválido");
        }

        return musicas;
    }
}
