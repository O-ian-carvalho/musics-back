package com.example.musicas.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="musicas")
public class Musica extends Base {

    @Temporal(TemporalType.DATE)
    private Date lancamento;

    private int duracaoEmSegundos;

    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany
    @JoinTable(
            name = "musica_playlist",
            joinColumns = @JoinColumn(name = "musica_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private Set<Playlist> playlists = new HashSet<>();

    // ✅ Construtor padrão
    public Musica() {}

    // ✅ Construtor com parâmetros
    public Musica(String nome, Date lancamento, int duracaoEmSegundos, Genero genero, Artista artista, Album album) {
        this.setNome(nome); // Vem da classe Base
        this.lancamento = lancamento;
        this.duracaoEmSegundos = duracaoEmSegundos;
        this.genero = genero;
        this.artista = artista;
        this.album = album;
    }

    // ✅ Getters e Setters
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



}
