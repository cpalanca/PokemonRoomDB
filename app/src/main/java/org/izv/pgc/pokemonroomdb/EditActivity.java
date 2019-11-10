package org.izv.pgc.pokemonroomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.izv.pgc.pokemonroomdb.entity.Pokemon;
import org.izv.pgc.pokemonroomdb.view.MainViewModel;

public class EditActivity extends AppCompatActivity {

    private EditText etNombre, etNoficial, etType, etHeight, etWeight, etAbility;
    private Button btSave;
    private MainViewModel viewModel;
    private ImageView imageView;
    private String nombre, type, ability, imagen;
    private Float weight, height;
    private Integer numoficial;
    private Pokemon pokemon;
    private Context context;
    private static final int PHOTO_SELECTED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initBlundeReciver();
        initComponents();
        initEvents();
    }

    private void initBlundeReciver() {
        Bundle pokemonFecht = getIntent().getExtras();
        pokemon = null;
        if(pokemonFecht!=null){
            pokemon = (Pokemon) pokemonFecht.getSerializable("pokemon");

            nombre = pokemon.getName();
            numoficial= pokemon.getNumoficial();
            type = pokemon.getType();
            weight = pokemon.getWeight();
            height = pokemon.getHeight();
            ability = pokemon.getAbility();
            imagen = pokemon.getUrl();

        }
    }

    private void initEvents() {

        //SET THE DATA
        etNombre.setText(nombre);
        etNoficial.setText(""+numoficial);
        etType.setText(""+type);
        etHeight.setText(""+height);
        etWeight.setText(""+weight);
        etAbility.setText(""+ability);

        if (imagen == null){
            imageView.setImageResource(R.drawable.pokeball);
        }else{
            Uri imageUri = Uri.parse(imagen);
            Glide.with(this).load(imageUri).into(imageView);
        }


        //imageView.setImageURI(Uri.parse(imagen));

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = pokemon.getId();
                pokemon.setId(id);
                pokemon.setName(etNombre.getText().toString());
                pokemon.setNumoficial(Integer.parseInt(etNoficial.getText().toString()));
                pokemon.setType(etType.getText().toString());
                pokemon.setWeight(Float.parseFloat(etWeight.getText().toString()));
                pokemon.setHeight(Float.parseFloat(etHeight.getText().toString()));
                pokemon.setAbility(etAbility.getText().toString());
                pokemon.setUrl(imagen);
                Log.v("POKEDEX", ""+pokemon.toString());
                viewModel.edit(pokemon);
                volverMain();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PHOTO_SELECTED);
            }
        });

    }

    private void volverMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void initComponents() {
        etNombre = findViewById(R.id.etName);
        etNoficial = findViewById(R.id.etNoficial);
        etType = findViewById(R.id.etType);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        etAbility = findViewById(R.id.etAbility);
        btSave = findViewById(R.id.btSave);
        imageView = findViewById(R.id.imageView);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SELECTED && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Glide.with(this).load(imageUri).into(imageView);
            imagen = imageUri.toString();

        }
    }
}
