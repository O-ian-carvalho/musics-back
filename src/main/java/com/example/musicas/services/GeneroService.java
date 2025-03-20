package com.example.musicas.services;

import com.example.musicas.dtos.GeneroDto;
import com.example.musicas.models.Genero;
import com.example.musicas.repositories.GeneroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GeneroService {
    private final GeneroRepository generoRepository;

    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Transactional
    public Genero add(GeneroDto generoDto) {
        if (generoDto.nome().isEmpty()) return null;
        Genero genero = new Genero();
        genero.setNome(generoDto.nome());
        return generoRepository.save(genero);
    }

    public Optional<Genero> get(UUID id) {
        return generoRepository.findById(id);
    }

    public List<Genero> getAll() {
        return generoRepository.findAll();
    }

    @Transactional
    public Genero update(UUID id, GeneroDto generoDto) {
        return generoRepository.findById(id).map(genero -> {
            genero.setNome(generoDto.nome());
            return generoRepository.save(genero);
        }).orElse(null);
    }

    @Transactional
    public boolean delete(UUID id) {
        return generoRepository.findById(id).map(genero -> {
            generoRepository.delete(genero);
            return true;
        }).orElse(false);
    }
}
