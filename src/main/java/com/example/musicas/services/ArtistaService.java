package com.example.musicas.services;

import com.example.musicas.dtos.ArtistaDto;
import com.example.musicas.models.Artista;
import com.example.musicas.repositories.ArtistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ArtistaService {
    private final ArtistaRepository artistaRepository;
    public ArtistaService(ArtistaRepository artistaRepository)
    {
        this.artistaRepository = artistaRepository;
    }

    @Transactional
    public Artista add(ArtistaDto artistaDto)
    {
        if(artistaDto.nome().isEmpty()) return null;
        Artista artista = new Artista();
        artista.setNome(artistaDto.nome());
        return artistaRepository.save(artista);
    }
}
