package com.ugb.urban_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityman extends AppCompatActivity {
    Dbman db_man;
    String accion="nuevo1";
    String id="";
    Button btn;
    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityman);
        btn = findViewById(R.id.btnGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {guardar_man();
            }
        });


        mostrar_datos_ropaman();
    }
    void mostrar_datos_ropaman(){
        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");
        if(accion.equals("modificar2")){
            String ropaman[] = parametros.getStringArray("ropa");
            id = ropaman[0];

            temp = findViewById(R.id.txtcodigo);
            temp.setText(ropaman[1]);

            temp = findViewById(R.id.txtdescripcion);
            temp.setText(ropaman[2]);

            temp = findViewById(R.id.txtmarca);
            temp.setText(ropaman[3]);

            temp = findViewById(R.id.txtpresentacion);
            temp.setText(ropaman[4]);

            temp = findViewById(R.id.txtprecio);
            temp.setText(ropaman[5]);
        }

    }
    void guardar_man(){
        try {
            temp = (TextView) findViewById(R.id.txtcodigo);
            String codigo = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtdescripcion);
            String descripcion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtmarca);
            String marca = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtpresentacion);
            String presentacion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtprecio);
            String precio = temp.getText().toString();

            db_man = new Dbman(MainActivityman.this, "",null,1);
            String result = db_man.administrar_man(id, codigo, descripcion, marca, presentacion,precio, accion);
            String msg = result;
            if( result.equals("ok") ){
                msg = "Registro guardado con exito";
                regresarListaman();
            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error en guardar ropa: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    void regresarListaman(){
        Intent ilistaman = new Intent(MainActivityman.this, Man.class);
        startActivity(ilistaman);

    }
}