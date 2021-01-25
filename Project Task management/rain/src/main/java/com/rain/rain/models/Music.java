package com.rain.rain.models;

import javax.persistence.*;

@Entity
@Table(name = "Music")


public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    private String Musiclink;

    private String Description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMusiclink() {
        return Musiclink;
    }

    public void setMusiclink(String musiclink) {
        Musiclink = musiclink;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", Music link='" + Musiclink + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
