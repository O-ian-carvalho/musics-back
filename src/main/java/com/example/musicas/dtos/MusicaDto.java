package com.example.musicas.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Schema()
public record MusicaDto(
        String nome,
        Date lancamento,
        int duracaoEmSegundos,
        UUID generoId,
        UUID artistaId,
        UUID albumId
) {
    @JsonCreator
    public MusicaDto {}
}
