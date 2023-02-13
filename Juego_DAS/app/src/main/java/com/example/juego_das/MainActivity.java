package com.example.juego_das;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //Codigo obtenido de --> https://youtu.be/y1fptOfsIRs : Autor --> TechPot
    //Valores de la ruleta
    final String[] sectors = {"rosa", "morado", "azul", "verde", "negro", "amarillo", "naranja", "rojo"};
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
        setContentView(R.layout.activity_main);

        //Lo que gira
        wheel = findViewById(R.id.wheel);
        //Detector (flecha)
        ImageView arrow = findViewById(R.id.arrow);

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
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                //Para cuando el giro pare
                //Toast que desvela el multiplicador
                double multi = Math.random()*4.1;
                multi = Math.round(multi*10.0)/10.0;

                String mensaje = "Tu multiplicador en juego es : x" + multi;
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG).show();

                //Fin Giro
                girando = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
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

}