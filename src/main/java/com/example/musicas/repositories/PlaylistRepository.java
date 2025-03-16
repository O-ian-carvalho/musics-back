package com.example.musicas.repositories;

import com.example.musicas.models.Album;
import com.example.musicas.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {
}
