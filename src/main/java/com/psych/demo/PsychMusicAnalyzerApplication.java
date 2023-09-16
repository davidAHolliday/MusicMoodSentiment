package com.psych.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.psych.demo.data.Scraper;
import com.psych.demo.model.ChartItem;
import com.psych.demo.model.ChartItemsPayload;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@SpringBootApplication
public class PsychMusicAnalyzerApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(PsychMusicAnalyzerApplication.class, args);


	}


}










