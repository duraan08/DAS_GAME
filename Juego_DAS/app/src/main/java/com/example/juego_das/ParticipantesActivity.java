package com.example.juego_das;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ParticipantesActivity extends AppCompatActivity {

    ArrayList<String> listaParticipantes;
    ArrayAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);
        Button add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText participante = findViewById(R.id.nom_participante);
                String participant = participante.getText().toString();
                listaParticipantes.add(participant);

                ListView listaPart = findViewById(R.id.participantes);
                listaPart.setAdapter(adaptador);

                listaPart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        //Elimina el elemento
                        listaParticipantes.remove(position);
                        //Mensaje emergente
                        Toast.makeText(ParticipantesActivity.this, "Participante Eliminado Correctamente", Toast.LENGTH_LONG).show();
                        //Actualiza la lista
                        adaptador.notifyDataSetChanged();
                        return true;
                    }
                });
                comprobarParticipantes();
            }
        });
    }

    private void comprobarParticipantes(){
        if (listaParticipantes.size() >= 4){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Has llegado al límite de 4 jugadores, ¿deseas comenzar el juego?");
            builder.setPositiveButton("Comenzar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent juego = new Intent(ParticipantesActivity.this, RuletaActivity.class);
                    startActivity(juego);
                    finish();
                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
    }
}