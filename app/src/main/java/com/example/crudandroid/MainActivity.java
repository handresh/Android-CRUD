package com.example.crudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String opcionDocumento;
    Spinner cmbOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cmbOpciones = findViewById(R.id.cmb1);
        final String[] datos = new String[]{"Elem1","Elem2","Elem3","Elem4"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valores_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbOpciones.setAdapter(adapter);
        cmbOpciones.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
                    opcionDocumento = parent.getItemAtPosition(position).toString();
                }
                public void onNothingSelected(AdapterView<?> parent) {
                    opcionDocumento = "";
                }
            });

    }



}
