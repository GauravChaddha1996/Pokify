package com.pokify.pokify.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokify.pokify.MainActivity;
import com.pokify.pokify.R;
import com.pokify.pokify.UIComponents.MaterialDialogCreator;
import com.pokify.pokify.Utils;
import com.pokify.pokify.pokemondatabase.PokemonDto;

/**
 * Created by gaurav on 25/7/16.
 */
public class PokemonDetailFragment extends Fragment {

    PokemonDto mPokemonDto;
    TextView pokemonName;
    TextView pokemonHp;
    TextView pokemonType;
    TextView pokemonWeight;
    TextView pokemonHeight;
    TextView pokemonDesc;
    TextView pokemonLvl;
    ImageView pokemonImage;
    Bitmap pokemonImageBitmap;
    View detailScreen;
    Bitmap savedScreen;
    MaterialDialogCreator materialDialogCreator;

    public PokemonDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPokemonDto = (PokemonDto) getArguments().getSerializable("PokemonDto");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(mPokemonDto.getName());
        View view = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        pokemonName = (TextView) v.findViewById(R.id.pokemon_name);
        pokemonHp = (TextView) v.findViewById(R.id.pokemon_hp);
        pokemonType = (TextView) v.findViewById(R.id.pokemon_type);
        pokemonWeight = (TextView) v.findViewById(R.id.pokemon_weight);
        pokemonHeight = (TextView) v.findViewById(R.id.pokemon_height);
        pokemonDesc = (TextView) v.findViewById(R.id.pokemon_desc);
        pokemonLvl = (TextView) v.findViewById(R.id.pokemon_level);
        pokemonImage = (ImageView) v.findViewById(R.id.pokemon_image);
        detailScreen = v.findViewById(R.id.detailScreen);
        pokemonImage.getLayoutParams().height = (int) (Utils.getDisplayHeight(getActivity()) * 0.40);
        setPokemonData();
        setOnClick();
    }

    private void initMaterial() {
        materialDialogCreator = new MaterialDialogCreator(getActivity(), new MaterialDialogCreator.OnClickCallBack() {
            @Override
            public void onPress(View v, String s) {
                switch (v.getId()) {
                    case R.id.pokemon_name:
                        pokemonName.setText(s);
                        break;
                    case R.id.pokemon_hp:
                        pokemonHp.setText(s + "Hp");
                        break;
                    case R.id.pokemon_type:
                        pokemonType.setText(s);
                        break;
                    case R.id.pokemon_weight:
                        pokemonWeight.setText(s + "Lbs");
                        break;
                    case R.id.pokemon_height:
                        pokemonHeight.setText(s + "Inch");
                        break;
                    case R.id.pokemon_desc:
                        pokemonDesc.setText(s);
                        break;
                    case R.id.pokemon_level:
                        pokemonLvl.setText("Exp " + s);
                        break;
                }


            }
        });
    }

    private void setOnClick() {
        initMaterial();
        pokemonName.setOnClickListener(materialDialogCreator);
        pokemonHp.setOnClickListener(materialDialogCreator);
        pokemonType.setOnClickListener(materialDialogCreator);
        pokemonWeight.setOnClickListener(materialDialogCreator);
        pokemonHeight.setOnClickListener(materialDialogCreator);
        pokemonDesc.setOnClickListener(materialDialogCreator);
        pokemonLvl.setOnClickListener(materialDialogCreator);
        final MainActivity mainActivity = (MainActivity) getActivity();
        pokemonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialDialogCreator.getShouldEdit()) {
                    mainActivity.startPokeImagePicker();
                }
            }
        });
    }

    public PokemonDto getPokemonDto() {
        return mPokemonDto;
    }

    private void setPokemonData() {
        pokemonName.setText(mPokemonDto.getName());
        pokemonHp.setText(mPokemonDto.getHp() + " Hp");
        pokemonType.setText(mPokemonDto.getType());
        pokemonWeight.setText(mPokemonDto.getWeight() + " Lbs");
        pokemonHeight.setText(mPokemonDto.getHeight() + " Inch");
        pokemonDesc.setText(mPokemonDto.getDesc());
        pokemonLvl.setText("Exp " + mPokemonDto.getLevel());
        Bitmap bitmap = null;
        if (mPokemonDto.getImagePath().equals("-1")) {
            bitmap = BitmapFactory.decodeFile(mPokemonDto.getBitmapPath());
            bitmap = Utils.getRoundedCornerBitmap(bitmap);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), getResources()
                    .getIdentifier(mPokemonDto.getImagePath(), "drawable", getActivity().getPackageName()));
        }
        pokemonImageBitmap = bitmap;
        pokemonImage.setImageBitmap(pokemonImageBitmap);
        if (Utils.isTypePresent(pokemonType.getText().toString().toLowerCase())) {
            detailScreen.setBackground(new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
                    getResources().getIdentifier(pokemonType.getText().toString().toLowerCase(), "drawable",
                            getActivity().getPackageName()))));
        } else {
            detailScreen.setBackground(getResources().getDrawable(R.drawable.standardbackground));
        }
    }

    public void shareThisPokemon() {
        detailScreen.setDrawingCacheEnabled(true);
        savedScreen = Bitmap.createBitmap(detailScreen.getDrawingCache());
        detailScreen.destroyDrawingCache();
        ((MainActivity) getActivity()).shareImage(savedScreen);
    }

    public void saveMyPokemon() {
        detailScreen.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(detailScreen.getDrawingCache());
        detailScreen.destroyDrawingCache();
        Utils.saveFile(getActivity(), bitmap, mPokemonDto.getId());
    }
}
