package net.iesseveroochoa.gabrielvidal.practica6.pokemonapi;


import com.google.gson.annotations.SerializedName;

import net.iesseveroochoa.gabrielvidal.practica6.Model.Pokemon;

import java.util.Date;

public class Pokemonapi {
    @SerializedName("name")
    private String nombrePokemon;
    private String url;

    public String getNombrePokemon() {
        return nombrePokemon;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Pokemon pokemonApiToPokemon(){
        int id;
        String[] urlPartes = url.split("/");
        id= Integer.parseInt(urlPartes[urlPartes.length - 1]);
        return new Pokemon(id,nombrePokemon,new Date());
    }

}
