package com.example.musicas.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Entity
@Table(name="albuns")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Album extends Base
{
    @ManyToOne(fetch = FetchType.EAGER)
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
