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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.view.MenuItem;  // Asegúrate de que esto esté presente
import androidx.annotation.NonNull;



public class ParqueosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    AdminSQLiteOpenHelper adminSQLiteOpenHelper;
    String usuario = "userTesting";

    private DrawerLayout drawerLayout;

    RecyclerView recyclerViewParqueos;
    ParqueosAdapter parqueosAdapter;
    List<Parqueo> parqueoList;

    // Obtengo el user validado desde El main
    //usuario = getIntent().getStringExtra("usuario");


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

        // Configurar DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Configurar el botón hamburguesa
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void cargarParqueos() {
        Cursor cursor = adminSQLiteOpenHelper.obtenerParqueosPorUsuario(usuario);

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int matriculaIndex = cursor.getColumnIndex("nro_matricula");
                    int tiempoIndex = cursor.getColumnIndex("tiempo");

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_parqueos) {
            // Acciones al seleccionar "Parqueos"
        } else if (item.getItemId() == R.id.nav_mi_cuenta) {
            // "Mi cuenta" es meramente estético
        }

        // Cierra el drawer después de seleccionar una opción
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
