package com.pokemonify.pokemonify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.adapters.PokemonListAdapter;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDatabase;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PokemonListFragment extends Fragment {
    PokemonListAdapter mPokemonListAdapter;
    RecyclerView mRecyclerView;
    List<PokemonDto> list;
    TextView mSeachedText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        list= Arrays.asList(PokemonDatabase.getPokemonDtos());
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        mSeachedText= (TextView) v.findViewById(R.id.searchedItem);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.searchPokemonRecyclerView);
        mPokemonListAdapter = new PokemonListAdapter(getActivity(),list);
        mRecyclerView.setAdapter(mPokemonListAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    public void search(String query) {
        mSeachedText.setText(query);
        List<PokemonDto> newlist=new ArrayList<>();
        for(PokemonDto p:list) {
            if(p.getName().toLowerCase().contains(query.toLowerCase())){
                Log.d("asjhas",p.getName());
                newlist.add(p);
            }
        }
        mPokemonListAdapter.setPokeList(newlist);
        mPokemonListAdapter.notifyDataSetChanged();
    }

    public PokemonListAdapter getPokemonListAdapter() {
        return mPokemonListAdapter;
    }
}
