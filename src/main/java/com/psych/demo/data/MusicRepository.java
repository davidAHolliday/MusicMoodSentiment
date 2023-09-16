package com.psych.demo.data;

import com.psych.demo.model.TopSong;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends MongoRepository<TopSong,Integer> {
    boolean findBySongName(String songName);
}
