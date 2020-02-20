package net.iesseveroochoa.gabrielvidal.practica6.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.iesseveroochoa.gabrielvidal.practica6.Model.Pokemon;
import net.iesseveroochoa.gabrielvidal.practica6.repository.PokemonRepository;

import java.util.List;

public class PokemonApiViewModel extends AndroidViewModel {
    private PokemonRepository mRepository;
    private LiveData<List<Pokemon>> mAllPokemons;
    private LiveData<Boolean> cargandoDatos;

    public PokemonApiViewModel(@NonNull Application application) {
        super(application);
        mRepository=PokemonRepository.getInstance(application);
        mAllPokemons=mRepository.getListaPokemonApiLiveData();
    }

    public void getListaSiguientePokemonApi(){
        List<Pokemon> listaPokemon= mAllPokemons.getValue();
        Pokemon ultimoPokemon= listaPokemon.get(listaPokemon.size()-1);
        int pokemonIndiceDesde=ultimoPokemon.getId();
        mRepository.getListaSiguientePokemonApi(pokemonIndiceDesde);
    }

    public LiveData<List<Pokemon>> getListaPokemonApi(){
        return mAllPokemons;
    }

    public LiveData<Boolean> getCargandoDatos() {
        return mRepository.getCargandoDatos();
    }
}
