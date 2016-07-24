package com.pokemonify.pokemonify.pokemondatabase;

import android.content.Context;

import com.pokemonify.pokemonify.R;

/**
 * Created by gaurav on 24/7/16.
 */
public class PokemonDatabase {
    private static PokemonDto[] sPokemonDtos;
    private static int[] sPokemonIds;
    private static String[] sPokemonName;
    private static PokemonDatabase mPokemonDatabase;
    public PokemonDatabase(Context context) {
        sPokemonDtos = new PokemonDto[10];
        sPokemonIds=context.getResources().getIntArray(R.array.pokemon_id);
        sPokemonName=context.getResources().getStringArray(R.array.pokemon_name);

        for(int i=0;i<10;i++) {
            sPokemonDtos[i]=new PokemonDto(sPokemonIds[i],sPokemonName[i],50,"","mouse",10,10,120);
        }
    }

    public static PokemonDatabase newInstance(Context context){
        mPokemonDatabase=new PokemonDatabase(context);
        return mPokemonDatabase;
    }

    public static PokemonDto[] getPokemonDtos() {
        return sPokemonDtos;
    }

    public static PokemonDto getPokemonViaId(int id) {
        for(int i=0;i<10;i++) {
            if(id==sPokemonIds[i]) {
                return sPokemonDtos[i];
            }
        }
        return null;
    }
}
