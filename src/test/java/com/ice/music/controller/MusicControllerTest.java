package com.ice.music.controller;
import com.ice.music.dto.ArtistDTO;
import com.ice.music.dto.TrackDTO;
import com.ice.music.service.ArtistService;
import com.ice.music.service.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MusicControllerTest {

    @Mock
    private TrackService trackService;

    @Mock
    private ArtistService artistService;

    @InjectMocks
    private MusicController musicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTrack_shouldReturnCreatedStatus() {
        TrackDTO trackDTO = new TrackDTO();
        ResponseEntity<TrackDTO> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(trackDTO);

        when(trackService.saveTrack(any(TrackDTO.class))).thenReturn(trackDTO);

        ResponseEntity<TrackDTO> actualResponse = musicController.addTrack(trackDTO);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(trackService, times(1)).saveTrack(trackDTO);
    }

    @Test
    void addArtist_shouldReturnCreatedStatus() {
        ArtistDTO artistDTO = new ArtistDTO();
        Long artistId = 1L;
        ResponseEntity<Long> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(artistId);

        when(artistService.saveArtist(any(ArtistDTO.class))).thenReturn(artistId);

        ResponseEntity<Long> actualResponse = musicController.addTrack(artistDTO);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(artistService, times(1)).saveArtist(artistDTO);
    }

    @Test
    void updateArtistName_shouldReturnOkStatus() {
        Long artistId = 1L;
        String newName = "New Artist Name";
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        when(artistService.updateArtist(newName, artistId)).thenReturn(newName);

        ResponseEntity<Void> actualResponse = musicController.updateArtistName(artistId, newName);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());

        verify(artistService, times(1)).updateArtist(newName, artistId);
    }

    @Test
    void getTracks_shouldReturnListOfTracks() {
        Long artistId = 1L;
        List<TrackDTO> expectedTrackList = new ArrayList<>();
        ResponseEntity<List<TrackDTO>> expectedResponse = ResponseEntity.ok(expectedTrackList);

        when(trackService.getAllTracks(artistId)).thenReturn(expectedTrackList);

        ResponseEntity<List<TrackDTO>> actualResponse = musicController.getTracks(artistId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(trackService, times(1)).getAllTracks(artistId);
    }

    @Test
    void fetchArtistOfTheDay_shouldReturnArtistDTO() {
        ArtistDTO expectedArtistDTO = new ArtistDTO();
        ResponseEntity<ArtistDTO> expectedResponse = ResponseEntity.ok(expectedArtistDTO);

        when(artistService.fetchArtistOfTheDay()).thenReturn(expectedArtistDTO);

        ResponseEntity<ArtistDTO> actualResponse = musicController.fetchArtistOfTheDay();

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(artistService, times(1)).fetchArtistOfTheDay();
    }
}