package com.ice.music.serviceImpl;

import com.ice.music.dto.ArtistDTO;
import com.ice.music.dto.TrackDTO;
import com.ice.music.entity.Artist;
import com.ice.music.entity.Track;
import com.ice.music.repository.ArtistRepository;
import com.ice.music.service.ArtistService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Long saveArtist(ArtistDTO artistDTO) {
        Artist artist = new Artist();
        artist.setName(artistDTO.getName());
        artist.setArtistOfTheDay(false);
        Artist savedArtist =  artistRepository.save(artist);
        return savedArtist.getId();
    }

    @Override
    public String updateArtist(String newName, Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with id: " +artistId));
        artist.setName(newName);
        artistRepository.save(artist);
        return artist.getName();
    }

    @Override
    public ArtistDTO fetchArtistOfTheDay() {
        Optional<Artist> artist = artistRepository.findByIsArtistOfTheDay(true);
       if( !artist.isPresent()){
           //for example if someone  calls before scheduler runs for the first time
           rotateArtistOfTheDay();
          artist = artistRepository.findByIsArtistOfTheDay(true);

       }
        return artist.isPresent()?convertToDto(artist.get()):null;
    }

    @Override
@Transactional
    public void rotateArtistOfTheDay() {
        //find current artist of day
        Optional<Artist> currentArtist = artistRepository.findByIsArtistOfTheDay(true);

        if (currentArtist.isPresent()) {
            //find maximum
            Optional<Artist> nextArtist = artistRepository.findFirstByIdGreaterThanOrderById(currentArtist.get().getId());
            if(nextArtist.isPresent()){
                artistRepository.updateIsArtistOfTheDayById(nextArtist.get().getId(), true);
            }
            else{
                // If there's no next artist, reset to the first one
                Optional<Artist> firstArtist = artistRepository.findFirstByOrderByIdAsc();
                firstArtist.ifPresent(artist -> artistRepository.updateIsArtistOfTheDayById(artist.getId(), true));
            }
            artistRepository.updateIsArtistOfTheDayById(currentArtist.get().getId(), false);

        }
        else {
            // Set the first artist to be the artist of the day if none is currently set
            Optional<Artist> firstArtist = artistRepository.findFirstByOrderByIdAsc();
            firstArtist.ifPresent(artist -> artistRepository.updateIsArtistOfTheDayById(artist.getId(), true));
        }
    }
    private ArtistDTO convertToDto(Artist artist) {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName(artist.getName());
        artistDTO.setId(artist.getId());
        return  artistDTO;
    }

}
