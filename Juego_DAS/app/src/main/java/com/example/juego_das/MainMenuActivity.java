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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);

        builder.setTitle("Instrucciones");
        builder.setMessage("Cualquier Jugador hace girar la ruleta. De manera aleatoria la app elegirá a" +
                "uno de los participantes y le asiganrá los tragos que le correspondan. El elegido tiene opción" +
                "de jugar a Doble o Nada; esa decir, se abrirá un minijuego el cual consistira en para el cronometro" +
                "en el instante exacto de tiempo (0:01:00) si el jugador lo consigue se salva, si no sus tragos se" +
                "duplicarán.");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}