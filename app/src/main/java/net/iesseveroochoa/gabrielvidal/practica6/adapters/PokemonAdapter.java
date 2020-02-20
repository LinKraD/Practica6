package net.iesseveroochoa.gabrielvidal.practica6.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.iesseveroochoa.gabrielvidal.practica6.Model.Pokemon;
import net.iesseveroochoa.gabrielvidal.practica6.R;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>{

    private List<Pokemon> listaPokemon;
    private onItemClickBorrarListener listener;

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder holder, int position) {
        if (listaPokemon != null) {
            final Pokemon pokemon = listaPokemon.get(position);
            holder.tvNombre.setText(pokemon.getNombre());
            holder.tvFechaCompra.setText(pokemon.getFechaCompraFormatoLocal());
            cargaImagen(holder.ivImagenPokemon, pokemon.getUri());
            holder.pokemon=pokemon;
        }
    }

    @Override
    public int getItemCount() {
        if (listaPokemon != null) {
            return listaPokemon.size();
        }else{
            return 0;
        }
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private ImageView ivImagenPokemon;
        private TextView tvFechaCompra;
        private ImageView ivBorrar;
        private Pokemon pokemon;

        public PokemonViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFechaCompra = itemView.findViewById(R.id.tvFechaCompra);
            ivImagenPokemon = itemView.findViewById(R.id.ivImagenPokemon);
            ivBorrar = itemView.findViewById(R.id.ivBorrar);

            ivBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){listener.onItemBorrarClick(pokemon);
                    }
                }
            });
        }
        public Pokemon getPokemon() {
            return pokemon;
        }
    }

    public void setListaPokemon(List<Pokemon> pokemons){
        listaPokemon=pokemons;notifyDataSetChanged();
    }

    private void cargaImagen(ImageView ivImagenPokemon, String uri) {
        Glide

        .with(ivImagenPokemon.getContext())
        .load(uri)
        .centerCrop()
        .placeholder(android.R.drawable.stat_notify_sync_noanim)
        .error(android.R.drawable.ic_lock_lock)
        .into(ivImagenPokemon);
    }

    public interface onItemClickBorrarListener {
        void onItemBorrarClick(Pokemon pokemon);
    }

    public void setOnItemBorrarClickListener(onItemClickBorrarListener listener) {
        this.listener = listener;
    }
}
