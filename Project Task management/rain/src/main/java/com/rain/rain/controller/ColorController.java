package com.rain.rain.controller;

import com.rain.rain.models.Color;
import com.rain.rain.repo.ColorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rain")
public class ColorController {

    @Autowired
    private ColorRepo colorrepo;

    //Get all colors
    @GetMapping("colors")
    public List<Color> findAll(){
        return colorrepo.findAll();
    }


}

