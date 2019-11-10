package org.izv.pgc.pokemonroomdb.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.izv.pgc.pokemonroomdb.EditActivity;
import org.izv.pgc.pokemonroomdb.MainActivity;
import org.izv.pgc.pokemonroomdb.R;
import org.izv.pgc.pokemonroomdb.entity.Pokemon;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private LayoutInflater inflater;
    private List<Pokemon> pokemonsList;
    private int contador = 0;
    private MainViewModel viewModel;
    private Context context;

    public PokemonAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        viewModel = MainActivity.viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v("xyzyx", "onCreateViewHolder: " + contador);
        contador++;
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new PokemonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Log.v("xyzyx", "onBindViewHolder: " + contador);
        if (pokemonsList != null) {
            final Pokemon current = pokemonsList.get(position);
            String urlImage = current.getUrl();

            if(urlImage != null) {
                urlImage = current.getUrl();
                Log.v("xyzyx", "urlImage: " + urlImage);
            } else {
                urlImage = "android.resource://" + context.getPackageName() + "/" + R.drawable.pokeball;
            }

            Glide.with(context)
                    .load(urlImage)
                    .into(holder.imgPokemon);

            holder.tvItem.setText(current.getName());

            holder.imgPokemon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pokemon", current);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            holder.imgPokemon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    viewModel.delete(current);
                    notifyDataSetChanged();
                    Toast.makeText(context, "pokemon removed", Toast.LENGTH_LONG).show();
                    return true;
                }
            });

        } else {
            holder.tvItem.setText("No pokemon available");
        }
    }


    @Override
    public int getItemCount() {
        Log.v("xyzyx", "getItemCount: " + contador);
        int elementos = 0;
        if(pokemonsList != null) {
            elementos = pokemonsList.size();
        }
        return elementos;
    }

    public void setPokemons(List<Pokemon> pokemonsList){
        this.pokemonsList = pokemonsList;
        notifyDataSetChanged();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;
        private final ImageView imgPokemon;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvNombre);
            imgPokemon = itemView.findViewById(R.id.imgPokemon);
        }
    }
}
