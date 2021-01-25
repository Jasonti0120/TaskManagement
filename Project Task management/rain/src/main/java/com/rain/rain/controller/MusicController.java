package com.rain.rain.controller;

import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.Music;
import com.rain.rain.repo.MusicRepo;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rain")
public class MusicController {

    @Autowired
    private MusicRepo musicrepo;

    //Get all pieces of Music
    @GetMapping("/music")
    public List<Music> GetAllMusic(){
        return musicrepo.findAll();
    }

    // Get a Single piece of music
    @GetMapping("/music/{id}")
    public Music GetMusicById(@PathVariable(value = "id") int musicId) {
        return musicrepo.findById(musicId)
                .orElseThrow(() -> new ResourceNotFoundException("Music", "id", musicId));
    }

    //add a piece of music
    @PostMapping("/music")
    public Music SaveMusic(@Validated @NotNull @RequestBody Music music){
        return musicrepo.save(music);
    }


    //Update a piece of music
    @PutMapping("/music/{musicId}")
    public Music UpdateMusic(@PathVariable int musicId, @Validated @NotNull @RequestBody Music musicRequest){
        return musicrepo.findById(musicId).map(music -> {
            music.setDescription(musicRequest.getDescription());
            music.setMusiclink(musicRequest.getMusiclink());
            return musicrepo.save(music);
        }).orElseThrow(() -> new ResourceNotFoundException("Music", "id", musicId));
    }

    //Delete a piece of music
    @DeleteMapping(value = "/music/{musicId}")
    public void DeleteMusic(@PathVariable int musicId){
        musicrepo.deleteById(musicId);
    }
}
