package net.iesseveroochoa.gabrielvidal.practica6.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.iesseveroochoa.gabrielvidal.practica6.Model.Pokemon;
import net.iesseveroochoa.gabrielvidal.practica6.R;
import net.iesseveroochoa.gabrielvidal.practica6.adapters.PokemonApiAdapter;
import net.iesseveroochoa.gabrielvidal.practica6.repository.PokemonRepository;
import net.iesseveroochoa.gabrielvidal.practica6.utils.Utils;
import net.iesseveroochoa.gabrielvidal.practica6.viewmodels.PokemonApiViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NuevoPokemonActivity extends AppCompatActivity {
    public final static String EXTRA_POKEMON_ENVIADO = "anyade";

    private PokemonApiAdapter adapter;
    private PokemonApiViewModel pokemonViewModel;
    private RecyclerView rvListaPokemon;

    private ProgressBar pbCargandoDatos;
    private ImageView imComprar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pokemon);

        rvListaPokemon = findViewById(R.id.rvListaPokemon);
        adapter = new PokemonApiAdapter();
        rvListaPokemon.setAdapter(adapter);
        Utils.definirFormatoReciclerView(this, rvListaPokemon);

        pokemonViewModel= ViewModelProviders.of(this).get(PokemonApiViewModel.class);
        pokemonViewModel.getListaPokemonApi().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                adapter.setListaPokemon(pokemons);
            }
        });

        pokemonViewModel.getCargandoDatos().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    pbCargandoDatos.setVisibility(View.VISIBLE);
                } else {
                    pbCargandoDatos.setVisibility(View.GONE);
                }
            }
        });
        defineDetectarFinRecycler();
        pbCargandoDatos=findViewById(R.id.pbCargando);
        imComprar=findViewById(R.id.ivComprar);
        adapter.setOnItemComprarClickListener(new PokemonApiAdapter.onItemClickComprarListener() {
            @Override
            public void onItemComprarClick(final Pokemon pokemon) {
                AlertDialog.Builder dialogoComprar =new AlertDialog.Builder(NuevoPokemonActivity.this);

                dialogoComprar.setTitle("Comprar").setMessage("¿Seguro que desea comprar a "+pokemon.getNombre()+"?").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=getIntent();
                        i.putExtra(EXTRA_POKEMON_ENVIADO,pokemon);
                        setResult(NuevoPokemonActivity.RESULT_OK,i);
                        finish();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
            }
        });
    }

    private void defineDetectarFinRecycler() {
        rvListaPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)&&(newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    Log.v("scroll","fin");
//                    pbCargandoDatos.setVisibility(View.VISIBLE);
                    pokemonViewModel.getListaSiguientePokemonApi();
                }
            }
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
        });
    }

}
