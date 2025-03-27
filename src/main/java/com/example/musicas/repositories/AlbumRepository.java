package com.example.musicas.repositories;

import com.example.musicas.models.Album;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {
    @EntityGraph(attributePaths = {"artista", "musicas"})
    List<Album> findAll();

}
