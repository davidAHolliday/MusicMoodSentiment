package com.psych.demo.model;

import lombok.Data;

import java.util.List;
@Data
public class TopSongsPayload {
    private List<TopSong> topSongs;

    public List<TopSong> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(List<TopSong> topSongs) {
        this.topSongs = topSongs;
    }

    // Getters and setters for the fields

    // Constructors
}
