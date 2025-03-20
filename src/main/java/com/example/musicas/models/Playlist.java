package com.example.musicas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="playlists")
public class Playlist extends Base {

    @ManyToMany(mappedBy = "playlists", fetch = FetchType.LAZY)
    private Set<Musica> musicas = new HashSet<>();

    public Set<Musica> getMusicas() {
        return musicas;
    }

    public void addMusica(Musica musica) {
        this.musicas.add(musica);
    }

    public void removeMusica(Musica musica) {
        this.musicas.remove(musica);
    }
}
