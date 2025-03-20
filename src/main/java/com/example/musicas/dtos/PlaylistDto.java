package com.example.musicas.dtos;

import java.util.Set;
import java.util.UUID;

public record PlaylistDto(
        String nome,
        Set<UUID> musicasIds
) {}
