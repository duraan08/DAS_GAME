package com.example.juego_das;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoInstrucciones extends DialogFragment {
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Instrucciones");
        builder.setMessage("Cualquier Jugador hace girar la ruleta, De manera aleatoria la app elegirá a" +
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
        return builder.create();
    }
}
