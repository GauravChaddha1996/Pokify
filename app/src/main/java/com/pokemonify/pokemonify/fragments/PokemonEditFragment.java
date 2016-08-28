package com.pokemonify.pokemonify.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gaurav on 25/7/16.
 */
public class PokemonEditFragment extends Fragment {

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
    ProgressDialog progressDialog;

    public PokemonEditFragment() {

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
        if (mPokemonDto.getType().equals("Mouse")) {
            detailScreen.setBackground(getResources().getDrawable(R.drawable.pokeball));
        } else {
            detailScreen.setBackground(getResources().getDrawable(R.drawable.pikachu));
        }
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
        materialDialogCreator.setShouldEdit();
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


    public void setPokemonImage(Bitmap bitmap) {
        pokemonImageBitmap = bitmap;
        pokemonImage.setImageBitmap(pokemonImageBitmap);
    }

    private void setPokemonData() {
        pokemonName.setText(mPokemonDto.getName());
        pokemonHp.setText(mPokemonDto.getHp() + " Hp");
        pokemonType.setText(mPokemonDto.getType());
        pokemonWeight.setText(mPokemonDto.getWeight() + " g");
        pokemonHeight.setText(mPokemonDto.getHeight() + " cm");
        pokemonDesc.setText(mPokemonDto.getDesc());
        pokemonLvl.setText("Lvl " + mPokemonDto.getLevel());
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
    }

    public void shareThisPokemon() {
        detailScreen.setDrawingCacheEnabled(true);
        savedScreen = Bitmap.createBitmap(detailScreen.getDrawingCache());
        detailScreen.destroyDrawingCache();
        ((MainActivity) getActivity()).shareImage(savedScreen);
    }

    public void saveMyCard(Fragment fragment) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Saving the pokemon");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        Log.d("TAG", "time start:" + System.currentTimeMillis() + "");
        if (DbHelper.getInstance().saveMyCard(getDtoOfScreen())) {
            progressDialog.dismiss();
            toggleShouldEdit();
            if (fragment != null) {
                ((MainActivity) getActivity()).doFragTransaction(fragment,0);
            }

        }
    }

    private PokemonDto getDtoOfScreen() {
        mPokemonDto = new PokemonDto();
        mPokemonDto.setId(Utils.getRandomId());
        mPokemonDto.setName(pokemonName.getText().toString());
        mPokemonDto.setHp(Integer.parseInt((pokemonHp.getText().toString().substring(0, pokemonHp.getText().toString().length() - 2)).trim()));
        mPokemonDto.setType(pokemonType.getText().toString());
        mPokemonDto.setWeight(Integer.parseInt((pokemonWeight.getText().toString().substring(0, pokemonWeight.getText().toString().length() - 1)).trim()));
        mPokemonDto.setHeight(Integer.parseInt((pokemonHeight.getText().toString().substring(0, pokemonHeight.getText().toString().length() - 2)).trim()));
        mPokemonDto.setDesc(pokemonDesc.getText().toString());
        mPokemonDto.setLevel(Integer.parseInt((pokemonLvl.getText().toString().substring(3)).trim()));
        mPokemonDto.setBitmapPath(writeToFile(pokemonImageBitmap, mPokemonDto.getId()).getAbsolutePath());
        mPokemonDto.setImagePath("-1");
        return mPokemonDto;
    }

    private File writeToFile(Bitmap bitmap, long id) {
        OutputStream outStream = null;

        File file = new File(getActivity().getFilesDir() + File.separator + id + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(getActivity().getFilesDir() + File.separator + id + ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + file.getAbsolutePath());
        }
        try {
            // make a new bitmap from your file
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;
    }

    public void toggleShouldEdit() {
        materialDialogCreator.setShouldEdit();
    }

    public void setThisAsCurrentPokemon() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Making this your current pokemon");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (Utils.setMyPokemon(getDtoOfScreen(), getActivity())) {
                    progressDialog.dismiss();
                }
            }
        });
        executorService.shutdown();
    }
}