package com.pokemonify.pokemonify.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pokemonify.pokemonify.MainActivity;
import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.UIComponents.CommonAdapter;
import com.pokemonify.pokemonify.Utils;
import com.pokemonify.pokemonify.pokemondatabase.DbHelper;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;
import com.pokemonify.pokemonify.recyclerviewcomponents.ItemClickSupport;
import com.pokemonify.pokemonify.recyclerviewcomponents.PokemonListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyCardListFragment extends Fragment implements CommonAdapter.OnGetViewListener<String> {
    PokemonListAdapter mPokemonListAdapter;
    RecyclerView mRecyclerView;
    List<PokemonDto> nameList;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;
    int temp = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("My Cards");
        nameList = DbHelper.getInstance().getAllMyCards();
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.pokemonRecyclerView);
        mPokemonListAdapter = new PokemonListAdapter(getActivity(), nameList);
        mRecyclerView.setAdapter(mPokemonListAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select action");
                final CommonAdapter<String> commonAdapter = new CommonAdapter<>(MyCardListFragment.this);
                final List<String> list = new ArrayList<>();
                list.add("Set as my pokemon");
                list.add("Delete this card");
                commonAdapter.setList(list);
                builder.setAdapter(commonAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (list.get(i).equals("Set as my pokemon")) {
                            progressDialog2 = new ProgressDialog(getActivity());
                            progressDialog2.setTitle("Making this your current pokemon");
                            progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog2.setCancelable(false);
                            progressDialog2.show();
                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    if (Utils.setMyPokemon(mPokemonListAdapter.getPokeList().get(position)
                                            , getActivity())) {
                                        progressDialog2.dismiss();
                                    }
                                }
                            });
                            executorService.shutdown();
                        } else {
                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("Making this your current pokemon");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            progressDialog.show();

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
                                    temp = DbHelper.getInstance().deleteCard(mPokemonListAdapter.
                                            getPokeList().get(position).getId());
                                    Log.d("TAG", "temp" + temp + "");
                                    progressDialog.dismiss();
                                }
                            });
                            executorService.shutdown();
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                PokemonDto clickedPokemon = mPokemonListAdapter.getPokeList().get(position);
                MainActivity mainActivity = (MainActivity) getActivity();
                Bundle bundle = new Bundle();
                bundle.putSerializable("PokemonDto", clickedPokemon);
                MyCardDetailFragment detailFragment = new MyCardDetailFragment();
                detailFragment.setArguments(bundle);
                Utils.hideKeyboard(mainActivity);
                mainActivity.hideSearch();
                mainActivity.changeFrag(detailFragment);
            }
        });
    }

    private void dismissListener() {
        if (temp == 0) {
            Toast.makeText(getActivity(), "Oops we couldn't delete the pokemon.", Toast.LENGTH_SHORT).show();
        } else {
            mPokemonListAdapter.notifyDataSetChanged();
            /*mPokemonListAdapter.setPokeList(DbHelper.getInstance().getAllMyCards());
            mPokemonListAdapter.notifyDataSetChanged();*/
        }
    }

    @Override
    public View getView(View convertView, String item, int position) {
        MyDialogViewHolder myDialogViewHolder;
        if (convertView == null) {
            myDialogViewHolder = new MyDialogViewHolder();
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_imagepicker, null);
            myDialogViewHolder.mTextView = (TextView) convertView.findViewById(R.id.dialogListText);
            myDialogViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.dialogListImage);
            convertView.setTag(myDialogViewHolder);
        } else {
            myDialogViewHolder = (MyDialogViewHolder) convertView.getTag();
        }
        if (item.equals("Set as my pokemon")) {
            myDialogViewHolder.mTextView.setText("Set as my pokemon");
            myDialogViewHolder.mImageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_set_as));
        } else {
            myDialogViewHolder.mTextView.setText("Delete this card");
            myDialogViewHolder.mImageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_delete));
        }
        return convertView;
    }

    private class MyDialogViewHolder {
        TextView mTextView;
        ImageView mImageView;
    }
}
