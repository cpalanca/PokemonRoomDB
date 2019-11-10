package org.izv.pgc.pokemonroomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
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

public class InsertActivity extends AppCompatActivity {

    private EditText etNombre, etNoficial, etType, etHeight, etWeight, etAbility;
    private Button btSave;
    private MainViewModel viewModel;
    private ImageView imageView;
    private String imagen;
    private static final int PHOTO_SELECTED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        initComponents();
        initEvents();
    }

    private void initEvents() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pokemon p = new Pokemon();
                p.setName(etNombre.getText().toString());
                p.setNumoficial(Integer.parseInt(etNoficial.getText().toString()));
                p.setType(etType.getText().toString());
                p.setWeight(Float.parseFloat(etWeight.getText().toString()));
                p.setHeight(Float.parseFloat(etHeight.getText().toString()));
                p.setAbility(etAbility.getText().toString());
                p.setUrl(imagen);
                Log.v("POKEDEX", ""+p.toString());
                viewModel.insert(p);
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

