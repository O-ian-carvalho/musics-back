package com.example.musicas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="artistas")
public class Artista extends Base
{
    @OneToMany(mappedBy = "artista", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Musica> musicas;

    @OneToMany(mappedBy = "artista", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Album> albuns;

    public Set<Musica> getMusicas() {
        return musicas;
    }


    public Set<Album> getAlbuns() {
        return albuns;
    }


}
