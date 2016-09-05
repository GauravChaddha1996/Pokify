package com.pokify.pokify;

import android.app.Application;

import com.pokify.pokify.UIComponents.FontOverride;
import com.pokify.pokify.pokemondatabase.DbHelper;
import com.pokify.pokify.pokemondatabase.PokemonDatabase;

import java.util.Random;

/**
 * Created by gaurav on 24/7/16.
 */
public class Pokify extends Application {
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
