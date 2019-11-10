package org.izv.pgc.pokemonroomdb.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.izv.pgc.pokemonroomdb.dao.PokemonDao;
import org.izv.pgc.pokemonroomdb.database.PokemonDatabase;
import org.izv.pgc.pokemonroomdb.entity.Pokemon;

import java.util.List;

public class Repository {

    private PokemonDao pokemonDao;
    private LiveData<List<Pokemon>> pokemonsLive;

    public Repository(Context contexto) {
        PokemonDatabase db = PokemonDatabase.getDatabase(contexto);
        pokemonDao = db.getPokemonDAO();
        pokemonsLive = pokemonDao.getAllLive();
    }

    public LiveData<List<Pokemon>> getPokemonsLive() {
        return pokemonsLive;
    }

    public void insertPokemon(Pokemon pokemon) {
        new InsertThread().execute(pokemon);
    }

    public void deletePokemon(Pokemon pokemon) {
        new DeleteThread().execute(pokemon);
    }

    public void editPokemon(Pokemon pokemon) {
        new EditThread().execute(pokemon);
    }

    private class InsertThread extends AsyncTask<Pokemon, Void, Void> {

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            pokemonDao.insert(pokemons[0]);
            Log.v("xyz", pokemons[0].toString());
            return null;
        }
    }

    private class DeleteThread extends AsyncTask<Pokemon, Void, Void> {

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            pokemonDao.delete(pokemons[0]);
            Log.v("xyz", pokemons[0].toString());
            return null;
        }
    }

    private class EditThread extends AsyncTask<Pokemon, Void, Void> {

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            pokemonDao.edit(pokemons[0]);
            Log.v("xyz", pokemons[0].toString());
            return null;
        }
    }
}