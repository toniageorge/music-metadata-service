package com.ice.music.controller;

import com.ice.music.dto.ArtistDTO;
import com.ice.music.dto.TrackDTO;
import com.ice.music.entity.Track;
import com.ice.music.service.ArtistService;
import com.ice.music.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musics")
public class MusicController {

    @Autowired
    TrackService trackService;
    @Autowired
    ArtistService artistService;

    @PostMapping("/tracks")
    public ResponseEntity<TrackDTO> addTrack(@RequestBody TrackDTO trackDTO){
        TrackDTO trackDTOSaved = trackService.saveTrack(trackDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(trackDTOSaved);

    }
    @PostMapping("/artists")
    public ResponseEntity<Long> addTrack(@RequestBody ArtistDTO artistDTO){
        Long artistId = artistService.saveArtist(artistDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(artistId);

    }

    @PutMapping("artist/{artistId}")
    public ResponseEntity<Void> updateArtistName(@PathVariable Long artistId, @RequestParam String newName){
        artistService.updateArtist(newName,artistId);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/artists/{artistId}/tracks")
    public ResponseEntity<List<TrackDTO>> getTracks(@PathVariable Long artistId){
     List<TrackDTO> tracklist = trackService.getAllTracks(artistId);
     return ResponseEntity.ok(tracklist);

    }

    @GetMapping("/artist-of-the-day")
    public  ResponseEntity<ArtistDTO> fetchArtistOfTheDay(){
    ArtistDTO artistDTO = artistService.fetchArtistOfTheDay();
    return  ResponseEntity.ok(artistDTO);

    }
}
