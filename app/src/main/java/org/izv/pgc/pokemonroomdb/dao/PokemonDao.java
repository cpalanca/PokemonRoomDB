package org.izv.pgc.pokemonroomdb.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.izv.pgc.pokemonroomdb.entity.Pokemon;

import java.util.List;

@Dao
public interface PokemonDao {

// CRUD - CREATE-READ-UPDATE-DELETE

    @Delete
    int delete(Pokemon pokemon);

    @Update
    int edit(Pokemon pokemon);

    @Insert
    long insert(Pokemon pokemon);


    @Query("select * from pokemon where id = :id")
    Pokemon get(long id);

    @Query("select * from pokemon order by numoficial")
    List<Pokemon> getAll();

    @Query("select * from pokemon order by numoficial")
    LiveData<List<Pokemon>> getAllLive();
}
