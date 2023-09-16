package com.psych.demo.data;

import com.psych.demo.model.TopSong;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends MongoRepository<TopSong,Integer> {
    List<TopSong> findBySongName(String songName);

    TopSong deleteBySongName(String songName);
}
