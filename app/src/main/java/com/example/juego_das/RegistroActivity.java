package com.example.juego_das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    MyBD gestorBD = new MyBD(RegistroActivity.this,"Usuarios",null,1);
    SQLiteDatabase bd;

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
            //Se comprueba que el usuario no existe
            if (comprobarUser(usuario)){
                //El usuario ya existe
                Toast.makeText(RegistroActivity.this, "El usuario ya existe", Toast.LENGTH_LONG).show();
            }
            else{
                //El usuario no existe
                //Insertamos la infor en la BBDD
                bd = gestorBD.getWritableDatabase();
                ContentValues modificaciones = new ContentValues();
                //modificaciones.put("Nombre", usuario);
                //modificaciones.put("Password", pass);
                bd.execSQL("INSERT INTO Usuarios ('Nombre', 'Password') VALUES ('" + usuario + "','" + pass + "')");
                bd.close();

                //Una vez insertado redirigimos al Login
                Intent registro = new Intent(RegistroActivity.this, InicioSesionActivity.class);
                startActivity(registro);
                finish();
            }
        }
        else{
            Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG);
        }
    }

    private boolean comprobarUser(String user){
        boolean existe = false;
        bd = gestorBD.getReadableDatabase();
        String[] argumento = new String[]{user};
        String[] campos = new String[]{"Nombre"};
        Cursor c = bd.query("Usuarios", campos, "Nombre=?", argumento, null, null, null);

        if (c.getPosition() != -1){
            existe = true;
        }
        c.close();
        bd.close();

        return existe;
    }
}