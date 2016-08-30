package com.pokemonify.pokemonify.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    Bitmap pokemonImageBitmap;
    View detailScreen;
    Bitmap savedScreen;
    MaterialDialogCreator materialDialogCreator;
    Boolean preEdit = false;
    int temp = 0;
    ProgressDialog progressDialog;

    public MyCardDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPokemonDto = (PokemonDto) getArguments().getSerializable("PokemonDto");
        Log.d("TAG", "id is:" + mPokemonDto.getId() + "");
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

    public MaterialDialogCreator getMaterialDialogCreator() {
        return materialDialogCreator;
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
                        setMyScreenBackGround();
                        break;
                    case R.id.pokemon_weight:
                        pokemonWeight.setText(s + " Lbs");
                        break;
                    case R.id.pokemon_height:
                        pokemonHeight.setText(s + " Inch");
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
        if (preEdit) {
            materialDialogCreator.setShouldEdit();
        }
    }

    public void setPokemonImage(Bitmap bitmap) {
        pokemonImageBitmap = bitmap;
        pokemonImage.setImageBitmap(pokemonImageBitmap);
    }

    private void setPokemonData() {
        getActivity().setTitle(mPokemonDto.getName());
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
        pokemonImage.setImageBitmap(bitmap);
        setMyScreenBackGround();
    }


    private void setMyScreenBackGround() {
        Log.d("Type:", pokemonType.getText().toString().toLowerCase());
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

    public void saveInGallery() {
        detailScreen.setDrawingCacheEnabled(true);
        savedScreen = Bitmap.createBitmap(detailScreen.getDrawingCache());
        detailScreen.destroyDrawingCache();
        Utils.saveFile(getActivity(), savedScreen, mPokemonDto.getId());
    }

    private void saveMyCard(final Fragment fragment) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("How do you want to save the card?");
        builder.setPositiveButton("New", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startProgressDialog("Saving your pokemon");
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.d("TAG", "time start:" + System.currentTimeMillis() + "");
                        if (DbHelper.getInstance().saveMyCard(getDtoOfScreenData())) {
                            toggleShouldEdit();
                            getActivity().supportInvalidateOptionsMenu();
                            progressDialog.dismiss();
                            if (fragment != null) {
                                ((MainActivity) getActivity()).changeFrag(fragment);
                            }
                        }
                    }
                };
                handler.sendEmptyMessageDelayed(0, 800);
            }
        });
        builder.setNegativeButton("Existing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startProgressDialog("Saving your pokemon");
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (DbHelper.getInstance().updateMyCard(getDtoOfScreenDataWithId(mPokemonDto.getId()))) {
                            toggleShouldEdit();
                            getActivity().supportInvalidateOptionsMenu();
                            progressDialog.dismiss();
                            if (fragment != null) {
                                ((MainActivity) getActivity()).doFragTransaction(fragment, 0);
                            }
                        }
                    }
                };
                handler.sendEmptyMessageDelayed(0, 800);
            }
        });
        builder.setNeutralButton("Don't Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (fragment != null) {
                    ((MainActivity) getActivity()).doFragTransaction(fragment, 0);
                }
            }
        });
        builder.show();
    }

    private PokemonDto getDtoOfScreenDataWithId(int id) {
        mPokemonDto = new PokemonDto();
        mPokemonDto.setId(id);
        mPokemonDto.setName(pokemonName.getText().toString());
        mPokemonDto.setHp(Integer.parseInt((pokemonHp.getText().toString().substring(0, pokemonHp.getText().toString().length() - 2)).trim()));
        mPokemonDto.setType(pokemonType.getText().toString());
        mPokemonDto.setWeight(Integer.parseInt((pokemonWeight.getText().toString().substring(0, pokemonWeight.getText().toString().length() - 3)).trim()));
        mPokemonDto.setHeight(Integer.parseInt((pokemonHeight.getText().toString().substring(0, pokemonHeight.getText().toString().length() - 4)).trim()));
        mPokemonDto.setDesc(pokemonDesc.getText().toString());
        mPokemonDto.setLevel(Integer.parseInt((pokemonLvl.getText().toString().substring(3)).trim()));
        mPokemonDto.setBitmapPath(writeToFile(pokemonImageBitmap, mPokemonDto.getId()).getAbsolutePath());
        mPokemonDto.setImagePath("-1");
        return mPokemonDto;
    }

    private PokemonDto getDtoOfScreenData() {
        mPokemonDto = new PokemonDto();
        mPokemonDto.setId(Utils.getRandomId());
        mPokemonDto.setName(pokemonName.getText().toString());
        mPokemonDto.setHp(Integer.parseInt((pokemonHp.getText().toString().substring(0, pokemonHp.getText().toString().length() - 2)).trim()));
        mPokemonDto.setType(pokemonType.getText().toString());
        mPokemonDto.setWeight(Integer.parseInt((pokemonWeight.getText().toString().substring(0, pokemonWeight.getText().toString().length() - 3)).trim()));
        mPokemonDto.setHeight(Integer.parseInt((pokemonHeight.getText().toString().substring(0, pokemonHeight.getText().toString().length() - 4)).trim()));
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

    public void deleteCard() {
        startProgressDialog("Deleting the pokemon");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        dismissListener();
                    }
                });
                temp = DbHelper.getInstance().deleteCard(mPokemonDto.getId());
                Log.d("TAG", "temp" + temp + "");
                progressDialog.dismiss();
            }
        });
        executorService.shutdown();

    }

    private void dismissListener() {
        if (temp == 0) {
            Toast.makeText(getActivity(), "Oops we couldn't delete the pokemon.", Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).doFragTransaction(new MyCardListFragment(), 0);
        } else {
            ((MainActivity) getActivity()).doFragTransaction(new MyCardListFragment(), 0);
        }
    }

    private void startProgressDialog(String title) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void saveAndToggle() {
        saveMyCard(null);
    }

    public void saveAndToggleAndChange(Fragment fragment) {
        saveMyCard(fragment);
    }

    public void toggleShouldEdit() {
        materialDialogCreator.setShouldEdit();
    }

    public Boolean getEditing() {
        return materialDialogCreator.getShouldEdit();
    }

}
