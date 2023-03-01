package com.example.juego_das;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class RuletaActivity extends AppCompatActivity {
    String premiado, multi_word;
    ArrayList<String> listaPart = new ArrayList<String>();

    //Codigo obtenido de --> https://youtu.be/y1fptOfsIRs : Autor --> TechPot
    //Valores de la ruleta
    final String[] sectors = {"rosa", "naranja", "azul", "verde", "rosa", "naranja", "azul", "verde"};
    final int[] sectorsDegrees = new int[sectors.length];

    //Indice Aleatorio
    int randomSectorIndex, multi, numTirada;

    //Lo que va a GIRAR
    ImageView wheel;
    boolean girando;
    TextView mult, turno;

    //Generar la aleatoriedad
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);

        //Gestion del giro de pantalla para que se mantenga la animacion
        if (savedInstanceState != null){
            multi = savedInstanceState.getInt("tragos");
            premiado = savedInstanceState.getString("participante");
            girando = savedInstanceState.getBoolean("giro");
            numTirada = savedInstanceState.getInt("numTirada");
        }

        if (!girando){
            mult = findViewById(R.id.trago);
            turno = findViewById(R.id.turno);
            multi_word = String.valueOf(multi);
            mult.setText(multi_word);
            turno.setText(premiado);
        }


        //Lo que gira
        wheel = findViewById(R.id.wheel);

        //Generar los grados de cada sector
        generateSectorDegrees();

        //Click en el boton para que gire
        Button spin = findViewById(R.id.spin_button);
        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Solo si no gira
                if (!girando){
                    girar();
                    girando = true;
                    numTirada ++;
                }
            }
        });

    }

    private void girar() {
        //Obetener cualquier indice aleatorio
        double randomIndex = Math.random()*sectors.length;
        if (Math.round(randomIndex) == 8){
            randomIndex = 6.8;
        }
        randomSectorIndex = (int) Math.round(randomIndex);

        //Generar un grado aleatorio
        int randomDegree = generateRandomDegreeToSpin();

        //Animacion de rotacion
        RotateAnimation rotateAnimation = new RotateAnimation(0, randomDegree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        //Tiempo
        rotateAnimation.setDuration(3600); //3.6 sec
        rotateAnimation.setFillAfter(true);

        //Interpolator
        rotateAnimation.setInterpolator(new DecelerateInterpolator()); //Rapido al inicio, despacio al final

        //Spinning Listener
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //Resetear los valores
                mult = findViewById(R.id.trago);
                turno = findViewById(R.id.turno);
                mult.setText("0");
                turno.setText(" ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Para cuando el giro pare
                //Participante aleatorio
                Bundle extrasPart = getIntent().getExtras();
                if (extrasPart != null){
                    listaPart = extrasPart.getStringArrayList("participantes");
                }
                double partRandom = Math.random()*listaPart.size();
                if (Math.round(partRandom) == listaPart.size()){
                    partRandom = listaPart.size() - 1.2;
                }
                int part = (int) Math.round(partRandom);
                premiado = listaPart.get(part);

                //Toast que desvela el multiplicador
                double numRandom = Math.random()*12;
                multi = (int) Math.round(numRandom);

                TextView mult = findViewById(R.id.trago);
                TextView turno = findViewById(R.id.turno);
                String multi_word = String.valueOf(multi);
                mult.setText(multi_word);
                turno.setText(premiado);

                //Añadimos al fichero (historial_Tiradas) la informacion de la tirada
                try {
                    OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput("historial_tiradas.txt",
                            Context.MODE_APPEND));
                    fichero.write("\n\nTirada numero " + numTirada + ": \nPremiado : " + premiado + " Tragos (sin x2) --> " + multi_word);
                    fichero.close();
                } catch (IOException e){e.printStackTrace();}
                //Fin Giro
                girando = false;

                //Alerta de dialogo para participar en un doble o nada
                AlertDialog.Builder builder = new AlertDialog.Builder(RuletaActivity.this);
                builder.setTitle("Tu número de tragos es : " + multi_word);
                builder.setMessage("¿Quieres jugartela a Doble o Nada?");
                builder.setPositiveButton(" ¡Claro que sí!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent dobleNada = new Intent(RuletaActivity.this, DobleNadaActivity.class);
                        dobleNada.putExtra("tragos", multi_word);
                        startActivity(dobleNada);
                    }
                });
                builder.setNegativeButton("Soy un gallina ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        //Aplicar la animacion a la imagen
        wheel.startAnimation(rotateAnimation);

    }

    private int generateRandomDegreeToSpin() {
        //Un numero lo mas grande posible
        return (360*sectors.length) + sectorsDegrees[randomSectorIndex];
    }

    private void generateSectorDegrees() {
        //Sector 1
        int sectorDegree = 360/sectors.length;

        for (int i = 0; i < sectors.length; i++ ){
            sectorsDegrees[i] = (i+1) * sectorDegree;
        }
    }

    @Override
    //Guardamos los valores para poder gestionar el giro de pnatalla
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("participante", premiado);
        outState.putInt("numTirada", numTirada);
        outState.putInt("tragos", multi);
        outState.putBoolean("giro", girando);
    }
}