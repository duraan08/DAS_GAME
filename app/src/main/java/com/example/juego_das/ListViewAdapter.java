package com.example.juego_das;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<String> listaParticipantes;
    private Context contexto;
    private int[] imagenes;
    private LayoutInflater inflater;

    public ListViewAdapter(Context pcontext, ArrayList<String> listaParticipantes, int[] imagenes)
    {
        this.contexto = pcontext;
        this.listaParticipantes = listaParticipantes;
        this.imagenes = imagenes;
        this.inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaParticipantes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaParticipantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.participante, null);
        TextView nombre = (TextView) view.findViewById(R.id.nom_part);
        ImageView icono = (ImageView) view.findViewById(R.id.icono);
        nombre.setText(listaParticipantes.get(position));
        icono.setImageResource(imagenes[0]);

        return view;
    }
}
