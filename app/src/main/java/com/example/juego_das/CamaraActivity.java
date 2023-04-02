package com.example.juego_das;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CamaraActivity extends AppCompatActivity {
    //Codigo extraido de --> https://youtu.be/kebuipmQliE; Autor --> Códigos de Programación
    Button btnCamara;
    ImageView img;
    Bundle extras;
    Bitmap imagenBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        btnCamara = findViewById(R.id.btnCamara);
        img = findViewById(R.id.imagenCamara);

        if (savedInstanceState != null){
            extras = savedInstanceState.getBundle("Imagen");
            imagenBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imagenBitmap);
        }
    }

    public void utilizarCamara(View view){
        abrirCamara();
    }

    //Iniciamos la app de la camara para sacar fotos
    private void abrirCamara(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null){
            startActivityForResult(i, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            extras = data.getExtras();
            //Mapeamos la imagen para poder colocarla en el ImageView
            imagenBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imagenBitmap);
        }
    }

    public void enviarImagen(View view){
        Toast.makeText(CamaraActivity.this, "Se enviaría la imagen al servidor", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("Imagen", extras);
    }
}