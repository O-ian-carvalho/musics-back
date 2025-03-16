package com.example.musicas.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Entity
@Table(name="albuns")
public class Album extends Base
{
    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private Set<Musica> musicas = new HashSet<>();

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Set<Musica> getMusicas() {
        return musicas;
    }

    public void addMusica(Musica musica)
    {
        musicas.add(musica);
    }


}
