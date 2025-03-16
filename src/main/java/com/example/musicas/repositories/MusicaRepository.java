package com.example.musicas.repositories;

import com.example.musicas.models.Album;
import com.example.musicas.models.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicaRepository extends JpaRepository<Musica, UUID> {
}
