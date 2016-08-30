package com.pokemonify.pokemonify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pokemonify.pokemonify.MainActivity;
import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.Utils;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDatabase;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;
import com.pokemonify.pokemonify.recyclerviewcomponents.ItemClickSupport;
import com.pokemonify.pokemonify.recyclerviewcomponents.PokemonListAdapter;
import com.pokemonify.pokemonify.recyclerviewcomponents.RecyclerViewEmptyExtdener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PokemonListFragment extends Fragment {
    PokemonListAdapter mPokemonListAdapter;
    RecyclerViewEmptyExtdener mRecyclerView;
    TextView mEmptyView;
    List<PokemonDto> nameList;
    TextView mSeachedText;
    String preScrollName=null;

    public PokemonListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Pokedex");
        nameList = Arrays.asList(PokemonDatabase.getPokemonDtos());
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        mSeachedText = (TextView) v.findViewById(R.id.searchedItem);
        mEmptyView = (TextView) v.findViewById(R.id.emptyRecyclerView);
        mEmptyView.setText("No pokemon found :( \n Search again !!");
        mRecyclerView = (RecyclerViewEmptyExtdener) v.findViewById(R.id.pokemonRecyclerView);
        mRecyclerView.setEmptyView(mEmptyView);
        if (nameList.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        mPokemonListAdapter = new PokemonListAdapter(getActivity(), nameList);
        mRecyclerView.setAdapter(mPokemonListAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                PokemonDto clickedPokemon = mPokemonListAdapter.getPokeList().get(position);
                MainActivity mainActivity = (MainActivity) getActivity();
                Bundle bundle = new Bundle();
                bundle.putSerializable("PokemonDto", clickedPokemon);
                PokemonDetailFragment detailFragment = new PokemonDetailFragment();
                detailFragment.setArguments(bundle);
                Utils.hideKeyboard(mainActivity);
                mainActivity.hideSearch();
                mainActivity.changeFrag(detailFragment);
            }
        });
        if(preScrollName!=null) {
            scrollTo();
        }
    }

    public void search(String query) {
        mSeachedText.setText(query);
        Set<PokemonDto> newSet = new HashSet<>();
        for (PokemonDto p : nameList) {
            if (p.getName().toLowerCase().contains(query.toLowerCase())) {
                newSet.add(p);
            }
        }
        mPokemonListAdapter.setPokeList(new ArrayList<PokemonDto>(newSet));
        mPokemonListAdapter.notifyDataSetChanged();
    }

    private void scrollTo() {
        int i=0;
        for(PokemonDto p:nameList) {
            i++;
            if(p.getName().toLowerCase().equals(preScrollName)) {
                break;
            }
        }
        i=i-2;
        if(i<0) {
            i=0;
        }
        mRecyclerView.scrollToPosition(i);
    }

    public void setPreScrollName(String preScrollName) {
        this.preScrollName = preScrollName;
    }
}
