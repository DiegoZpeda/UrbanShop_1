package com.ugb.urban_shop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbman extends SQLiteOpenHelper {
    public static final String dbname = "db_man";
    public static final int v = 2;
    static final String sqlDbman = "CREATE TABLE man(idman integer primary key autoincrement, codigo text, descripcion text, marca text, presentacion text, precio text, man text)";
    public Dbman(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {sqLiteDatabase.execSQL(sqlDbman) ;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public String administrar_man(String id, String cod, String des, String mar, String pres, String prec, String accion) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (accion.equals("nuevo1")) {
                db.execSQL("INSERT INTO man(codigo,descripcion,marca,presentacion,precio) VALUES('" + cod + "','" + des + "','" + mar + "','" + pres + "','" + prec + "')");

            } else if (accion.equals("modificar2")) {
                db.execSQL("UPDATE man SET codigo='" + cod + "', descripcion='" + des + "', marca='" + mar + "', presentacion='" + pres + "',precio='" + prec + "' WHERE idman='" + id + "'");

            } else if (accion.equals("eliminar3")) {
                db.execSQL("DELETE FROM man WHERE idman='" + id + "'");

            }
            return "ok";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

    }
    public Cursor consultar_man(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM man ORDER BY codigo";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }
}
