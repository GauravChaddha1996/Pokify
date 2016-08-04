package com.pokemonify.pokemonify.pokemondatabase;

import java.io.Serializable;

/**
 * Created by gaurav on 24/7/16.
 */
public class PokemonDto implements Serializable {
    private long id;
    private String name;
    private int hp;
    private String imagePath = "-1";
    private String type;
    private String desc;
    private String adjectives;
    private int weight;
    private int height;
    private int level;
    private String bitmapPath =null;

    public PokemonDto() {
    }

    public PokemonDto(long id, String name, int hp, String imagePath, String type, String desc, String adjectives, int weight, int height, int level) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.imagePath = imagePath;
        this.type = type;
        this.desc = desc;
        this.adjectives = adjectives;
        this.weight = weight;
        this.height = height;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(String adjectives) {
        this.adjectives = adjectives;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBitmapPath() {
        return bitmapPath;
    }

    public void setBitmapPath(String bitmapPath) {
        this.bitmapPath = bitmapPath;
    }
}
