package com.example.juego_das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InicioSesionActivity extends AppCompatActivity {
    String usu, passwd;
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

        //Obtenemos los datos de la BD
        MyBD gestorBD = new MyBD(InicioSesionActivity.this, "Usuarios", null, 1 );
        SQLiteDatabase bd = gestorBD.getReadableDatabase();
        Cursor c = bd.rawQuery("SELECT Nombre, Password FROM USUARIOS WHERE Nombre = '"+usuario+"' ", null);
        usu = c.getString(0);
        passwd = c.getString(1);
        c.close();
        bd.close();

        if (usuario.equals(usu) && pass.equals(passwd)){
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