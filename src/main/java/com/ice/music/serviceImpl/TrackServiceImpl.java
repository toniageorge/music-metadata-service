package com.ice.music.serviceImpl;

import com.ice.music.dto.TrackDTO;
import com.ice.music.entity.Artist;
import com.ice.music.entity.Track;
import com.ice.music.repository.ArtistRepository;
import com.ice.music.repository.TrackRepository;
import com.ice.music.service.TrackService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;


    public TrackServiceImpl(TrackRepository trackRepository, ArtistRepository artistRepository) {

        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public TrackDTO saveTrack(TrackDTO trackDTO ) {
        Track trackToSave = new Track();
        Artist artist = artistRepository.findById(trackDTO.getArtistId())
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with id: " + trackDTO.getArtistId()));

        trackToSave.setTitle(trackDTO.getTitle());
        trackToSave.setGenre(trackDTO.getGenre());
        trackToSave.setLength(trackDTO.getLength());
        trackToSave.setArtist(artist);
        trackRepository.save(trackToSave );
        return convertToDto(trackToSave);
    }

    @Override
    public List<TrackDTO> getAllTracks(Long artistId) {
        List<Track> tracks =  trackRepository.findByArtistId(artistId);
        return tracks.stream()
                .map(track -> convertToDto(track))
                .collect(Collectors.toList());
    }

    // Convert Track entity to TrackDTO
    private TrackDTO convertToDto(Track track) {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setGenre(track.getGenre());
        trackDTO.setTitle(track.getTitle());
        trackDTO.setLength(track.getLength());
        trackDTO.setArtistId(track.getArtist().getId());
        return trackDTO;
    }
}
