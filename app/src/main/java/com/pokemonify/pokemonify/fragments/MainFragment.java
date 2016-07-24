package com.pokemonify.pokemonify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.Utils;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDatabase;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;

public class MainFragment extends Fragment {

    TextView mMyPokemon;
    PokemonDto currentMyPokemon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v){
        mMyPokemon= (TextView) v.findViewById(R.id.myPokemon);
        setMyPokemon();
        mMyPokemon.setText(currentMyPokemon.getName());
    }

    public void setMyPokemon() {
        int temp= Utils.getMyPokemon(getActivity());
        if(temp==-1) {
            currentMyPokemon=new PokemonDto(0, "HAHAHAH", 50, "", "Mouse", 18, 3, 15);
        }else {
            currentMyPokemon= PokemonDatabase.getInstance().getPokemonViaId(temp);
            if(currentMyPokemon==null){
                currentMyPokemon=new PokemonDto(1, "Raichu", 50, "", "Mouse", 18, 3, 15);
            }
        }
        Utils.setMyPokemon(currentMyPokemon,getActivity());
    }
}
