package com.pokemonify.pokemonify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.adapters.PokemonListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PokemonListFragment extends Fragment {
    PokemonListAdapter mPokemonListAdapter;
    RecyclerView mRecyclerView;
    List<String> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        list= Arrays.asList(getResources().getStringArray(R.array.pokemon_name));
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.searchPokemonRecyclerView);
        mPokemonListAdapter = new PokemonListAdapter(getActivity(),list);
        mRecyclerView.setAdapter(mPokemonListAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    public void search(String query) {

        List<String> newlist=new ArrayList<>();
        for(String s:list) {
            if(s.toLowerCase().contains(query.toLowerCase())){
                newlist.add(s);
            }
        }
        list=newlist;
        mPokemonListAdapter.setList(list);
        mPokemonListAdapter.notifyDataSetChanged();
    }



}
