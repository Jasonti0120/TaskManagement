package com.rain.rain.repo;


import com.rain.rain.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepo extends JpaRepository<Color, Integer> {
}
