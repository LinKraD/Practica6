package net.iesseveroochoa.gabrielvidal.practica6.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.iesseveroochoa.gabrielvidal.practica6.Model.Pokemon;
import net.iesseveroochoa.gabrielvidal.practica6.repository.PokemonRepository;

import java.util.List;

public class PokemonViewModel extends AndroidViewModel {
    private PokemonRepository mRepository;
    private LiveData<List<Pokemon>> mAllPokemons;
    private MutableLiveData<Boolean> cargandoDatos;

    public PokemonViewModel(@NonNull Application application) {
        super(application);
        mRepository = PokemonRepository.getInstance(application);
        mAllPokemons=mRepository.getAllPokemons();
    }

    public LiveData<List<Pokemon>> getAllPokemons(){
        return mAllPokemons;
    }

    public void insert(Pokemon pokemon){
        mRepository.insert(pokemon);
    }

    public void delete(Pokemon pokemon){
        mRepository.delete(pokemon);
    }
}
