package com.example.crudandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

// Con SQLiteOpenHelper se heredan los métodos de la clase
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Creación de la base de datos con una tabla llamada persona con siete campos numIdent, tipoIdent, nombre, apellido, correo, teléfono, genero.
    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("Create table persona(numIdent int primary key, tipoIdent text, nombre text, apellido text, correo text, telefono int, genero text)");
    }
    // Método que se usa en caso de que halla que actualizar la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
