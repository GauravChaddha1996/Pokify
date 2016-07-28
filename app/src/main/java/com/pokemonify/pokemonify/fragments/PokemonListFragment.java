package com.pokemonify.pokemonify.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pokemonify.pokemonify.ItemClickSupport;
import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.Utils;
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
        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("My Pokemon");
                builder.setMessage("Set "+mPokemonListAdapter.getPokeList().get(position).getName()+
                " as My pokemon?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.setMyPokemon(mPokemonListAdapter.getPokeList().get(position),getActivity());
                    }
                });
                builder.show();
                return true;
            }
        });
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
