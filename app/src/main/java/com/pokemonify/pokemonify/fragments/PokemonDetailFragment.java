package com.pokemonify.pokemonify.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokemonify.pokemonify.MainActivity;
import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.UIComponents.MaterialDialogCreator;
import com.pokemonify.pokemonify.Utils;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        pokemonImage.getLayoutParams().height = (int) (displaymetrics.heightPixels * 0.50);
        setPokemonData();
        setOnClick();
    }

    private void setOnClick() {
        materialDialogCreator=new MaterialDialogCreator(getActivity(), new MaterialDialogCreator.OnClickCallBack() {
            @Override
            public void onPress(View v,String s) {
                switch (v.getId()){
                    case R.id.pokemon_name:
                        pokemonName.setText(s);
                        break;
                    case R.id.pokemon_hp:
                        pokemonHp.setText(s+" Hp");
                        break;
                    case R.id.pokemon_type:
                        pokemonType.setText(s);
                        break;
                    case R.id.pokemon_weight:
                        pokemonWeight.setText(s+" g");
                        break;
                    case R.id.pokemon_height:
                        pokemonHeight.setText(s+" cm");
                        break;
                    case R.id.pokemon_desc:
                        pokemonDesc.setText(s);
                        break;
                    case R.id.pokemon_level:
                        pokemonLvl.setText(s+" Lvl");
                        break;
                }


            }
        });
        pokemonName.setOnClickListener(materialDialogCreator);
        pokemonHp.setOnClickListener(materialDialogCreator);
        pokemonType.setOnClickListener(materialDialogCreator);
        pokemonWeight.setOnClickListener(materialDialogCreator);
        pokemonHeight.setOnClickListener(materialDialogCreator);
        pokemonDesc.setOnClickListener(materialDialogCreator);
        pokemonLvl.setOnClickListener(materialDialogCreator);
        final MainActivity mainActivity= (MainActivity) getActivity();
        pokemonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startPokeImagePicker();
            }
        });
    }

    public void setPokemonImage(Bitmap bitmap){
        pokemonImage.setImageBitmap(bitmap);
    }

    private void setPokemonData() {
        pokemonName.setText(mPokemonDto.getName());
        pokemonHp.setText(mPokemonDto.getHp() + " Hp");
        pokemonType.setText(mPokemonDto.getType());
        pokemonWeight.setText(mPokemonDto.getWeight() + " g");
        pokemonHeight.setText(mPokemonDto.getHeight() + " cm");
        pokemonDesc.setText(mPokemonDto.getDesc());
        pokemonLvl.setText(mPokemonDto.getLevel() + " Lvl");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources()
                .getIdentifier(mPokemonDto.getImagePath(), "drawable", getActivity().getPackageName()));
        pokemonImage.setImageBitmap(bitmap);
    }

    public void shareThisPokemon() {
        detailScreen.setDrawingCacheEnabled(true);
        savedScreen = Bitmap.createBitmap(detailScreen.getDrawingCache());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        savedScreen.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "tmp.jpg");
        try {
            f.createNewFile();
            // write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(f.getAbsolutePath()));
        startActivity(Intent.createChooser(share, "Share Pokemon"));
    }

    public void toggleShouldEdit() {
        materialDialogCreator.setShouldEdit();
    }

    public Boolean getEditing() {
        return materialDialogCreator.getShouldEdit();
    }

    public void setThisAsCurrentPokemon() {
        Utils.setMyPokemon(mPokemonDto, getActivity());
    }
}
