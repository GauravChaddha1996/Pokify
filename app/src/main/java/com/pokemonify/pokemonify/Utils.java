package com.pokemonify.pokemonify;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;

/**
 * Created by gaurav on 24/7/16.
 */
public class Utils {
    public static void setMyPokemon(PokemonDto pokemonDto, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPokemon", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("myPokemonId", pokemonDto.getId());
        editor.commit();
    }

    public static int getMyPokemon(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPokemon", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("myPokemonId", -1);
    }
    public static void hideKeyboard(Activity activity) {
        View view=activity.getCurrentFocus();
        InputMethodManager methodManager= (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
