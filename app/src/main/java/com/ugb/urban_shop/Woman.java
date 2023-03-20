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
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Woman extends AppCompatActivity {
    SearchView searchView;
    Bundle parametros = new Bundle();
    DbUrban db_shop;
    ListView lts;
    Cursor cRopa;
    FloatingActionButton btn;
    final ArrayList<String> alRopa = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woman);
        obtenerDatosRopa();
        btn = findViewById(R.id.btnmujer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirAgregarRopa(parametros);
            }
        });
    }
    public void abrirAgregarRopa(Bundle parametros) {
        Intent iAgregarwoman = new Intent(Woman.this, MainActivitywoman.class);
        iAgregarwoman.putExtras(parametros);
        startActivity(iAgregarwoman);
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
                    parametros.putString("accion", "nuevo");
                    abrirAgregarRopa(parametros);

                    return true;
                case R.id.mnxModificar:
                    String ropa[] = {
                            cRopa.getString(0), //idmujer
                            cRopa.getString(1), //codigo
                            cRopa.getString(2), //descripcion
                            cRopa.getString(3), //marca
                            cRopa.getString(4), //presentacion
                            cRopa.getString(5), //precio

                    };
                    parametros.putString("accion", "modificar");
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
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(Woman.this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(cRopa.getString(2));
            confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db_shop.administrar_mujer(cRopa.getString(0), "","", "", "", "","eliminar" );

                    obtenerDatosRopa();
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
    public void obtenerDatosRopa(){
        try {
            alRopa.clear();
            db_shop = new DbUrban(Woman.this, "", null, 2);
            cRopa = db_shop.consultar_mujer();
            if(cRopa.moveToFirst()){
                lts = findViewById(R.id.ltsWoman);
                final ArrayAdapter<String> adropa = new ArrayAdapter<String>(Woman.this,
                        android.R.layout.simple_expandable_list_item_1, alRopa);
                lts.setAdapter(adropa);
                do{
                    alRopa.add(cRopa.getString(2));//2 es el nombre del amigo, pues 0 es el idAmigo.

                }while(cRopa.moveToNext());
                adropa.notifyDataSetChanged();
                registerForContextMenu(lts);
            }else{
                Toast.makeText(this, "NO HAY datos que mostrar", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Error al obtener amigos: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}