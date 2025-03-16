package com.example.musicas.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="musicas")
public class Musica extends Base
{
    private Date Lancamento;
    private int DuracaoEmSegundos;
    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;
    @ManyToOne
    @JoinColumn(name = "artista_id")
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
    private Set<Playlist> playlists;


    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Date getLancamento() {
        return Lancamento;
    }

    public void setLancamento(Date lancamento) {
        Lancamento = lancamento;
    }

    public int getDuracaoEmSegundos() {
        return DuracaoEmSegundos;
    }

    public void setDuracaoEmSegundos(int duracaoEmSegundos) {
        DuracaoEmSegundos = duracaoEmSegundos;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }
}
