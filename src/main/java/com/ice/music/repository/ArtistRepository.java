package com.ice.music.repository;

import com.ice.music.entity.Artist;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByIsArtistOfTheDay(boolean isArtistOfTheDay);

    // Method to update only the isArtistOfTheDay field by ID
    @Modifying
    @Transactional
    @Query("UPDATE Artist a SET a.isArtistOfTheDay = :isArtistOfTheDay WHERE a.id = :id")
    void updateIsArtistOfTheDayById(Long id, boolean isArtistOfTheDay);

    Optional<Artist> findFirstByIdGreaterThanOrderById(Long id);

    Optional<Artist> findFirstByOrderByIdAsc();
}

