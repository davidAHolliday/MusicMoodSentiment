package com.psych.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.psych.demo.data.Scraper;
import com.psych.demo.dictionary.Mood;
import com.psych.demo.model.*;
import com.psych.demo.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.aggregation.SelectionOperators;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("Mood/v1")
public class Controller {

    @Autowired
    private MusicService musicService;


    //    @Bean
//    public void printToTerminal() throws IOException, InterruptedException {
//
//        System.out.println(returnTopTenArtist());
//    }


    @GetMapping("/songs/all")
    public List<TopSong> getAllSongsInDataBase(){
        return musicService.getAllSongs();
    }

    @DeleteMapping("/songs/deleteNull")
    public List<TopSong> deleteAllLyricNullFromMongo(){
        List<TopSong> findAllNull = musicService.getAllSongs().stream().filter(x-> x.getLyrics() == null).toList();
        return musicService.deleteAllSong(findAllNull);

    }

    @GetMapping("/songs/search/{songName}")
    public List<TopSong> getAllSongsInDataBase(@PathVariable String songName){
        return musicService.findBySongName(songName);
    }

    @PostMapping("/testMethod")
        public TopSongsPayload testMethod(@RequestBody TopSongsPayload payload) throws IOException, InterruptedException {
            List<TopSong> songToGetLyricsFor = payload.getTopSongs();
            TopSongsPayload response = new TopSongsPayload();
            List<TopSong> proccessedSong = new ArrayList<>();
            for(TopSong song : songToGetLyricsFor){
                List<TopSong> duplicate = musicService.findBySongName(song.getSongName());
                if(!duplicate.isEmpty()){
                    System.out.println("SONG ALREADY IN SYSTEM");
                    continue;
                }
               TopSong newSong =  musicService.testAzLyricMethod(song.getArtist(),song.getSongName().trim());
                Thread.sleep(10000);
               if(newSong.getLyrics() == null){
//                   System.out.println("Lyric could not be scraped");
//                   System.out.println("Press Enter to continue");
                   try{System.in.read();}
                   catch(Exception e){}


                   continue;
               }
                proccessedSong.add(newSong);
                Thread.sleep(10000);
//                System.out.println("Next Song");
//                System.out.println("Press Enter to continue");
                try{System.in.read();}
                catch(Exception e){}

            }
            response.setTopSongs(proccessedSong);
            return response;

    }

    @GetMapping("songs/Top100")
public TopSongsPayload getFullChart() throws IOException { return Scraper.getTop100List();
}
    @PostMapping("/songs/addSong")
    public TopSong addLyric(@RequestBody  TopSong songData){
        return musicService.addSong(songData);
    }

    @PostMapping("/songs/addSongBulk")
    public TopSongsPayload addLyric(@RequestBody  TopSongsPayload payload){
        TopSongsPayload songs = payload;
        for(TopSong song: songs.getTopSongs()){
            musicService.addSong(song);
        }
        return songs;
    }

    @GetMapping("/songs/findMoodOfTop10")
    public MoodResponse getAllSongs() throws IOException {
        List<TopSong> allSongs = musicService.getAllSongs();
        TopSongsPayload payload = new TopSongsPayload();
        payload.setTopSongs(allSongs);
        MoodResponse mood = createSongLyricArray(payload);

        return  mood;


    }



    public MoodResponse createSongLyricArray(TopSongsPayload payload) throws IOException {
        HashMap<String, String> songReport = new HashMap();
        for (TopSong song : payload.getTopSongs()) {
            List<String> processedLyrics = processLyrics(song.getLyrics());
            songReport.put(song.getSongName(), processedLyrics.toString());

            System.out.println("\n");
        }

        String mood;

        int pos = 0;
        int neg = 0;
        for (Map.Entry<String, String> song: songReport.entrySet() ){
            System.out.println(song.getKey() + " " + song.getValue() + " ");
            if(song.getValue().contains(":  Positive")){
                pos++;

            }else if( song.getValue().contains(":  Negative")){
                neg++;
            }else{}

        }

        if(pos>neg){
            System.out.println("The mood of the country is Positive");
            mood = "The mood of the country is Positive";
        }else if(neg>pos){
            System.out.println("The mood of the country is Negative");
            mood = "The mood of the country is Negative";

        }else{
            System.out.println("Im confused");
            mood="Im confused";

        }

        MoodResponse res = new MoodResponse();
        res.setData(songReport);
        res.setMood(mood);


        return res;
    }

    public List<String> processLyrics(String lyricPayload) throws IOException {
        String payload = lyricPayload;
        HashMap<String, Integer> positiveSentimentMap = new HashMap<>();
        HashMap<String, Integer> neutralSentimentMap = new HashMap<>();
        HashMap<String, Integer> negativeSentimentMap = new HashMap<>();

        String[] positiveWords = Mood.POSITIVE;

        // Negative Sentiment Indicators
        String[] negativeWords = Mood.NEGATIVE;

        // Neutral Sentiment Indicators
        String[] neutralWords = {
                ""
//                "It", "Is", "The", "In", "At",
//                "And", "Of", "On", "With", "As",
//                "A", "An", "To", "But", "Also"
        };

        for (String word : positiveWords) {
            positiveSentimentMap.put(word, 0);
        }
        for (String word : neutralWords) {
            neutralSentimentMap.put(word, 0);
        }
        for (String word : negativeWords) {
            negativeSentimentMap.put(word, 0);
        }

        String[] words = payload.split("[\\s\\p{Punct}&&[^']]+");
//        System.out.println(processSentiment(negativeSentimentMap, words));
        int neg = sentimentCount(negativeSentimentMap, words);

//        System.out.println(processSentiment(positiveSentimentMap, words));

        int pos = sentimentCount(positiveSentimentMap, words);

//        System.out.println(processSentiment(neutralSentimentMap, words));
        int neu = sentimentCount(neutralSentimentMap, words);

List<String> data = new ArrayList<>();
data.add(neg + " Negative");
data.add(pos + " Positive");
data.add(neu + " Neutral");
String mood = "";
        if (neg > pos && neg > neu) {
            mood = " Negative";
        } else if (pos > neg && pos > neu) {
            mood = " Positive";
        } else {
            mood = " Neutral";
        }
    data.add("General Mood is : " + mood);






        return data;
    }

    public  Integer sentimentCount(HashMap<String, Integer> sentimentMap, String[] payload) {
        int totalCount = 0;
        for (Map.Entry<String, Integer> word : sentimentMap.entrySet()) {
            String targetWord = word.getKey();
            int count = 0;

            for (String ele : payload) {
                if (ele.equalsIgnoreCase(targetWord)) {
                    count++;
                    sentimentMap.put(word.getKey(), count);
                }

            }
            totalCount = totalCount + count;
        }

        return totalCount;
    }

    public HashMap<String, Integer> processSentiment(HashMap<String, Integer> sentimentMap, String[] payload) {
        for (Map.Entry<String, Integer> word : sentimentMap.entrySet()) {
            String targetWord = word.getKey();
            int count = 0;

            for (String ele : payload) {
                if (ele.equalsIgnoreCase(targetWord)) {
                    count++;
                    sentimentMap.put(word.getKey(), count);
                }
            }
        }

        return sentimentMap;
    }
}















