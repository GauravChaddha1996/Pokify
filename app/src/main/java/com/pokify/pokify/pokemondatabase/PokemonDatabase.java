package com.pokify.pokify.pokemondatabase;

import android.content.Context;

import com.pokify.pokify.R;

/**
 * Created by gaurav on 24/7/16.
 */
public class PokemonDatabase {
    private static PokemonDto[] sPokemonDtos;
    private static int[] sPokemonIds;

    public PokemonDatabase(Context context) {
        sPokemonDtos = new PokemonDto[150];
        sPokemonIds = context.getResources().getIntArray(R.array.pokemon_id);
        String[] sPokemonName = context.getResources().getStringArray(R.array.pokemon_name);
        String[] sPokemonType = context.getResources().getStringArray(R.array.pokemon_type);
        String[] sPokemonDesc = context.getResources().getStringArray(R.array.pokemon_desc);
        String[] sPokemonAdjectives = context.getResources().getStringArray(R.array.pokemon_adjectives);
        int[] sPokemonHp = context.getResources().getIntArray(R.array.pokemon_hp);
        int[] sPokemonWeight = context.getResources().getIntArray(R.array.pokemon_weight);
        int[] sPokemonHeight = context.getResources().getIntArray(R.array.pokemon_height);
        int[] sPokemonLevel = context.getResources().getIntArray(R.array.pokemon_level);
        for (int i = 0; i < 150; i++) {
            sPokemonDtos[i] = new PokemonDto(sPokemonIds[i], sPokemonName[i], sPokemonHp[i], sPokemonName[i],
                    sPokemonType[i], sPokemonDesc[i], "", sPokemonWeight[i], sPokemonHeight[i], sPokemonLevel[i]);
        }
    }

    public static PokemonDatabase newInstance(Context context) {
        return new PokemonDatabase(context);
    }

    public static PokemonDto[] getPokemonDtos() {
        return sPokemonDtos;
    }

    public static PokemonDto getPokemonViaId(long id) {
        for (int i = 0; i < 150; i++) {
            if (id == sPokemonIds[i]) {
                return sPokemonDtos[i];
            }
        }
        return sPokemonDtos[24];
    }
}
