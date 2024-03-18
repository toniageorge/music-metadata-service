package com.ice.music.service;

import com.ice.music.dto.ArtistDTO;
import com.ice.music.entity.Artist;
import com.ice.music.repository.ArtistRepository;
import com.ice.music.serviceImpl.ArtistServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class ArtistServiceImplTest {
    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistServiceImpl artistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveArtist_shouldReturnArtistId() {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName("Test Artist");

        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName(artistDTO.getName());

        when(artistRepository.save(any(Artist.class))).thenReturn(artist);

        Long savedArtistId = artistService.saveArtist(artistDTO);

        assertNotNull(savedArtistId);
        assertEquals(artist.getId(), savedArtistId);

        verify(artistRepository, times(1)).save(any(Artist.class));
    }
    @Test
    void updateArtist_shouldUpdateArtistName() {
        Long artistId = 1L;
        String newName = "Updated Artist Name";

        Artist artist = new Artist();
        artist.setId(artistId);

        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        when(artistRepository.save(any(Artist.class))).thenReturn(artist);

        String updatedName = artistService.updateArtist(newName, artistId);

        assertEquals(newName, updatedName);

        verify(artistRepository, times(1)).findById(artistId);
        verify(artistRepository, times(1)).save(any(Artist.class));
    }
    @Test
    void updateArtist_shouldThrowEntityNotFoundException_whenArtistNotFound() {
        Long artistId = 1L;
        String newName = "Updated Artist Name";

        when(artistRepository.findById(artistId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> artistService.updateArtist(newName, artistId));

        verify(artistRepository, times(1)).findById(artistId);
        verify(artistRepository, never()).save(any(Artist.class));
    }
    @Test
    void fetchArtistOfTheDay_artistExists_returnArtistDTO() {
        // Mock artist data
        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("Test Artist");
        artist.setArtistOfTheDay(true);
        when(artistRepository.findByIsArtistOfTheDay(true)).thenReturn(Optional.of(artist));
        ArtistDTO result = artistService.fetchArtistOfTheDay();
        assertEquals(artist.getId(), result.getId());
        assertEquals(artist.getName(), result.getName());
    }

    @Test
    void fetchArtistOfTheDay_noArtistExists_rotateAndReturnNewArtistDTO() {
        when(artistRepository.findByIsArtistOfTheDay(true)).thenReturn(Optional.empty());
        artistService.fetchArtistOfTheDay();
        verify(artistRepository, times(3)).findByIsArtistOfTheDay(true);
    }

    @Test
    void rotateArtistOfTheDay_artistExists_rotateToNextArtist() {
        Artist currentArtist = new Artist();
        currentArtist.setId(1L);
        currentArtist.setArtistOfTheDay(true);

        Artist nextArtist = new Artist();
        nextArtist.setId(2L);
        when(artistRepository.findByIsArtistOfTheDay(true)).thenReturn(Optional.of(currentArtist));
        when(artistRepository.findFirstByIdGreaterThanOrderById(currentArtist.getId())).thenReturn(Optional.of(nextArtist));

        artistService.rotateArtistOfTheDay();

        verify(artistRepository, times(1)).updateIsArtistOfTheDayById(nextArtist.getId(), true);
        verify(artistRepository, times(1)).updateIsArtistOfTheDayById(currentArtist.getId(), false);
    }

}
