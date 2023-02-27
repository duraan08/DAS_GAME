package com.example.juego_das;

import androidx.annotation.NonNull;
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

    ArrayList<String> listaParticipantes = new ArrayList<String>();
    ArrayAdapter adaptador;
    ListView listaPart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);
        Button add_button = findViewById(R.id.add_button);

        if (savedInstanceState != null){
            listaParticipantes = savedInstanceState.getStringArrayList("participantes");
            listaPart = findViewById(R.id.participantes);
        }
        if (listaParticipantes.size() != 0){
            adaptador = new ArrayAdapter<String>(ParticipantesActivity.this, android.R.layout.simple_list_item_1, listaParticipantes);
            listaPart.setAdapter(adaptador);
        }
    }

    public void add(View view){
        EditText participante = findViewById(R.id.nom_participante);
        String participant = participante.getText().toString();
        participante.setText(" ");

        listaParticipantes.add(participant);

        listaPart = findViewById(R.id.participantes);
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
                comprobarParticipantes();
                return true;
            }
        });
        comprobarParticipantes();
    }

    private void comprobarParticipantes(){
        //Si en la lista se encuentran un minimo de 2 participantes salta en Dialog para poder comenzar el juego
        if (listaParticipantes.size() >=2 && listaParticipantes.size() <=5){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿deseas comenzar el juego?");

            builder.setPositiveButton("Comenzar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent juego = new Intent(ParticipantesActivity.this, RuletaActivity.class);
                    juego.putExtra("participantes", listaParticipantes);
                    startActivity(juego);
                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        //Si en la lista se encuentra que hay mas de 5 participantes (maximo) se avisará y se deberá de eliminar uno de los participantes
        else if (listaParticipantes.size() > 5){
            DialogoMaxParticipantes maximo = new DialogoMaxParticipantes();
            maximo.show(getSupportFragmentManager(), "maximoParticipantes");
        }
    }

    @Override
    //Guardamos los valores para poder gestionar el giro de pantalla
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("participantes", listaParticipantes);
    }
}