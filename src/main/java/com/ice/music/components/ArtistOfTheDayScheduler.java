package com.ice.music.components;

import com.ice.music.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ArtistOfTheDayScheduler {

    @Autowired
    private ArtistService artistService;
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void rotateArtistOfTheDay() {
        artistService.rotateArtistOfTheDay();
    }


}
