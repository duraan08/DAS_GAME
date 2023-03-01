package com.example.juego_das;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class DobleNadaActivity extends AppCompatActivity {
    int seconds;
    boolean running, dialogOn;
    int hours, minutes, secs;
    String valor;
    TextView tiempo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doble_nada);

        if (savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            dialogOn = savedInstanceState.getBoolean("dialogo");
        }

        if (running){
            tiempo = (TextView) findViewById(R.id.timer);
            hours = seconds / 3600;
            minutes = (seconds % 3600) / 60;
            secs = seconds % 60;

            String tmp = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
            tiempo.setText(tmp);
        }

        if (dialogOn){
            activarDialog();
        }

        runTimer();
    }

    public void onStart(View view){
        running = true;
    }
    //Se comprueba si ha conseguido para el crono en el momento justo, y se muestra un dialogo personalizado para cada caso.
    public void onStop(View view){
        running = false;
        activarDialog();
    }

    @Override
    //Guardamos los valores para poder gestionar el giro de pnatalla y que el cronometro siga corriendo sin que se reinicie
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("dialogo", dialogOn);
    }

    private void runTimer(){
        TextView timer = findViewById(R.id.timer);
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                hours = seconds / 3600;
                minutes = (seconds % 3600) / 60;
                secs = seconds % 60;

                String tiempo = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timer.setText(tiempo);

                if (running){
                    seconds ++;
                }
                else{
                    timer.setText("0:00:00");
                }

                handler.postDelayed(this, 20);
            }
        });
    }

    private void activarDialog(){
        dialogOn = true;
        TextView tiempo = findViewById(R.id.timer);
        String t = tiempo.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(DobleNadaActivity.this);
        builder.setTitle("Tu tiempo ha sido de : " + t);

        if (t.equals("0:01:00")){
            builder.setMessage("Te has salvado tus tragos se resetean a 0");
        }
        else{
            Bundle extras = getIntent().getExtras();
            if (extras != null){
                valor = extras.getString("tragos");
                valor = String.valueOf(Integer.parseInt(valor) * 2);
            }
            builder.setMessage("Has perdido tus tragos se duplican x2. \nTe toca beber : " + valor + " tragos");
        }
        builder.setPositiveButton("VOLVER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

}