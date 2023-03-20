package com.ugb.urban_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Man extends AppCompatActivity {
    Bundle parametros = new Bundle();
    Dbman db_man;
    ListView lts;
    Cursor cRopa;
    FloatingActionButton btn;
    final ArrayList<String> alRopaman = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man);
        obtenerDatosRopa1();
        btn = findViewById(R.id.btnman);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo1");
                abrirAgregarRopa(parametros);
            }
        });
    }
    public void abrirAgregarRopa(Bundle parametros) {
        Intent iAgregarman = new Intent(Man.this, MainActivityman.class);
        iAgregarman.putExtras(parametros);
        startActivity(iAgregarman);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        cRopa.moveToPosition(info.position);
        menu.setHeaderTitle(cRopa.getString(2)); //2=> Codigo de Ropa...
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try{
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo1");
                    abrirAgregarRopa(parametros);

                    return true;
                case R.id.mnxModificar:
                    String ropa[] = {
                            cRopa.getString(0), //id
                            cRopa.getString(1), //codigo
                            cRopa.getString(2), //descripcion
                            cRopa.getString(3), //marca
                            cRopa.getString(4), //presentacion
                            cRopa.getString(5), //precio

                    };
                    parametros.putString("accion", "modificar2");
                    parametros.putStringArray("ropa", ropa);
                    abrirAgregarRopa(parametros);
                    return true;
                case R.id.mnxEliminar:
                    eliminarDatosRopa();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }catch (Exception e){
            return super.onContextItemSelected(item);
        }
    }
    void eliminarDatosRopa(){
        try{
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(Man.this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(cRopa.getString(1));
            confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db_man.administrar_man(cRopa.getString(0), "","", "", "", "","eliminar3" );

                    obtenerDatosRopa1();
                    dialogInterface.dismiss();
                }
            });
            confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmacion.create().show();
        }catch (Exception e){
            Toast.makeText(this, "Error al eliminar: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void obtenerDatosRopa1(){
        try {
            alRopaman.clear();
            db_man = new Dbman(Man.this, "", null, 2);
            cRopa = db_man.consultar_man();
            if(cRopa.moveToFirst()){
                lts = findViewById(R.id.ltsMan);
                final ArrayAdapter<String> adropa = new ArrayAdapter<String>(Man.this,
                        android.R.layout.simple_expandable_list_item_1, alRopaman);
                lts.setAdapter(adropa);
                do{
                    alRopaman.add(cRopa.getString(2));//2 es el nombre del amigo, pues 0 es el idAmigo.
                }while(cRopa.moveToNext());
                adropa.notifyDataSetChanged();
                registerForContextMenu(lts);
            }else{
                Toast.makeText(this, "NO HAY datos que mostrar", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Error al obtener : "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}