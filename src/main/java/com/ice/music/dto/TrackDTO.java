package com.ice.music.dto;

import lombok.Data;

@Data
public class TrackDTO {
    private Long id;
    private String title;
    private String genre;
    private int length;
    private Long artistId;


}
