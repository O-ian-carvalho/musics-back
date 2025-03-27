package com.example.musicas.dtos;

import com.example.musicas.models.Album;
import com.example.musicas.models.Artista;
import com.example.musicas.models.Genero;

import java.util.Date;
import java.util.UUID;

public record MusicaResponseDto(
        UUID id,
        String nome,
        String url,
        Date lancamento,
        int duracaoEmSegundos,
        Genero genero,
        Artista artista,
        Album album
) {}
