package com.psych.demo.service;

import com.psych.demo.data.MusicRepository;
import com.psych.demo.data.Scraper;
import com.psych.demo.model.TopSong;
import com.psych.demo.model.TopSongsPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.aggregation.SelectionOperators;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MusicService {

    @Autowired
    MusicRepository musicRepository;

    public void saveTop10Lyrics() throws IOException, InterruptedException {
       TopSongsPayload top10list = Scraper.getTop100List();
        for (TopSong row : top10list.getTopSongs()) {
            String lyricsStr = Scraper.getLyrics(row.getArtist(), row.getSongName());
            if(lyricsStr == null){
                Thread.sleep(10000);
                continue;
            }
            row.setLyrics(lyricsStr);
            musicRepository.save(row);
            Thread.sleep(10000);

        }
    }

    public TopSong testAzLyricMethod(String artistName, String songTitle) throws IOException, InterruptedException {

            String lyricsStr = Scraper.getLyrics(artistName,songTitle);
            System.out.println(lyricsStr);
            TopSong newSong = new TopSong();
            newSong.setArtist(artistName);
            newSong.setSongName(songTitle);
            newSong.setLyrics(lyricsStr);
           return musicRepository.save(newSong);
    }



    public TopSong addSong(@RequestBody TopSong topSong){
        return musicRepository.save(topSong);

    }

    public List<TopSong> getAllSongs() {
        return musicRepository.findAll();
    }

    public boolean findBySongName(String songName) {
        return musicRepository.findBySongName(songName);
    }
}
