package com.example.crudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String opcionDocumento;
    // Creación de los objetos
    private Spinner cmb1;
    private EditText et1, et2, et3, et4, et5;
    private RadioButton rb1, rb2, rb3;
    private RadioGroup rg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Comunicación entreo los elementos lógicos con los elementos gráficos
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        rg1 = findViewById(R.id.rg1);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        cmb1 = findViewById(R.id.cmb1);
        listaDespleglabe(); // Llama la función para mostrar los datos en el spinner
    }


    // Método para crear un registro en la base de datos, realiza la conexión con la base de datos.
    // Verifica que todos los campos estén llenos para poder guardar los registros, confirmándolo con un Toast.
    public void crear(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "personal", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase(); //Abre la base de datos en modo de lectura y escritura.

        //Se convierten los datos string y se recupera el dato del objeto gráfico
        String numIdentificacion    = et1.getText().toString();
        String tipoIdentificacion   = opcionDocumento;
        String nombre               = et2.getText().toString();
        String apellido             = et3.getText().toString();
        String correo               = et4.getText().toString();
        String telefono             = et5.getText().toString();
        String genero               = generoSeleccionado(view);

        //Se verifica si las variables son diferentes de vacío
        if (!numIdentificacion.isEmpty() && !tipoIdentificacion.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() &&
                !correo.isEmpty() && !telefono.isEmpty() && !genero.isEmpty() ){

            //La clase ContentValues se utiliza para para retener los valores de un solo registro, que será el que se insertará en la BD.
            ContentValues registro = new ContentValues();

            //Se colocan los valores que se leen de la parte gráfica en el registro
            registro.put("numIdent", numIdentificacion);
            registro.put("tipoIdent", tipoIdentificacion);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registro.put("correo", correo);
            registro.put("telefono", telefono);
            registro.put("genero", genero);
            BaseDeDatos.insert("persona", null, registro);
            BaseDeDatos.close();
            limpiarCampos(view);
            Toast.makeText(this, "Registro OK", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "LLena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para buscar un registro en la base de datos verifica que el campo de número de identificación no este vació y busca en la base de datos,
    // si el número de identificación se encuentra en la base de datos muestra los datos de la persona en los campos.
    public void buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "personal", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();  //Abre la base de datos en modo de lectura y escritura.

        String numIdentificacion = et1.getText().toString();
        //Se verifica si las variables son diferentes de vacío
        if (!numIdentificacion.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select tipoIdent, nombre, apellido, correo, telefono, genero " +
                    "from persona where numIdent = " + numIdentificacion, null );
            //Se obtiene los datos de la base de datos
            if (fila.moveToFirst()){
                for(int i = 0; i < cmb1.getCount() ; i++) {
                    System.out.println(cmb1.getItemAtPosition(i).toString());
                    System.out.println(fila.getString(0).toString());
                    if (fila.getString(0).contains(cmb1.getItemAtPosition(i).toString() )) {
                        cmb1.setSelection(i);
                        break;
                    }
                }

                //Se muestran en la aplicación, los datos encontrados en la base de dados
                et2.setText(fila.getString(1));
                et3.setText(fila.getString(2));
                et4.setText(fila.getString(3));
                et5.setText(fila.getString(4));
                if (fila.getString(5).contains(rb1.getText())){
                    rb1.setChecked(true);
                } else if (fila.getString(5).contains(rb2.getText())) {
                    rb2.setChecked(true);
                } else if (fila.getString(5).contains(rb3.getText())){
                    rb3.setChecked(true);
                }
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "No esta registrada la persona.", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
                limpiarCampos(view);
            }
        } else {
            Toast.makeText(this, "Ingresa un número de Identificación", Toast.LENGTH_SHORT).show();
            limpiarCampos(view);
        }
    }

    //Método para modificar un registro en la base de datos verifica que ningún campo este vacío y
    // busca en la base de datos el número de identificación si existe modifica los datos de la base de datos con los datos ingresados en la aplicación.
    public void modificacion(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "personal", null, 1 );
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();  //Abre la base de datos en modo de lectura y escritura.

        //Se convierten los datos string y se recupera el dato del objeto gráfico
        String numIdentificacion    = et1.getText().toString();
        String tipoIdentificacion   = opcionDocumento;
        String nombre               = et2.getText().toString();
        String apellido             = et3.getText().toString();
        String correo               = et4.getText().toString();
        String telefono             = et5.getText().toString();
        String genero               = generoSeleccionado(view);

        //Se verifica si las variables son diferentes de vacío
        if (!numIdentificacion.isEmpty() && !tipoIdentificacion.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() &&
                !correo.isEmpty() && !telefono.isEmpty() && !genero.isEmpty() ){

            //La clase ContentValues se utiliza para para retener los valores de un solo registro, que será el que se insertará en la BD.
            ContentValues registro = new ContentValues();

            //Se colocan los valores que se leen de la parte gráfica en el registro
            registro.put("numIdent", numIdentificacion);
            registro.put("tipoIdent", tipoIdentificacion);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registro.put("correo", correo);
            registro.put("telefono", telefono);
            registro.put("genero", genero);
            limpiarCampos(view);
            int cant = BaseDeDatos.update("persona", registro, "numIdent=" + numIdentificacion, null);
            if (cant == 1){
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No existe el numero de identificación", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Método para eliminar un registro en la base de datos verifica que el campo de número de identificación no este vació y busca en la base de datos,
    //si el número de identificación se encuentra en la base de datos elimina el registro de la base de datos.
    public void eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "personal", null, 1 );
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();  //Abre la base de datos en modo de lectura y escritura.
        String numIdentificacion    = et1.getText().toString();
        limpiarCampos(view);
        int cant = BaseDeDatos.delete("persona", "numIdent=" + numIdentificacion, null);
        if (cant == 1){
            Toast.makeText(this, "Se elimino exitosamente", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "No existe el numero de identificación", Toast.LENGTH_SHORT).show();
        }
    }

    //Método que mediante la función OnClick lanza el otro activiy que muestra todos los datos que se encuentran en la base de datos.
    public void mostrarTodosLosDatos(View view){
        Intent mostarDatos = new Intent(this, Main2Activity.class);
        startActivity(mostarDatos);
    }

    //Método que borra todos los datos escritos en los campos de la aplicación.
    public void limpiarCampos(View view){
        et1.setText("");
        cmb1.setSelection(0);
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
        rg1.clearCheck();
    }

    //Método que permite mostrar los datos obtenidos del archivo valores_array.xml en una lista desplegable (Spinner).
    public void listaDespleglabe(){
        final String[] datos = new String[]{"Elem1","Elem2","Elem3","Elem4"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valores_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Se muestra los datos del adaptador en el spinner
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
        cmb1.setSelection(0);
    }

    //Método que verifica que radiobutton fue seleccionado y según la selección le asigna un valor en este caso es el genero.
    // Retorna un String
    public String generoSeleccionado (View view){
        String rbSeleccionado = "";
        if(rb1.isChecked() == true){
            rbSeleccionado = "Masculino";
        }else if (rb2.isChecked() == true) {
            rbSeleccionado = "Femenino";
        }else if (rb3.isChecked() == true){
            rbSeleccionado = "Otro";
        }
        return rbSeleccionado;
    }
}
