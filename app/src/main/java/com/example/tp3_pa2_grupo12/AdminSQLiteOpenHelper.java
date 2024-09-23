package com.example.tp3_pa2_grupo12;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "ParkingDB";
    private static final int DATABASE_VERSION = 1;


    public AdminSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        db.execSQL("DROP TABLE IF EXISTS paqueos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    // Método para verificar si el usuario está registrado
    public boolean estaElUsuarioRegistrado(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?", new String[]{username, password});
        boolean estaRegistrado = (cursor.getCount() > 0);
        cursor.close();
        return estaRegistrado;
    }
}
