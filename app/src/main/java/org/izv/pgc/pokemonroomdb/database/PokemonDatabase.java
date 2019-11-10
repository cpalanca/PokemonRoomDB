package org.izv.pgc.pokemonroomdb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import org.izv.pgc.pokemonroomdb.dao.PokemonDao;
import org.izv.pgc.pokemonroomdb.entity.Pokemon;

@Database(entities = {Pokemon.class}, version = 1, exportSchema = false)
public abstract class PokemonDatabase extends androidx.room.RoomDatabase {
    public abstract PokemonDao getPokemonDAO();
    private static PokemonDatabase INSTANCIA;

    public static PokemonDatabase getDatabase(final Context context) {
        if (INSTANCIA == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                            PokemonDatabase.class, "pokedex.sqlite")
                            .build();
                }
            }
        }
        return INSTANCIA;
    }
}
