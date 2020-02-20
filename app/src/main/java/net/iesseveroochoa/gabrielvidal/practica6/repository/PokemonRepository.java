package net.iesseveroochoa.gabrielvidal.practica6.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.iesseveroochoa.gabrielvidal.practica6.Model.Pokemon;
import net.iesseveroochoa.gabrielvidal.practica6.Model.PokemonDAO;
import net.iesseveroochoa.gabrielvidal.practica6.Model.PokemonDatabase;
import net.iesseveroochoa.gabrielvidal.practica6.pokemonapi.ListaPokemonapi;
import net.iesseveroochoa.gabrielvidal.practica6.pokemonapi.Pokemonapi;
import net.iesseveroochoa.gabrielvidal.practica6.pokemonapi.WebServicePokeApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonRepository {
    static String TAG="Pokemon:";
    private static volatile PokemonRepository INSTANCE;
    private PokemonDAO mPokemonDAO;
    private LiveData<List<Pokemon>> mAllPokemons;

    public static PokemonRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PokemonRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    private PokemonRepository(Application application){
        PokemonDatabase db=PokemonDatabase.getDatabase(application);
        mPokemonDAO =db.pokemonDao();
        mAllPokemons=mPokemonDAO.allPokemon();
    }

    public LiveData<List<Pokemon>> getAllPokemons(){
        return mAllPokemons;
    }

    private static class inserAsyncTask extends AsyncTask<Pokemon,Void,Void> {
        private PokemonDAO mAsyncTaskDao;
        public inserAsyncTask(PokemonDAO mPokemonDao) {
            mAsyncTaskDao=mPokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            mAsyncTaskDao.insert(pokemons[0]);
            return null;
        }
    }

    public void insert(Pokemon pokemon){
        new inserAsyncTask(mPokemonDAO).execute(pokemon);
    }

    public void delete(Pokemon pokemon){
        new deleteAsyncTask(mPokemonDAO).execute(pokemon);
    }
    private static class deleteAsyncTask extends AsyncTask<Pokemon,Void,Void> {
        private PokemonDAO mAsyncTaskDao;
        public deleteAsyncTask(PokemonDAO mPokemonDao) {
            mAsyncTaskDao=mPokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            mAsyncTaskDao.deleteByPokemon(pokemons[0]);
            return null;
        }
    }

    private final int limit=20;
    private Retrofit retrofit;
    private WebServicePokeApi servicioWebPokemon;
    private ArrayList<Pokemon> listaPokemonApi;
    private MutableLiveData<List<Pokemon>> listaPokemonApiLiveData;
    private MutableLiveData<Boolean> cargandoDatos;

    private void iniciaRetrofit()
    {
        retrofit= new Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        servicioWebPokemon=retrofit.create(WebServicePokeApi.class);
        listaPokemonApi=new ArrayList<Pokemon>();
        listaPokemonApiLiveData=new MutableLiveData<>();
        cargandoDatos=new MutableLiveData<>();
        cargandoDatos.setValue(false);
        getListaSiguientePokemonApi(0);
    }

    private ArrayList<Pokemon> transformaListaAPokemon(ArrayList<Pokemonapi> lista){
        ArrayList <Pokemon>  listaTransformada=new ArrayList<Pokemon>();
        Pokemon pokemon;for(Pokemonapi pokemonApi: lista){
            pokemon=pokemonApi.pokemonApiToPokemon();
            listaTransformada.add(pokemon);
            Log.d(TAG, pokemon.getId()+"-"+pokemon.getNombre() );
        }
        return listaTransformada;
    }

    public void getListaSiguientePokemonApi(final int offset){
        if(retrofit==null){
            iniciaRetrofit();
        }
        Call<ListaPokemonapi> listaPokemonApiCall=servicioWebPokemon.getListaPokemon(limit,offset);
        cargandoDatos.setValue(true);
        listaPokemonApiCall.enqueue(new Callback<ListaPokemonapi>() {
            @Override
            public void onResponse(Call<ListaPokemonapi> call, Response<ListaPokemonapi> response) {
                if (response.isSuccessful()) {Log.d(TAG, "Pokemons desde offset: " + offset);
                ListaPokemonapi pokemonRespuesta = response.body();
                ArrayList<Pokemon> lista=transformaListaAPokemon(pokemonRespuesta.getListaPokemon());
                listaPokemonApi.addAll(lista);
                listaPokemonApiLiveData.setValue(listaPokemonApi);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
                cargandoDatos.setValue(false);
            }

            @Override
            public void onFailure(Call<ListaPokemonapi> call, Throwable t) {
                cargandoDatos.setValue(false);
            }
        });
    }

    public MutableLiveData<List<Pokemon>> getListaPokemonApiLiveData() {
        if(retrofit==null){
            iniciaRetrofit();
        }
        return listaPokemonApiLiveData;
    }

    public MutableLiveData<Boolean> getCargandoDatos(){
        return cargandoDatos;
    }
}

