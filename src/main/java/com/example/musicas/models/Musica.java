package com.example.musicas.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="musicas")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Musica extends Base {
    @Temporal(TemporalType.DATE)
    private Date lancamento;
    private String url;
    private int duracaoEmSegundos;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "musica_playlist",
            joinColumns = @JoinColumn(name = "musica_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private Set<Playlist> playlists = new HashSet<>();

    public Musica() {}


    public Musica(String nome, Date lancamento, int duracaoEmSegundos, Genero genero, Artista artista, Album album, String url) {
        this.setNome(nome); // Vem da classe Base
        this.lancamento = lancamento;
        this.duracaoEmSegundos = duracaoEmSegundos;
        this.genero = genero;
        this.artista = artista;
        this.album = album;
        this.url = url;
    }

    @Lob
    @Column(name = "arquivo_musica", columnDefinition = "LONGBLOB")
    @JsonIgnore
    private byte[] arquivoMusica;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setAlbum(Album album) {
        this.album = album;
    }
    public Date getLancamento() {
        return lancamento;
    }
    public void setLancamento(Date lancamento) {
        this.lancamento = lancamento;
    }
    public int getDuracaoEmSegundos() {
        return duracaoEmSegundos;
    }
    public void setDuracaoEmSegundos(int duracaoEmSegundos) {
        this.duracaoEmSegundos = duracaoEmSegundos;
    }
    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    public Artista getArtista() {
        return artista;
    }
    public void setArtista(Artista artista) {
        this.artista = artista;
    }
    public Album getAlbum() {
        return album;
    }
    public Set<Playlist> getPlaylists() {
        return playlists;
    }


    public byte[] getArquivoMusica() {
        return arquivoMusica;
    }

    public void setArquivoMusica(byte[] arquivoMusica) {
        this.arquivoMusica = arquivoMusica;
    }
}
