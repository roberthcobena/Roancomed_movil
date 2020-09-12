package com.example.roancomed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PrincipalActivity extends AppCompatActivity {

    public static final String nombres = "nombres";
    public static final String id= "id";

    TextView cajaHola;
    Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnCerrar=findViewById(R.id.btnCerrar);
        cajaHola = (TextView)findViewById(R.id.textView);
        String usuario = getIntent().getStringExtra("nombres");
        cajaHola.setText("Bienvenido " + usuario);

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}