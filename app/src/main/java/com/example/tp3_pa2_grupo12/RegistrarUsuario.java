package com.example.tp3_pa2_grupo12;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarUsuario extends AppCompatActivity {

    private EditText et_nombre, et_email, et_contrasena, et_repetirContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_usuario);

        et_nombre = findViewById(R.id.txt_nombre);
        et_email = findViewById(R.id.txt_email);
        et_contrasena = findViewById(R.id.txt_contrasena);
        et_repetirContrasena = findViewById(R.id.txt_repetirContrasena);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        String nombre = et_nombre.getText().toString();
        String email = et_email.getText().toString();
        String contrasena = et_contrasena.getText().toString();
        String repContra = et_repetirContrasena.getText().toString();

        if(!nombre.isEmpty() && !email.isEmpty() && !contrasena.isEmpty() && !repContra.isEmpty()){
            if(contrasena.equals(repContra)) {
                if (!existeEmail(db, email)) {
                    registrarUsuario(db, nombre, email, contrasena);
                    Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(this, "ERROR, Email existente", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();

    }

    public void limpiarCampos(){
        et_nombre.setText("");
        et_email.setText("");
        et_contrasena.setText("");
        et_repetirContrasena.setText("");

        et_nombre.requestFocus();
    }

    public void registrarUsuario(SQLiteDatabase db ,String nombre, String email, String contrasena){
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("email", email);
        registro.put("contrasena", contrasena);

        db.insert("usuarios", null, registro);
        db.close();
        limpiarCampos();
    }

    public boolean existeEmail(SQLiteDatabase db, String email){
        Cursor fila = db.rawQuery("select * from usuarios where email='" + email +"'", null);
        return fila.moveToFirst();
    }
}