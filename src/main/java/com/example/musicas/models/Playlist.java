package com.example.musicas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="playlists")
public class Playlist extends Base
{

    @ManyToMany(mappedBy = "playlists", fetch = FetchType.LAZY)
    private Set<Musica> musicas;

    public Set<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(Set<Musica> musicas) {
        this.musicas = musicas;
    }



}
