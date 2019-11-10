package org.izv.pgc.pokemonroomdb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.izv.pgc.pokemonroomdb.entity.Pokemon;
import org.izv.pgc.pokemonroomdb.view.MainViewModel;
import org.izv.pgc.pokemonroomdb.view.PokemonAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ID_PERMISO_LEER_STORAGE = 1;
    public static MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);

            }
        });

        pedirPermisos();

        RecyclerView rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new GridLayoutManager(this, 4));

        final PokemonAdapter adapter = new PokemonAdapter(this);
        rvList.setAdapter(adapter);

        //viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getPokemons().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(@Nullable final List<Pokemon> pokemons) {
                // Update the cached copy of the words in the adapter.
                adapter.setPokemons(pokemons);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pedirPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //realizar accion
            Toast.makeText(this, "CARGANDO", Toast.LENGTH_SHORT).show();
        } else {
            // AQUI SE COMPRUEBA SI LA APP TIENE PERMISOS PARA LO QUE SOLICITAMOS
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // SI NO TUVIERA PERMISO LA APP VOLVERA A PEDIRLA
                // DEBERIA VOLVER A PREGUNTAR POR EL PERMISO
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    Toast.makeText(this, "LO NECESITO", Toast.LENGTH_LONG).show();
                    // 2º VEZ QUE LE PIDO PERMISO AL USUARIO
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            ID_PERMISO_LEER_STORAGE);

                } else {
                    // ES LA 1º VEZ QUE PIDO PERMISO
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            ID_PERMISO_LEER_STORAGE);
                }
            } else {
                // Tengo permiso por lo que realizo la acción
                Toast.makeText(this, "CARGANDO", Toast.LENGTH_SHORT).show();
            }
        }
    }

}