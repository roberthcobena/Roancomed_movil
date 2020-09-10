package com.example.roancomed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtUsu,txtPas;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsu=findViewById(R.id.txtusu);
        txtPas=findViewById(R.id.txtpas);
        btnLogin=findViewById(R.id.btnIngresar);

        recuperarPreferencias();

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Thread tr= new Thread(){
            @Override
            public void run() {
                final String resultado=enviarDatos(txtUsu.getText().toString(),txtPas.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r=obtenerDatos(resultado);
                        if (r>0){
                            Intent i=new Intent(getApplicationContext(),PrincipalActivity.class);
                            i.putExtra("email",txtUsu.getText().toString());
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Usuario o Contraseña incorrectos",Toast.LENGTH_LONG).show();
                            txtUsu.setText(null);
                            txtPas.setText(null);
                        }
                    }
                });
            }
        };
        tr.start();
    }

    private String enviarDatos(String usu, String pas) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;

        try {
            url = new URL("http://192.168.1.11/roancomed/vendor/android/servicios/validar_usuario.php?usuario=" + usu + "&password=" + pas);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            respuesta = connection.getResponseCode();

            resul = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((linea = reader.readLine()) != null) {
                    resul.append(linea);
                }
            }
        }catch (Exception e) {}
        return resul.toString();
    }

    public int obtenerDatos(String response){
        int res=0;
            try{
                JSONArray json = new JSONArray(response);
                if (json.length()>0){
                    res=1;
                }
            }catch (Exception e){}
        return res;
    }


    private void recuperarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
        txtUsu.setHint(preferences.getString("usuario","Ingrese correo"));
        txtPas.setHint(preferences.getString("password","Ingrese Contraseña"));
    }


}