package com.pokemonify.pokemonify.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokemonify.pokemonify.R;
import com.pokemonify.pokemonify.pokemondatabase.PokemonDto;

import java.util.List;

/**
 * Created by gaurav on 23/7/16.
 */
public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.MyViewHolder> {

    List<PokemonDto> pokeList;
    Context mContext;
    Bitmap pokeImage;

    public PokemonListAdapter(Context c, List<PokemonDto> pokeList) {
        this.pokeList = pokeList;
        mContext = c;
    }

    public void setPokeList(List<PokemonDto> pokeList) {
        this.pokeList = pokeList;
    }

    public List<PokemonDto> getPokeList() {
        return pokeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(pokeList.get(position).getName());
        pokeImage = BitmapFactory.decodeResource(mContext.getResources(), mContext.getResources()
                .getIdentifier(pokeList.get(position).getImagePath(), "drawable", mContext.
                        getPackageName()));
        holder.image.setImageBitmap(pokeImage);
    }

    @Override
    public int getItemCount() {
        return pokeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.pokemonItemName);
            image = (ImageView) itemView.findViewById(R.id.pokemonItemImage);
        }
    }
}
