package com.example.juego_das;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
    int[] imagenes = {R.drawable.usuario};
    ListViewAdapter elAdaptador;
    ListView listaPart;
    boolean dialogoOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);
        Button add_button = findViewById(R.id.add_button);

        if (savedInstanceState != null){
            listaParticipantes = savedInstanceState.getStringArrayList("participantes");
            listaPart = findViewById(R.id.participantes);
            dialogoOn = savedInstanceState.getBoolean("dialogo");
        }

        elAdaptador = new ListViewAdapter(getApplicationContext(), listaParticipantes, imagenes);

        if (listaParticipantes.size() != 0){
            listaPart.setAdapter(elAdaptador);
        }

        if (dialogoOn){
            activarDialog(listaParticipantes);
        }
    }

    public void add(View view){
        EditText participante = findViewById(R.id.nom_participante);
        String participant = participante.getText().toString();

        //Se comprueba que el campo no este vacio
        if (!participant.isEmpty()){
            listaParticipantes.add(participant);
        }
        else{
            Toast.makeText(ParticipantesActivity.this, "No puede dejar el campo vacio", Toast.LENGTH_SHORT).show();
        }

        listaPart = findViewById(R.id.participantes);
        listaPart.setAdapter(elAdaptador);

        listaPart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Elimina el elemento
                listaParticipantes.remove(position);
                //Mensaje emergente
                Toast.makeText(ParticipantesActivity.this, "Participante Eliminado Correctamente", Toast.LENGTH_LONG).show();
                //Actualiza la lista
                elAdaptador.notifyDataSetChanged();
                comprobarParticipantes();
                return true;
            }
        });
        comprobarParticipantes();
    }

    private void comprobarParticipantes(){
        //Si en la lista se encuentran un minimo de 2 participantes salta en Dialog para poder comenzar el juego
        if (listaParticipantes.size() >=2 && listaParticipantes.size() <=10){
            //Activamos el dialog
            activarDialog(listaParticipantes);
        }
        //Si en la lista se encuentra que hay mas de 10 participantes (maximo) se avisará y se deberá de eliminar uno de los participantes
        else if (listaParticipantes.size() > 10){
            DialogoMaxParticipantes maximo = new DialogoMaxParticipantes();
            maximo.show(getSupportFragmentManager(), "maximoParticipantes");
        }
    }

    private void activarDialog(ArrayList<String> listaParticipantes){
        dialogoOn = true;
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ParticipantesActivity.this);
        builder.setMessage("¿deseas comenzar el juego?");
        builder.setCancelable(false);

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

    @Override
    //Guardamos los valores para poder gestionar el giro de pantalla
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("participantes", listaParticipantes);
        outState.putBoolean("dialogo", dialogoOn);
    }
}