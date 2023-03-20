package com.ugb.urban_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivitywoman extends AppCompatActivity {
    DbUrban db_shop;
    String accion="nuevo";
    String id="";
    Button btn;
    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar_mujer();
            }
        });


        mostrar_datos_ropa();
    }
    void mostrar_datos_ropa(){
        Bundle parametros = getIntent().getExtras();
        accion = parametros.getString("accion");
        if(accion.equals("modificar")){
            String ropa[] = parametros.getStringArray("ropa");
            id = ropa[0];

            temp = findViewById(R.id.txtcodigo);
            temp.setText(ropa[1]);

            temp = findViewById(R.id.txtdescripcion);
            temp.setText(ropa[2]);

            temp = findViewById(R.id.txtmarca);
            temp.setText(ropa[3]);

            temp = findViewById(R.id.txtpresentacion);
            temp.setText(ropa[4]);

            temp = findViewById(R.id.txtprecio);
            temp.setText(ropa[5]);
        }

    }
    void guardar_mujer(){
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

            db_shop = new DbUrban(MainActivitywoman.this, "",null,1);
            String result = db_shop.administrar_mujer(id, codigo, descripcion, marca, presentacion,precio, accion);
            String msg = result;
            if( result.equals("ok") ){
                msg = "Registro guardado con exito";
                regresarListaropa();
            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error en guardar ropa: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    void regresarListaropa(){
        Intent ilistawoman = new Intent(MainActivitywoman.this, Woman.class);
        startActivity(ilistawoman);

    }
}