package com.psych.demo.data;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.psych.demo.model.TopSong;
import com.psych.demo.model.TopSongsPayload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class Scraper
{


    public static String getLyrics(String artistSearch, String songSearch) throws IOException {
        System.out.println("Looking for the following artist and song " + artistSearch + " : " + songSearch);

        // Create a WebClient and disable JavaScript
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);

        // Build the URL for the artist's page
        String letter = String.valueOf(artistSearch.toLowerCase().charAt(0));
        String artistPageUrl = "https://www.azlyrics.com/" + letter + ".html";

        // Navigate to the artist's page
        HtmlPage artistPage = webClient.getPage(artistPageUrl);

        // Find the anchor containing the artist's name
        HtmlAnchor artistAnchor = artistPage.getFirstByXPath("//a[contains(text(),'" + artistSearch + "')]");

        //If artist is retun null try again with modifed artist name
        if (artistAnchor == null){
            artistAnchor = artistPage.getFirstByXPath("//a[contains(text(),'" + artistSearch.substring(0,5) + "')]");
        }
        if (artistAnchor != null) {
            // Click on the artist's name to go to their page
            HtmlPage artistProfilePage = artistAnchor.click();
System.out.println(songSearch);
            // Search for the song title on the artist's page
            HtmlAnchor songAnchor = artistProfilePage.getFirstByXPath("//a[contains(text(),'" + songSearch + "')]");
            if (songAnchor != null) {
                // Click on the song title to go to the lyrics page
                HtmlPage lyricPage = songAnchor.click();
                System.out.println("Lyric Page");

                // Find the div element containing the lyrics
                HtmlDivision divElement = lyricPage.getFirstByXPath(".//div[@class='col-xs-12 col-lg-8 text-center' and not(contains(@class, 'panel songlist-panel noprint'))]");

                // Check if the div element exists
                if (divElement != null) {
                    // Extract the text content from the div
                    String lyrics = divElement.getTextContent();
                    String delimiter = "if  ";
                    String[] songPrint = lyrics.split(delimiter);

                    // Return the extracted lyrics after cleaning up whitespace and newlines
                    return songPrint[0].trim().replace("  ", "").replace("\n", " ");
                } else {
                    return null; // Lyrics not found
                }
            } else {
                System.out.println("Could Not Find Song");
            }
        } else {
            System.out.println("Could Not Find Artist");
        }
        return null;
    }

    public static TopSongsPayload getTop100List() throws IOException {
        WebClient webClient = new WebClient();
        // Disable JavaScript (optional, depending on your needs)
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = webClient.getPage("https://acharts.co/us_singles_top_100");
        String tbodyXPath = "//*[@id='ChartTable']/tbody";

        TopSongsPayload payload = new TopSongsPayload();
        List<TopSong> listOfSongs = new ArrayList<>();

        DomElement tbodyElement = page.getFirstByXPath(tbodyXPath);
        if (tbodyElement != null) {
            String trXpath = ".//td[@class='cPrinciple']"; // Make it relative to tbodyElement
            String nameSub = ".//span[@class='Sub']"; // Make it relative to tbodyElement
            List<DomElement> trElement = tbodyElement.getByXPath(trXpath);
            List<DomElement> artistName = tbodyElement.getByXPath(nameSub);
            for (int i =0; i<100; i++){
                TopSong song = new TopSong();
                String name = artistName.get(i).getTextContent().trim().replace("\n", "").replaceAll(" +", " ");
                String[] str = trElement.get(i).getTextContent().trim().replace("\n", "").replaceAll(" +", " ").split(name);
                String songTitle = str[0];
                song.setSongName(songTitle);
                song.setArtist(name);
                listOfSongs.add(song);
            }
            payload.setTopSongs(listOfSongs);
        }
        return payload;
    }
}
