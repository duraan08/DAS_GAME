package com.example.juego_das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void registrar(View view){
        EditText user = findViewById(R.id.user_register);
        EditText psw1 = findViewById(R.id.psw_register);
        EditText psw2 = findViewById(R.id.psw_register_2);

        String usuario = user.getText().toString().toLowerCase();
        String pass = psw1.getText().toString();
        String pass2 = psw2.getText().toString();

        //Se comprueba que ambas password coincidan y que haya introducido algo en el campo de usuario
        if (usuario != null && pass.equals(pass2)){
            Intent registro = new Intent(RegistroActivity.this, InicioSesionActivity.class);
            startActivity(registro);
            finish();
        }
    }
}