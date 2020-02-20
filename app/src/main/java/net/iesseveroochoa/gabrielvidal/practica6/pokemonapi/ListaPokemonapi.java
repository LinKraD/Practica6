package net.iesseveroochoa.gabrielvidal.practica6.pokemonapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListaPokemonapi {

    @SerializedName("next")
    private String uriSiguientes;
    @SerializedName("results")
    private ArrayList<Pokemonapi> listaPokemon;

    public String getUriSiguientes() {
        return uriSiguientes;
    }

    public void setUriSiguientes(String uriSiguientes) {
        this.uriSiguientes = uriSiguientes;
    }

    public ArrayList<Pokemonapi> getListaPokemon() {
        return listaPokemon;
    }

    public void setListaPokemon(ArrayList<Pokemonapi> listaPokemon) {
        this.listaPokemon = listaPokemon;
    }

}
