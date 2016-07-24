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

        sPokemonDtos[0] = new PokemonDto(sPokemonIds[0], sPokemonName[0], 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[1] = new PokemonDto(sPokemonIds[1], sPokemonName[1], 13, "", "Snake", 20, 3, 15);
        sPokemonDtos[2] = new PokemonDto(sPokemonIds[2], sPokemonName[2], 10, "", "Mouse", 18, 3, 15);
        sPokemonDtos[3] = new PokemonDto(sPokemonIds[3], sPokemonName[3], 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[4] = new PokemonDto(sPokemonIds[4], sPokemonName[4], 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[5] = new PokemonDto(sPokemonIds[5], sPokemonName[5], 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[6] = new PokemonDto(sPokemonIds[6], sPokemonName[6], 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[7] = new PokemonDto(sPokemonIds[7], sPokemonName[7], 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[8] = new PokemonDto(sPokemonIds[8], sPokemonName[8], 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[9] = new PokemonDto(sPokemonIds[9], sPokemonName[9], 50, "", "Mouse", 18, 3, 15);
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
