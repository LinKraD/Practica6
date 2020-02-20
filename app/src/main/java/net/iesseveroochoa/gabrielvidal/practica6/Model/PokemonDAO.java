package net.iesseveroochoa.gabrielvidal.practica6.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PokemonDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)void insert(Pokemon pokemon);

    @Delete
    void deleteByPokemon(Pokemon pokemon);

    @Query("DELETE FROM "+Pokemon.TABLE_NAME)
    void deleteAll();

    @Update
    void update(Pokemon pokemon);

    @Query("SELECT * FROM "+Pokemon.TABLE_NAME+" ORDER BY "+Pokemon.NOMBRE)
    LiveData<List<Pokemon>> allPokemon();

    @Query("SELECT * FROM "+Pokemon.TABLE_NAME+" WHERE :nombre LIKE "+Pokemon.NOMBRE)
    LiveData<List<Pokemon>> getByNombre(String nombre);
}
