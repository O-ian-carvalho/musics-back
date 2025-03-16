package com.example.musicas.repositories;

import com.example.musicas.models.Album;
import com.example.musicas.models.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeneroRepository extends JpaRepository<Genero, UUID> {
}
