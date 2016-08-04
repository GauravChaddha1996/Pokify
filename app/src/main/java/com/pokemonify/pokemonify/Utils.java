package com.pokemonify.pokemonify;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.pokemonify.pokemonify.pokemondatabase.DbHelper;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;

import java.util.Random;

/**
 * Created by gaurav on 24/7/16.
 */
public class Utils {
    public static boolean setMyPokemon(PokemonDto pokemonDto, Context context) {
        return DbHelper.getInstance().saveMyPokemon(pokemonDto);
    }

    public static PokemonDto getMyPokemon(Context context) {
        return DbHelper.getInstance().getMyPokemon();
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        InputMethodManager methodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int getDisplayHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public static int getRandomId() {
        return new Random().nextInt();
    }
}
