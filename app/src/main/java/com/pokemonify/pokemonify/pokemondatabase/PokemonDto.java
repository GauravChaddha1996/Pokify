package com.pokemonify.pokemonify.pokemondatabase;

/**
 * Created by gaurav on 24/7/16.
 */
public class PokemonDto {
    private int id;
    private String name;
    private int hp;
    private String imagePath;
    private String type;
    private int weight;
    private int height;
    private int level;

    public PokemonDto() {
    }

    public PokemonDto(int id, String name, int hp, String imagePath, String type, int weight, int height, int level) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.imagePath = imagePath;
        this.type = type;
        this.weight = weight;
        this.height = height;
        this.level = level;
    }

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
}
