package com.ice.music.service;

import com.ice.music.dto.ArtistDTO;
import com.ice.music.entity.Artist;

public interface ArtistService {

    Long  saveArtist(ArtistDTO artistDTO);

    String updateArtist(String newName,Long Id);

    ArtistDTO fetchArtistOfTheDay();

    void rotateArtistOfTheDay();
}
