package com.pokemonify.pokemonify;

import android.app.Application;

import com.pokemonify.pokemonify.pokemondatabase.PokemonDatabase;

/**
 * Created by gaurav on 24/7/16.
 */
public class Pokemonify extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PokemonDatabase database = PokemonDatabase.newInstance(getApplicationContext());
    }

}
