package com.example.musicas.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="playlists")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Playlist extends Base {

    @ManyToMany(mappedBy = "playlists", fetch = FetchType.EAGER)
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
