package com.example.roancomed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    RequestQueue rq;
    JsonRequest jrq;
    EditText Cajaname, Cajamail, Cajapas;
    Button btnRegistro, btnSesion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_session, container, false);
        View vista = inflater.inflate(R.layout.fragment_session, container, false);
        Cajaname=(EditText) vista.findViewById(R.id.txtnames);
        Cajamail=(EditText) vista.findViewById(R.id.txtemail);
        Cajapas=(EditText) vista.findViewById(R.id.txtcontra);
        btnRegistro=(Button) vista.findViewById(R.id.btnRegistrar);
        btnSesion=(Button) vista.findViewById(R.id.btnIrInicio);

        rq = Volley.newRequestQueue(getContext());

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar_sesion();
            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarusuario();
            }
        });
        return inflater.inflate(R.layout.fragment_registrar, container, false);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se pudo registrar el usuario ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "Usuario registrado " + Cajaname.getText().toString(), Toast.LENGTH_SHORT).show();
        limpiar_cajas();
    }

    void limpiar_cajas(){
        Cajaname.setText("");
        Cajamail.setText("");
        Cajapas.setText("");
        Intent intencion = new Intent(getContext(), PrincipalActivity.class);
        startActivity(intencion);
    }

    void iniciar_sesion(){
        SessionFragment fr = new SessionFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.escenario,fr)
                .addToBackStack(null)
                .commit();
    }

    void registrarusuario(){
        String url= "https://roancomed.000webhostapp.com/vendor/android/servicios/registro_usuario.php?nombre="+Cajaname.getText().toString()+"&mail="+Cajamail.getText().toString()+"&password="+Cajapas.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
}