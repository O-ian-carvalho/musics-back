package com.example.musicas.services;

import com.example.musicas.dtos.AlbumDto;
import com.example.musicas.models.Album;
import com.example.musicas.models.Artista;
import com.example.musicas.repositories.AlbumRepository;
import com.example.musicas.repositories.ArtistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistaRepository artistaRepository;

    public AlbumService(AlbumRepository albumRepository, ArtistaRepository artistaRepository) {
        this.albumRepository = albumRepository;
        this.artistaRepository = artistaRepository;
    }

    // Criar um novo álbum
    @Transactional
    public Album add(AlbumDto albumDto) {
        Optional<Artista> artista = artistaRepository.findById(albumDto.artistaId());
        if (artista.isEmpty()) {
            throw new RuntimeException("Artista não encontrado!");
        }

        Album album = new Album();
        album.setNome(albumDto.nome());
        album.setArtista(artista.get());

        return albumRepository.save(album);
    }

    // Buscar álbum por ID
    public Optional<Album> getById(UUID id) {
        return albumRepository.findById(id);
    }

    // Buscar todos os álbuns
    public List<Album> getAll() {
        return albumRepository.findAll();
    }

    // Atualizar álbum
    @Transactional
    public Optional<Album> update(UUID id, AlbumDto albumDto) {
        Optional<Album> albumOptional = albumRepository.findById(id);
        if (albumOptional.isEmpty()) {
            return Optional.empty();
        }

        Album album = albumOptional.get();
        album.setNome(albumDto.nome());

        Optional<Artista> artista = artistaRepository.findById(albumDto.artistaId());
        artista.ifPresent(album::setArtista);

        return Optional.of(albumRepository.save(album));
    }

    // Deletar álbum
    @Transactional
    public boolean delete(UUID id) {
        if (albumRepository.existsById(id)) {
            albumRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
