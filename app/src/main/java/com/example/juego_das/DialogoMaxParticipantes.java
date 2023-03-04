package com.example.juego_das;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoMaxParticipantes extends DialogFragment {
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¡¡ALERTA!!");
        builder.setMessage("Has alcanzado el límite de jugadores, elimina uno de los añadidos");
        builder.setCancelable(false);
        builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }
}
