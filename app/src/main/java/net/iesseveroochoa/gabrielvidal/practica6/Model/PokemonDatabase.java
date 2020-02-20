package net.iesseveroochoa.gabrielvidal.practica6.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Pokemon.class}, version = 1)
@TypeConverters({TransformaFechaSqlLite.class})
public abstract class PokemonDatabase extends RoomDatabase {
    public abstract PokemonDAO pokemonDao();
    private static volatile PokemonDatabase INSTANCE;
    public static PokemonDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),PokemonDatabase.class, "pokemon_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
