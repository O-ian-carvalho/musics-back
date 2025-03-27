package com.example.musicas.repositories;

import com.example.musicas.models.Album;
import com.example.musicas.models.Playlist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {
    @EntityGraph(attributePaths = {"musicas", "musicas.artista"})
    List<Playlist> findAll();

    @EntityGraph(attributePaths = {"musicas", "musicas.artista"})
    Optional<Playlist> findById(UUID id);
}
