package com.pokemonify.pokemonify.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokemonify.pokemonify.MainActivity;
import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.UIComponents.MaterialDialogCreator;
import com.pokemonify.pokemonify.Utils;
import com.pokemonify.pokemonify.pokemondatabase.DbHelper;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;

/**
 * Created by gaurav on 25/7/16.
 */
public class MyCardDetailFragment extends Fragment {

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
    Boolean preEdit=false;
    public MyCardDetailFragment() {

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
        pokemonImage.getLayoutParams().height = (int) (Utils.getDisplayHeight(getActivity()) * 0.40);
        setPokemonData();
        setOnClick();
    }

    public Boolean getPreEdit() {
        return preEdit;
    }

    public void setPreEdit(Boolean preEdit) {
        this.preEdit = preEdit;
    }

    private void setOnClick() {
        materialDialogCreator = new MaterialDialogCreator(getActivity(), new MaterialDialogCreator.OnClickCallBack() {
            @Override
            public void onPress(View v, String s) {
                switch (v.getId()) {
                    case R.id.pokemon_name:
                        pokemonName.setText(s);
                        break;
                    case R.id.pokemon_hp:
                        pokemonHp.setText(s + " Hp");
                        break;
                    case R.id.pokemon_type:
                        pokemonType.setText(s);
                        break;
                    case R.id.pokemon_weight:
                        pokemonWeight.setText(s + " g");
                        break;
                    case R.id.pokemon_height:
                        pokemonHeight.setText(s + " cm");
                        break;
                    case R.id.pokemon_desc:
                        pokemonDesc.setText(s);
                        break;
                    case R.id.pokemon_level:
                        pokemonLvl.setText("Lvl " + s);
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
        final MainActivity mainActivity = (MainActivity) getActivity();
        pokemonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialDialogCreator.getShouldEdit()) {
                    mainActivity.startPokeImagePicker();
                }
            }
        });
        if(preEdit){
            materialDialogCreator.setShouldEdit();
        }
    }

    public void setPokemonImage(Bitmap bitmap) {
        pokemonImage.setImageBitmap(bitmap);
    }

    private void setPokemonData() {
        pokemonName.setText(mPokemonDto.getName());
        pokemonHp.setText(mPokemonDto.getHp() + " Hp");
        pokemonType.setText(mPokemonDto.getType());
        pokemonWeight.setText(mPokemonDto.getWeight() + " g");
        pokemonHeight.setText(mPokemonDto.getHeight() + " cm");
        pokemonDesc.setText(mPokemonDto.getDesc());
        pokemonLvl.setText("Lvl " + mPokemonDto.getLevel());
        Bitmap bitmap=null;
        if(mPokemonDto.getImagePath().equals("-1")) {
            bitmap=mPokemonDto.getBitmap();
        } else {
        bitmap = BitmapFactory.decodeResource(getResources(), getResources()
                .getIdentifier(mPokemonDto.getImagePath(), "drawable", getActivity().getPackageName()));
        }
        pokemonImage.setImageBitmap(bitmap);
    }

    public void shareThisPokemon() {
        detailScreen.setDrawingCacheEnabled(true);
        savedScreen = Bitmap.createBitmap(detailScreen.getDrawingCache());
        detailScreen.destroyDrawingCache();
        ((MainActivity)getActivity()).shareImage(savedScreen);
    }

    private void saveMyCard() {
        final DbHelper dbHelper=new DbHelper(getActivity());
        dbHelper.saveMyCard(getDtoOfScreenData());
    }

    private PokemonDto getDtoOfScreenData() {
        PokemonDto dto=new PokemonDto();
        dto.setId(System.currentTimeMillis());
        dto.setName(pokemonName.getText().toString());
        dto.setHp(Integer.parseInt((pokemonHp.getText().toString().substring(0,pokemonHp.getText().toString().length()-2)).trim()));
        dto.setType(pokemonType.getText().toString());
        dto.setWeight(Integer.parseInt((pokemonWeight.getText().toString().substring(0,pokemonWeight.getText().toString().length()-1)).trim()));
        dto.setHeight(Integer.parseInt((pokemonHeight.getText().toString().substring(0,pokemonHeight.getText().toString().length()-2)).trim()));
        dto.setDesc(pokemonDesc.getText().toString());
        dto.setLevel(Integer.parseInt((pokemonLvl.getText().toString().substring(3)).trim()));
        pokemonImage.setDrawingCacheEnabled(true);
        dto.setBitmap(Bitmap.createBitmap(pokemonImage.getDrawingCache()));
        pokemonImage.destroyDrawingCache();
        dto.setImagePath("-1");
        return dto;
    }

    public void saveAndToggle() {
        saveMyCard();
        toggleShouldEdit();
    }

    public void toggleShouldEdit() {
        materialDialogCreator.setShouldEdit();
    }

    public Boolean getEditing() {
        return materialDialogCreator.getShouldEdit();
    }

    public void setThisAsCurrentPokemon() {
        Utils.setMyPokemon(getDtoOfScreenData(), getActivity());
    }
}
