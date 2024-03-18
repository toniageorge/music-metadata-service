package com.ice.music.service;

import com.ice.music.dto.TrackDTO;
import com.ice.music.entity.Artist;
import com.ice.music.entity.Track;
import com.ice.music.repository.ArtistRepository;
import com.ice.music.repository.TrackRepository;
import com.ice.music.serviceImpl.TrackServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrackServiceImplTest {

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private TrackServiceImpl trackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveTrack_validTrackDTO_returnTrackDTO() {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setTitle("Test Track");
        trackDTO.setGenre("Test Genre");
        trackDTO.setLength(300);
        trackDTO.setArtistId(1L);

        Artist artist = new Artist();
        artist.setId(1L);

        Track savedTrack = new Track();
        savedTrack.setId(1L);
        savedTrack.setTitle(trackDTO.getTitle());
        savedTrack.setGenre(trackDTO.getGenre());
        savedTrack.setLength(trackDTO.getLength());
        savedTrack.setArtist(artist);

        when(artistRepository.findById(trackDTO.getArtistId())).thenReturn(Optional.of(artist));
        when(trackRepository.save(any(Track.class))).thenReturn(savedTrack);

        TrackDTO result = trackService.saveTrack(trackDTO);

        assertNotNull(result);
        assertEquals(savedTrack.getTitle(), result.getTitle());
        assertEquals(savedTrack.getGenre(), result.getGenre());
        assertEquals(savedTrack.getLength(), result.getLength());
        assertEquals(savedTrack.getArtist().getId(), result.getArtistId());
    }

    @Test
    void saveTrack_invalidArtistId_throwEntityNotFoundException() {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setArtistId(1L);

        when(artistRepository.findById(trackDTO.getArtistId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trackService.saveTrack(trackDTO));
    }

    @Test
    void getAllTracks_validArtistId_returnListOfTrackDTOs() {
        Long artistId = 1L;

        List<Track> tracks = new ArrayList<>();
        Track track1 = new Track();
        track1.setId(1L);
        track1.setTitle("Test Track 1");
        track1.setGenre("Test Genre 1");
        track1.setLength(300);
        track1.setArtist(new Artist());

        Track track2 = new Track();
        track2.setId(2L);
        track2.setTitle("Test Track 2");
        track2.setGenre("Test Genre 2");
        track2.setLength(400);
        track2.setArtist(new Artist());

        tracks.add(track1);
        tracks.add(track2);

        when(trackRepository.findByArtistId(artistId)).thenReturn(tracks);

        List<TrackDTO> result = trackService.getAllTracks(artistId);

        assertNotNull(result);
        assertEquals(tracks.size(), result.size());
        assertEquals(track1.getTitle(), result.get(0).getTitle());
        assertEquals(track1.getGenre(), result.get(0).getGenre());
        assertEquals(track1.getLength(), result.get(0).getLength());
        assertNull(result.get(0).getArtistId()); // Assuming you don't want to include artist ID in the DTO
        assertEquals(track2.getTitle(), result.get(1).getTitle());
        assertEquals(track2.getGenre(), result.get(1).getGenre());
        assertEquals(track2.getLength(), result.get(1).getLength());
        assertNull(result.get(1).getArtistId()); // Assuming you don't want to include artist ID in the DTO
    }

}