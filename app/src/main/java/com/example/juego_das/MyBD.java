package com.example.juego_das;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyBD extends SQLiteOpenHelper {
    public MyBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE USUARIOS ('Nombre' VARCHAR(30) PRIMARY KEY NOT NULL, 'Password' VARCHAR(30))");
        //Rellenamos la bd
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //No hay operaciones
    }
}
