package com.rain.rain.controller;

import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.User_Music;
import com.rain.rain.repo.*;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rain")

public class UserMusicController {
    @Autowired
    UserMusicRepo userMusicRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    MusicRepo musicRepo;


    // Get All UserMusic
    @GetMapping("/usermusic")
    public List<User_Music> GetAllUserMusic() {
        return userMusicRepo.findAll();
    }

    //Get Usermusic by music ID
    @GetMapping("/music/{musicId}/usermusic")
    public Page<User_Music> getAllUser_MusicByMusicId(@PathVariable(value = "musicId") int musicId,
                                                      Pageable pageable){
        return userMusicRepo.findByMusicId(musicId, pageable);
    }

    //Get Usermusic by music ID
    @GetMapping("/users/{userId}/usermusic")
    public Page<User_Music> getAllUser_MusicByUserId(@PathVariable(value = "userId") int userId,
                                                                          Pageable pageable){
        return userMusicRepo.findByUserId(userId, pageable);
    }

    // Create a new User_Music
    @PostMapping("/music/{musicId}/users/{userId}/usermusic")
    public User_Music CreateNewUserMusic(@PathVariable(value = "musicId") int musicId,
                                         @PathVariable(value = "userId") int userId,
                                         @Validated @NotNull @RequestBody User_Music userMusic){

        return musicRepo.findById(musicId).map(music -> {
            userMusic.setMusic(music);
            return userRepo.findById(userId).map(user -> {
                userMusic.setUser(user);
                return userMusicRepo.save(userMusic);
            }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", userId));
        }).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", musicId));
    }

    // Update an UserBoard
    @PutMapping("/music/{musicId}/users/{userId}/usermusic/{userMusicId}")
    public User_Music UpdateUserBoard(@PathVariable(value = "musicId") int musicId,
                                      @PathVariable(value = "userId") int userId,
                                      @PathVariable(value = "userMusicId") int userMusicId,
                                      @Validated @NotNull @RequestBody User_Music userMusicRequest) {
        if (!musicRepo.existsById(musicId)) {
            throw new ResourceNotFoundException("Music", "Id", musicId);
        }
        else if (!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User", "Id", userId);
        }
        return userMusicRepo.findById(userMusicId).map(user_music -> {
            user_music.setUser_music_rank(userMusicRequest.getUser_music_rank());
            return userMusicRepo.save(user_music);
        }).orElseThrow(() -> new ResourceNotFoundException("User_Music", "Id", userMusicId));
    }


    // Delete a User_Music
    @DeleteMapping("/usermusic/{id}")
    public ResponseEntity<?> DeleteUserMusic(@PathVariable(value = "id") int userMusicId) {

        User_Music userMusic = userMusicRepo.findById(userMusicId)
                .orElseThrow(() -> new ResourceNotFoundException("UserMusic", "id", userMusicId));

        userMusicRepo.delete(userMusic);

        return ResponseEntity.ok().build();
    }


}
