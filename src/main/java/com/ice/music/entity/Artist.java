package com.ice.music.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name ="is_artist_of_the_day")
    private boolean isArtistOfTheDay;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Track> tracks;
}
