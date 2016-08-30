package com.pokemonify.pokemonify;

import android.app.Application;

import com.pokemonify.pokemonify.UIComponents.FontOverride;
import com.pokemonify.pokemonify.pokemondatabase.DbHelper;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDatabase;

/**
 * Created by gaurav on 24/7/16.
 */
public class Pokemonify extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PokemonDatabase database = PokemonDatabase.newInstance(getApplicationContext());
        DbHelper dbHelper = new DbHelper(getApplicationContext());
        dbHelper.setMyCurrentPokemon();
        dbHelper.setMyCardsList();
        FontOverride.setDefaultFont(getApplicationContext(), "MONOSPACE", "archrival.ttf");
    }

}
