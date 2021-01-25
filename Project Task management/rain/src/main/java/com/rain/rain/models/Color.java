package com.rain.rain.models;

import javax.persistence.*;

@Entity
@Table(name = "Color")


public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Color;

    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", Color='" + Color + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
