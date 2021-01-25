package com.rain.rain.repo;


import com.rain.rain.models.User_Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMusicRepo extends JpaRepository<User_Music, Integer> {
    Page<User_Music> findByUserId(Integer UserId, Pageable pageable);
    Page<User_Music> findByMusicId(Integer MusicId, Pageable pageable);
}
