package com.example.juego_das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
        if (!usuario.isEmpty() && !pass.isEmpty() && !pass2.isEmpty()){
            if (pass.equals(pass2)){
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
                    modificaciones.put("Nombre", usuario);
                    modificaciones.put("Password", pass);
                    bd.insert("Usuarios", null, modificaciones);
                    bd.close();

                    //Una vez insertado redirigimos al Login
                    finish();
                }
            }
            else {
                Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(RegistroActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean comprobarUser(String user){
        boolean existe = false;
        bd = gestorBD.getReadableDatabase();
        String[] argumento = new String[]{user};
        Cursor c = bd.rawQuery("SELECT * FROM Usuarios WHERE Nombre = ?", argumento);

        //Se comprueba si se han recogido datos
        if (c.moveToNext()){
            existe = true;
        }
        else{
            existe = false;
        }
        c.close();
        bd.close();

        return existe;
    }
}