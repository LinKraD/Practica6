package net.iesseveroochoa.gabrielvidal.practica6.pokemonapi;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServicePokeApi {
    @GET("pokemon")
    Call<ListaPokemonapi> getListaPokemon(@Query("limit") int limit, @Query("offset") int offset);
}
