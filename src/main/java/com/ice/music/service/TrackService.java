package com.ice.music.service;

import com.ice.music.dto.TrackDTO;
import com.ice.music.entity.Track;

import java.util.List;

public interface TrackService {

    TrackDTO saveTrack(TrackDTO trackDTO);
    List<TrackDTO> getAllTracks(Long artistId);

}
