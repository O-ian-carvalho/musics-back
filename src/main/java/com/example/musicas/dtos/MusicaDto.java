package com.example.musicas.dtos;

import java.util.Date;
import java.util.UUID;

public record MusicaDto(
        String nome,
        Date lancamento,
        int duracaoEmSegundos,
        UUID generoId,
        UUID artistaId,
        UUID albumId
) {}
