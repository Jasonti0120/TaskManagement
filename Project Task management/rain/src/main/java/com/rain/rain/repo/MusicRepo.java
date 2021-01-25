package com.rain.rain.repo;

import com.rain.rain.models.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepo extends JpaRepository<Music, Integer> {
}
