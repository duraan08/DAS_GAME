package com.example.juego_das;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class RuletaActivity extends AppCompatActivity {
    String requiredValue;
    ArrayList<String> listaPart = new ArrayList<String>();

    //Codigo obtenido de --> https://youtu.be/y1fptOfsIRs : Autor --> TechPot
    //Valores de la ruleta
    final String[] sectors = {"rosa", "naranja", "azul", "verde", "rosa", "naranja", "azul", "verde"};
    final int[] sectorsDegrees = new int[sectors.length];

    //Indice Aleatorio
    int randomSectorIndex = 0;

    //Lo que va a GIRAR
    ImageView wheel;
    boolean girando = false;

    //Generar la aleatoriedad
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta);

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
                TextView mult = findViewById(R.id.trago);
                TextView turno = findViewById(R.id.turno);
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
                String p = listaPart.get(part);

                //Toast que desvela el multiplicador
                double numRandom = Math.random()*12;
                int multi = (int) Math.round(numRandom);

                TextView mult = findViewById(R.id.trago);
                TextView turno = findViewById(R.id.turno);
                String multi_word = String.valueOf(multi);
                mult.setText(multi_word);
                turno.setText(p);

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
                        //startActivityIntent.launch(dobleNada);
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

    /*ActivityResultLauncher<Intent> startActivityIntent =
            registerForActivityResult(new
                            ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK){
                                requiredValue = result.getData().getStringExtra("resultado");
                            }
                        }
                    });*/

}