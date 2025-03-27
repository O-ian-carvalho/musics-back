package com.example.musicas.repositories;

import com.example.musicas.models.Album;
import com.example.musicas.models.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MusicaRepository extends JpaRepository<Musica, UUID> {

    @Query("SELECT m FROM Musica m " +
            "WHERE (:titulo IS NULL OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :titulo, '%'))) " +
            "AND (:artista IS NULL OR LOWER(m.artista.nome) LIKE LOWER(CONCAT('%', :artista, '%'))) " +
            "AND (:album IS NULL OR LOWER(m.album.nome) LIKE LOWER(CONCAT('%', :album, '%'))) " +
            "AND (:genero IS NULL OR LOWER(m.genero.nome) LIKE LOWER(CONCAT('%', :genero, '%'))) " +
            "AND (:ano IS NULL OR FUNCTION('YEAR', m.lancamento) = :ano)")
    List<Musica> buscarPorFiltros(
            @Param("titulo") String titulo,
            @Param("artista") String artista,
            @Param("album") String album,
            @Param("genero") String genero,
            @Param("ano") Integer ano);

}
