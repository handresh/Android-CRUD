package com.example.crudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lv = (ListView)findViewById(R.id.lista);
        lista = llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista );
        lv.setAdapter(adaptador);
    }


    public ArrayList llenar_lv(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "personal", null, 1 );
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor fila = BaseDeDatos.rawQuery("select * from persona", null);
        if (fila.moveToFirst()){
            do{
                lista.add(fila.getString(2) + " " + fila.getString(3) + " " + fila.getString(1)
                        + " " + fila.getString(0)+ " " + fila.getString(4) + " " + fila.getString(5)
                        + " " + fila.getString(6));
            } while (fila.moveToNext());
        }
        return lista;
    }
}
