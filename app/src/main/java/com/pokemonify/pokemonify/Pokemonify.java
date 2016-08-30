package com.pokemonify.pokemonify;

import android.app.Application;

import com.pokemonify.pokemonify.UIComponents.FontOverride;
import com.pokemonify.pokemonify.pokemondatabase.DbHelper;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDatabase;

import java.util.Random;

/**
 * Created by gaurav on 24/7/16.
 */
public class Pokemonify extends Application {
    public static int thisTimeRandomId=24;
    @Override
    public void onCreate() {
        super.onCreate();
        PokemonDatabase database = PokemonDatabase.newInstance(getApplicationContext());
        DbHelper dbHelper = new DbHelper(getApplicationContext());
        dbHelper.setMyCardsList();
        FontOverride.setDefaultFont(getApplicationContext(), "MONOSPACE", "archrival.ttf");
        thisTimeRandomId=new Random().nextInt(151);
    }


}
