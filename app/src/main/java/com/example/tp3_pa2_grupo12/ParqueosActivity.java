package com.example.tp3_pa2_grupo12;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import android.widget.LinearLayout;

public class ParqueosActivity extends AppCompatActivity {

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
    }

    private void showRegisterDialog() {

        final EditText matriculaInput = new EditText(this);
        matriculaInput.setHint("Nro de matrícula");

        final EditText tiempoInput = new EditText(this);
        tiempoInput.setHint("Tiempo");


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
