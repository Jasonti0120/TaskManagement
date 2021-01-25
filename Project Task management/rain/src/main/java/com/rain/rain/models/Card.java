package com.rain.rain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "Card")

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    @Lob
    private String description;

    @NotNull
    private String card_type;

    @NotNull
    private int card_rank;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List list;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "music_id", nullable = false)
    @OnDelete(action=OnDeleteAction.NO_ACTION)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Music music;


    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCard_Type() {
        return card_type;
    }

    public void setCard_Type(String card_type) {
        this.card_type = card_type;
    }

    public int getCard_Rank() {
        return card_rank;
    }

    public void setCard_Rank(int card_rank) {
        this.card_rank = card_rank;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", Card_name='" + name + '\'' +
                ", Card_description='" + description + '\'' +
                ", Card_Type='" + card_type + '\'' +
                ", card_order=" + card_rank +
                ", list=" + list +
                ", music=" + music +
                '}';
    }
}

