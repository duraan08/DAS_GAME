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
        builder.setMessage("1. El usuario principal debe Iniciar sesión o registrarse en caso de que no tenga una cuenta." +
                "\n\n2. Se deben añadir los participantes del juego (mín 2 y máx 10)" +
                "\n\n3. Cualquier jugador puede hacer girar la ruleta, esta al pararse elegirá uno de los participantes de forma" +
                " aleatoria y le asignará unos tragos." +
                "\n\n4. El jugador elegido, tiene opción de jugar un minijuego para evitar beber los tragos asigandos." +
                " Este trata de parar el cronometro en el momento exacto de (0:01:00) si lo consigue se libra de los tragos" +
                " si no se duplicarán.");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
