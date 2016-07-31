package com.pokemonify.pokemonify.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokemonify.pokemonify.MainActivity;
import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.Utils;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDatabase;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;

public class MainFragment extends Fragment {

    TextView mMyPokemonName;
    TextView mMyPokemonHp;
    TextView mMyPokemonType;
    TextView mMyPokemonLevel;
    TextView mMyPokemonWeight;
    TextView mMyPokemonHeight;
    TextView mMyPokemonDesc;
    ImageView mMyPokemonImage;
    View mMyPokemonScreen;
    PokemonDto currentMyPokemon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        mMyPokemonName = (TextView) v.findViewById(R.id.my_pokemon_name);
        mMyPokemonHp = (TextView) v.findViewById(R.id.my_pokemon_hp);
        mMyPokemonType = (TextView) v.findViewById(R.id.my_pokemon_type);
        mMyPokemonLevel = (TextView) v.findViewById(R.id.my_pokemon_level);
        mMyPokemonWeight = (TextView) v.findViewById(R.id.my_pokemon_weight);
        mMyPokemonHeight = (TextView) v.findViewById(R.id.my_pokemon_height);
        mMyPokemonDesc = (TextView) v.findViewById(R.id.my_pokemon_desc);
        mMyPokemonImage = (ImageView) v.findViewById(R.id.my_pokemon_image);
        mMyPokemonScreen = v.findViewById(R.id.my_pokemon_screen);
        setMyPokemon();
        mMyPokemonName.setText(currentMyPokemon.getName());
        mMyPokemonHp.setText(currentMyPokemon.getHp() + "Hp");
        mMyPokemonType.setText(currentMyPokemon.getType());
        mMyPokemonLevel.setText("Lvl " + currentMyPokemon.getLevel());
        mMyPokemonWeight.setText(currentMyPokemon.getWeight() + "lbs");
        mMyPokemonHeight.setText(currentMyPokemon.getHeight() + "cm");
        mMyPokemonDesc.setText(currentMyPokemon.getDesc());
        mMyPokemonImage.getLayoutParams().height= (int) (Utils.getDisplayHeight(getActivity())*0.40);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources()
                .getIdentifier(currentMyPokemon.getImagePath(), "drawable", getActivity().getPackageName()));
        mMyPokemonImage.setImageBitmap(bitmap);
    }

    public void shareMyPokemon(){
        mMyPokemonScreen.setDrawingCacheEnabled(true);
        Bitmap bitmap=Bitmap.createBitmap(mMyPokemonScreen.getDrawingCache());
        mMyPokemonScreen.destroyDrawingCache();
        ((MainActivity)getActivity()).shareImage(bitmap);
    }

    public void setMyPokemon() {
        long temp = Utils.getMyPokemon(getActivity());
        if (temp == -1) {
            currentMyPokemon = new PokemonDto(0, "Snorlax", 50, "", "Mouse", "", "lazy,fat", 18, 3, 15);
        } else {
            currentMyPokemon = PokemonDatabase.getPokemonViaId(temp);
            if (currentMyPokemon == null) {
                currentMyPokemon = new PokemonDto(1, "Raichu", 50, "", "Mouse", "", "angry,strong", 18, 3, 15);
            }
        }
        Utils.setMyPokemon(currentMyPokemon, getActivity());
    }
}
