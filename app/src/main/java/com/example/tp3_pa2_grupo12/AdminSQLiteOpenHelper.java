package com.example.tp3_pa2_grupo12;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{


    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuarios(id_usuario integer primary key autoincrement, nombre text, email text, contrasena text)");
        db.execSQL("CREATE TABLE parqueos(id_parqueo INTEGER PRIMARY KEY AUTOINCREMENT, nro_matricula TEXT, tiempo TEXT, usuario TEXT)");

    }


    public void insertarParqueo(String usuario, String nroMatricula, String tiempo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO parqueos (nro_matricula, tiempo, usuario) VALUES (?, ?, ?)", new Object[]{nroMatricula, tiempo, usuario});
        db.close();
    }

    public Cursor obtenerParqueosPorUsuario(String usuario) {
        SQLiteDatabase db = this.getReadableDatabase();
           return db.rawQuery("SELECT nro_matricula, tiempo FROM parqueos WHERE usuario = ?", new String[]{usuario});
    }

    public Cursor obtenerParqueos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM parqueos", null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
