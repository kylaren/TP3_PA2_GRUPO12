package com.example.tp3_pa2_grupo12;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText et_nombreUsuario, et_password;

    private Button btnIniciarSesion;
    private AdminSQLiteOpenHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa los campos de inicio de sesión
        et_nombreUsuario = (EditText)findViewById(R.id.et_nombreUsuario);
        et_password = (EditText)findViewById(R.id.et_password);
        dbHelper = new AdminSQLiteOpenHelper(this);


        // Encuentra el botón "Iniciar sesión" por su ID
        Button btnIniciarSesion = findViewById(R.id.btn_iniciarSesion);

        // Configura el OnClickListener para el botón
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = et_nombreUsuario.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                // Validación de campos

                if (username.isEmpty()){
                    Toast.makeText(MainActivity.this, "El campo Usuario está vacío.", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()){
                    Toast.makeText(MainActivity.this, "El campo Contraseña está vacío.", Toast.LENGTH_SHORT).show();
                } else if (!dbHelper.estaElUsuarioRegistrado(username,password)){
                    Toast.makeText(MainActivity.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                } else {
                    // Login exitoso
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();
                }

                // Crea un Intent para lanzar ParqueosActivity
                Intent intent = new Intent(MainActivity.this, ParqueosActivity.class);
                startActivity(intent);  // Inicia la nueva actividad
            }
        });
    }
}