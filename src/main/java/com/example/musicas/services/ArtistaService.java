package com.example.musicas.services;

import com.example.musicas.dtos.ArtistaDto;
import com.example.musicas.models.Artista;
import com.example.musicas.repositories.ArtistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtistaService {
    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    @Transactional
    public Artista add(ArtistaDto artistaDto) {
        if (artistaDto.nome().isEmpty()) return null;
        Artista artista = new Artista();
        artista.setNome(artistaDto.nome());
        return artistaRepository.save(artista);
    }

    @Transactional(readOnly = true)
    public Optional<Artista> get(UUID id) {
        return artistaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Artista> getAll() {
        return artistaRepository.findAll();
    }

    @Transactional
    public Artista update(UUID id, ArtistaDto artistaDto) {
        Optional<Artista> artistaOptional = artistaRepository.findById(id);
        if (artistaOptional.isPresent()) {
            Artista artista = artistaOptional.get();
            artista.setNome(artistaDto.nome());
            return artistaRepository.save(artista);
        }
        return null; // Ou lançar uma exceção personalizada
    }

    @Transactional
    public boolean delete(UUID id) {
        if (artistaRepository.existsById(id)) {
            artistaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
