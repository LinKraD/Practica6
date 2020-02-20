package net.iesseveroochoa.gabrielvidal.practica6.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import net.iesseveroochoa.gabrielvidal.practica6.Model.Pokemon;
import net.iesseveroochoa.gabrielvidal.practica6.R;
import net.iesseveroochoa.gabrielvidal.practica6.adapters.PokemonAdapter;
import net.iesseveroochoa.gabrielvidal.practica6.viewmodels.PokemonViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static net.iesseveroochoa.gabrielvidal.practica6.utils.Utils.definirFormatoReciclerView;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_POKEMON="anyade";

    private static final int COMPRA_POKEMON=0;

    private PokemonViewModel pokemonViewModel;
    private FloatingActionButton fabNuevo;
    private RecyclerView rvListaPokemon;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvListaPokemon = findViewById(R.id.rvListaPokemon);
        adapter = new PokemonAdapter();
        rvListaPokemon.setAdapter(adapter);
        //rvListaPokemon.setLayoutManager(new LinearLayoutManager(this));
        definirFormatoReciclerView(this,rvListaPokemon);
        definirEventoSwiper();

        pokemonViewModel= ViewModelProviders.of(this).get(PokemonViewModel.class);

        adapter.setOnItemBorrarClickListener(new PokemonAdapter.onItemClickBorrarListener() {
            @Override
            public void onItemBorrarClick(final Pokemon pokemon) {
                AlertDialog.Builder dialogoBorrar=new AlertDialog.Builder(MainActivity.this);
                dialogoBorrar.setTitle(R.string.borrar).setMessage(getString(R.string.seguro)+pokemon.getNombre()+"?")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pokemonViewModel.delete(pokemon);

                            }
                        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                        .show();
            }
        });

        pokemonViewModel.getAllPokemons().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                adapter.setListaPokemon(pokemons);
            }});


        fabNuevo = findViewById(R.id.fabNuevo);
        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NuevoPokemonActivity.class);
                startActivityForResult(intent,COMPRA_POKEMON);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_prueba_pokemon:
                cargaPokemonEjemplo();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void definirEventoSwiper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir){
                final Pokemon pokemonDelete=((PokemonAdapter.PokemonViewHolder)viewHolder).getPokemon();
                AlertDialog.Builder dialogoBorrar=new AlertDialog.Builder(MainActivity.this);
                dialogoBorrar.setTitle(R.string.borrar).setMessage(getString(R.string.seguro)+pokemonDelete.getNombre()+" ?")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pokemonViewModel.delete(pokemonDelete);

                            }
                        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                        .show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvListaPokemon);
    }

    private void cargaPokemonEjemplo() {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Pokemon pokemon;
        try {
            pokemon = new Pokemon(1, "bulbasaur", formatoDelTexto.parse("10-10-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon = new Pokemon(2, "ivysaur", formatoDelTexto.parse("11-10-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon = new Pokemon(3, "venusaur", formatoDelTexto.parse("12-11-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon = new Pokemon(4, "charmander", formatoDelTexto.parse("12-9-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon = new Pokemon(5, "charmeleon", formatoDelTexto.parse("12-5-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon = new Pokemon(6, "charizard", formatoDelTexto.parse("8-3-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon = new Pokemon(7, "squirtle", formatoDelTexto.parse("1-1-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(8,"wartortle",formatoDelTexto.parse("13-3-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(9,"blastoise",formatoDelTexto.parse("16-4-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(10,"caterpie",formatoDelTexto.parse("2-5-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(11,"metapod",formatoDelTexto.parse("6-7-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(12,"butterfree",formatoDelTexto.parse("20-2-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(13,"weedle",formatoDelTexto.parse("20-1-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(14,"kakuna",formatoDelTexto.parse("20-3-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(15,"beedrill",formatoDelTexto.parse("20-4-2019"));
            pokemonViewModel.insert(pokemon);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Pokemon pokemon=data.getParcelableExtra(EXTRA_POKEMON);
        pokemonViewModel.insert(pokemon);
    }
}
