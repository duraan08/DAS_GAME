package com.example.juego_das;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void comenzar(View view){
        Intent inicio = new Intent(MainMenuActivity.this, InicioSesionActivity.class);
        startActivity(inicio);
    }

    public void instrucciones(View view){
        DialogoInstrucciones instrucciones = new DialogoInstrucciones();
        instrucciones.show(getSupportFragmentManager(), "instrucciones");
    }
}