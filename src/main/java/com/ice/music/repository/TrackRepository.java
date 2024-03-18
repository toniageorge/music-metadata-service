package com.ice.music.repository;

import com.ice.music.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {

    List<Track>  findByArtistId(Long artistId);

}
