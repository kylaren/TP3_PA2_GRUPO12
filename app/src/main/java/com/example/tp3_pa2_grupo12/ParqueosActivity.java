package com.example.tp3_pa2_grupo12;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;

public class ParqueosActivity extends AppCompatActivity {

    AdminSQLiteOpenHelper adminSQLiteOpenHelper;
    String usuario = "userTesting";

    RecyclerView recyclerViewParqueos;
    ParqueosAdapter parqueosAdapter;
    List<Parqueo> parqueoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueos);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Parking Control");
        getSupportActionBar().setSubtitle("Parqueos");

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        recyclerViewParqueos = findViewById(R.id.recyclerViewParqueos);
        recyclerViewParqueos.setLayoutManager(new GridLayoutManager(this, 2));

        adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(this);
        parqueoList = new ArrayList<>();

        cargarParqueos();

        parqueosAdapter = new ParqueosAdapter(this, parqueoList);
        recyclerViewParqueos.setAdapter(parqueosAdapter);
    }

    private void cargarParqueos() {
        Cursor cursor = adminSQLiteOpenHelper.obtenerParqueos();

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int matriculaIndex = cursor.getColumnIndex("nro_matricula");
                    int tiempoIndex = cursor.getColumnIndex("tiempo");

                    // Verifica que los índices no sean -1
                    if (matriculaIndex != -1 && tiempoIndex != -1) {
                        String matricula = cursor.getString(matriculaIndex);
                        String tiempo = cursor.getString(tiempoIndex);
                        parqueoList.add(new Parqueo(matricula, tiempo));
                    }
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null) {
            cursor.close();
        }
    }


    private void showRegisterDialog() {
        final EditText matriculaInput = new EditText(this);
        matriculaInput.setHint("Nro de matrícula");
        matriculaInput.setInputType(InputType.TYPE_CLASS_TEXT); // Acepta texto (letras y números)

        final EditText tiempoInput = new EditText(this);
        tiempoInput.setHint("Tiempo");
        tiempoInput.setInputType(InputType.TYPE_CLASS_NUMBER); // Solo números

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(matriculaInput);
        layout.addView(tiempoInput);

        new AlertDialog.Builder(this)
                .setTitle("Registrar parqueo")
                .setView(layout)
                .setPositiveButton("REGISTRAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String matricula = matriculaInput.getText().toString();
                        String tiempo = tiempoInput.getText().toString();

                        if (!matricula.isEmpty() && !tiempo.isEmpty()) {
                            adminSQLiteOpenHelper.insertarParqueo(matricula, tiempo, usuario);
                            // Después de registrar, recargar los parqueos para actualizar la vista
                            parqueoList.clear(); // Limpiar la lista antes de cargar nuevos datos
                            cargarParqueos();
                            parqueosAdapter.notifyDataSetChanged(); // Notificar cambios al adapter
                        }
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }


}
