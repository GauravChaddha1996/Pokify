package com.pokemonify.pokemonify.pokemondatabase;

/**
 * Created by gaurav on 24/7/16.
 */
public class PokemonDatabase {
    private static PokemonDto[] sPokemonDtos;
    private static int[] sPokemonIds={0,1,2,3,4,5,6,7,8,9};
    private static PokemonDatabase mPokemonDatabase;
    public PokemonDatabase() {
        sPokemonDtos = new PokemonDto[10];
        sPokemonDtos[0] = new PokemonDto(sPokemonIds[0], "Pikachu", 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[1] = new PokemonDto(sPokemonIds[1], "Onix", 13, "", "Snake", 20, 3, 15);
        sPokemonDtos[2] = new PokemonDto(sPokemonIds[2], "ratata", 10, "", "Mouse", 18, 3, 15);
        sPokemonDtos[3] = new PokemonDto(sPokemonIds[3], "bulbasaur", 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[4] = new PokemonDto(sPokemonIds[4], "squirtle", 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[5] = new PokemonDto(sPokemonIds[5], "charmandir", 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[6] = new PokemonDto(sPokemonIds[6], "charlizard", 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[7] = new PokemonDto(sPokemonIds[7], "eevee", 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[8] = new PokemonDto(sPokemonIds[8], "snorlax", 50, "", "Mouse", 18, 3, 15);
        sPokemonDtos[9] = new PokemonDto(sPokemonIds[9], "Tanmay", 50, "", "Mouse", 18, 3, 15);
    }

    public static PokemonDatabase getInstance(){
        if(mPokemonDatabase==null){
            mPokemonDatabase=new PokemonDatabase();
        }
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
