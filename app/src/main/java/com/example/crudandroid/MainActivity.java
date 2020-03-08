package com.example.crudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String opcionDocumento;
    private Spinner cmb1;
    private EditText et1, et2, et3, et4, et5;
    private RadioButton rb1, rb2, rb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        cmb1 = findViewById(R.id.cmb1);
        listaDespleglabe();
    }



    public void crear(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "personal", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String numIdentificacion       = et1.getText().toString();
        String tipoIdentificacion   = opcionDocumento;
        String nombre               = et2.getText().toString();
        String apellido             = et3.getText().toString();
        String correo               = et4.getText().toString();
        String telefono             = et5.getText().toString();
        String genero               = generoSeleccionado(view);

        if (!numIdentificacion.isEmpty() && !tipoIdentificacion.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() &&
                !correo.isEmpty() && !telefono.isEmpty() && !genero.isEmpty() ){
            ContentValues registro = new ContentValues();

            registro.put("numIdent", numIdentificacion);
            registro.put("tipoIdent", tipoIdentificacion);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registro.put("correo", correo);
            registro.put("telefono", telefono);
            registro.put("genero", genero);
            BaseDeDatos.insert("persona", null, registro);
            BaseDeDatos.close();
            et1.setText("");
            et2.setText("");
            et3.setText("");
            et4.setText("");
            et5.setText("");
            Toast.makeText(this, "Registro OK", Toast.LENGTH_SHORT).show();

        } else{
            Toast.makeText(this, "LLena todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    public void listaDespleglabe(){
        final String[] datos = new String[]{"Elem1","Elem2","Elem3","Elem4"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valores_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmb1.setAdapter(adapter);
        cmb1.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
                        opcionDocumento = parent.getItemAtPosition(position).toString();
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        opcionDocumento = "";
                    }
                });
    }

    public String generoSeleccionado (View view){
        String rbSeleccionado = "";
        if(rb1.isChecked() == true){
            rbSeleccionado = rb1.toString();
        }else if (rb2.isChecked() == true) {
            rbSeleccionado = rb2.toString();
        }else if (rb3.isChecked() == true){
            rbSeleccionado = rb3.toString();
        }
        return rbSeleccionado;
    }
}
