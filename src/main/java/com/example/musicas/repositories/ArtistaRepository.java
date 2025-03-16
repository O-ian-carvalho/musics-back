package com.example.musicas.repositories;

import com.example.musicas.models.Album;
import com.example.musicas.models.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistaRepository extends JpaRepository<Artista, UUID> {
}
