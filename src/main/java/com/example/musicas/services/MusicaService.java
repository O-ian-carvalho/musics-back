package com.example.musicas.services;

import com.example.musicas.dtos.MusicaDto;
import com.example.musicas.dtos.MusicaResponseDto;
import com.example.musicas.models.Album;
import com.example.musicas.models.Artista;
import com.example.musicas.models.Genero;
import com.example.musicas.models.Musica;
import com.example.musicas.repositories.AlbumRepository;
import com.example.musicas.repositories.ArtistaRepository;
import com.example.musicas.repositories.GeneroRepository;
import com.example.musicas.repositories.MusicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MusicaService {
    private final MusicaRepository musicaRepository;
    private final GeneroRepository generoRepository;
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;

    public MusicaService(MusicaRepository musicaRepository, GeneroRepository generoRepository,
                         ArtistaRepository artistaRepository, AlbumRepository albumRepository) {
        this.musicaRepository = musicaRepository;
        this.generoRepository = generoRepository;
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
    }

    @Transactional
    public MusicaResponseDto add(MusicaDto dto, String url) {
        Optional<Genero> genero = generoRepository.findById(dto.generoId());
        Optional<Artista> artista = artistaRepository.findById(dto.artistaId());
        Optional<Album> album = Optional.empty();;
        if(dto.albumId() != null)
        {
            album = albumRepository.findById(dto.albumId());
        }


        if (genero.isEmpty() || artista.isEmpty()) {
            throw new RuntimeException("Gênero ou Artista não encontrado!");
        }

        Musica musica = new Musica(dto.nome(), dto.lancamento(), dto.duracaoEmSegundos(),
                genero.get(), artista.get(), album.orElse(null), url);



        musica = musicaRepository.save(musica);

        return convertToResponseDto(musica);
    }

    public List<MusicaResponseDto> getAll() {
        return musicaRepository.findAll().stream().map(this::convertToResponseDto).toList();
    }

    public Optional<MusicaResponseDto> getById(UUID id) {
        return musicaRepository.findById(id).map(this::convertToResponseDto);
    }

    @Transactional
    public Optional<MusicaResponseDto> update(UUID id, MusicaDto dto) {
        Optional<Musica> musicaOptional = musicaRepository.findById(id);

        if (musicaOptional.isEmpty()) return Optional.empty();

        Musica musica = musicaOptional.get();
        musica.setNome(dto.nome());
        musica.setLancamento(dto.lancamento());
        musica.setDuracaoEmSegundos(dto.duracaoEmSegundos());

        Optional<Genero> genero = generoRepository.findById(dto.generoId());
        Optional<Artista> artista = artistaRepository.findById(dto.artistaId());
        Optional<Album> album = albumRepository.findById(dto.albumId());

        if (genero.isEmpty() || artista.isEmpty()) {
            throw new RuntimeException("Gênero ou Artista não encontrado!");
        }

        musica.setGenero(genero.get());
        musica.setArtista(artista.get());
        musica = musicaRepository.save(musica);

        return Optional.of(convertToResponseDto(musica));
    }

    @Transactional
    public boolean delete(UUID id) {
        if (musicaRepository.existsById(id)) {
            musicaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<MusicaResponseDto> buscarMusicas(String titulo, String artista, String album, String genero, Integer ano) {
        List<Musica> musicas = musicaRepository.buscarPorFiltros(titulo, artista, album, genero, ano);
        return musicas.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    private MusicaResponseDto convertToResponseDto(Musica musica) {
        return new MusicaResponseDto(
                musica.getId(),
                musica.getNome(),
                musica.getUrl(),
                musica.getLancamento(),
                musica.getDuracaoEmSegundos(),
                musica.getGenero(),
                musica.getArtista(),
                musica.getAlbum()
        );
    }


    private static final String DIRETORIO_UPLOAD = "C:\\Users\\carva\\OneDrive\\Documentos\\Estudos\\Faculdade\\OOP\\MusicsFront\\src\\assets\\musicas"; // Define o diretório de upload

    public String saveArchive(MultipartFile arquivo) throws IOException {
        Path caminhoDiretorio = Paths.get(DIRETORIO_UPLOAD);

        if (!Files.exists(caminhoDiretorio)) {
            Files.createDirectories(caminhoDiretorio);
        }

        String nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
        Path caminhoArquivo = caminhoDiretorio.resolve(nomeArquivo);

        arquivo.transferTo(caminhoArquivo.toFile());

        return nomeArquivo;
    }
}
