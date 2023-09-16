package com.psych.demo.model;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class SongsResponse {

    private  String error;

    @Getter
    private List<TopSong> topSongs;

    public void setTopSongs(List<TopSong> topSongs) {
        this.topSongs = topSongs;
    }

    // Getters and setters for the fields

    // Constructors
}
