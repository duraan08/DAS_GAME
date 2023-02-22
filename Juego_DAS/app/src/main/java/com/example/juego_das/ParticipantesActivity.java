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

    ArrayList<String> listaParticipantes = new ArrayList<String>();
    ArrayAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);
        Button add_button = findViewById(R.id.add_button);
        adaptador = new ArrayAdapter<String>(ParticipantesActivity.this, android.R.layout.simple_list_item_1, listaParticipantes);
    }

    public void add(View view){
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
                comprobarParticipantes();
                return true;
            }
        });
        comprobarParticipantes();
    }

    private void comprobarParticipantes(){
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
        else if (listaParticipantes.size() > 5){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¡¡ALERTA!!");
            builder.setMessage("Has alcanzado el límite de 5 jugadores, elimina uno de los añadidos");
            builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }
}