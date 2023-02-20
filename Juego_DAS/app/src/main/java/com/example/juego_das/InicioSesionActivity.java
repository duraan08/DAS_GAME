package com.example.juego_das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InicioSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
    }

    public void comprobar(View view){
        EditText user = findViewById(R.id.user);
        EditText psw = findViewById(R.id.psw);

        String usuario = user.getText().toString().toLowerCase();
        String pass = psw.getText().toString();

        if (usuario.equals("unai") && pass.equals("123456")){
            Intent inicio = new Intent(InicioSesionActivity.this, ParticipantesActivity.class);
            startActivity(inicio);
            finish();
        }
        else{
            Toast.makeText(InicioSesionActivity.this, "Tu usuario o contraseña son incorrectos", Toast.LENGTH_LONG);
        }

    }

    public void registrar(View view){
        Intent registro = new Intent(InicioSesionActivity.this, RegistroActivity.class);
        startActivity(registro);
        finish();
    }
}