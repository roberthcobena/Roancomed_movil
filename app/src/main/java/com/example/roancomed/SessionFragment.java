package com.example.roancomed;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class SessionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    RequestQueue rq;
    JsonRequest jrq;
    EditText cajaUser, cajaPwd;
    Button btnIngresar, btnRegistrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_session, container, false);
        View vista = inflater.inflate(R.layout.fragment_session, container, false);
        cajaUser=(EditText) vista.findViewById(R.id.txtemail);
        cajaPwd=(EditText) vista.findViewById(R.id.txtpas);
        btnIngresar=(Button) vista.findViewById(R.id.btnIngresar);
        btnRegistrar=(Button) vista.findViewById(R.id.btnRegistro);
        rq = Volley.newRequestQueue(getContext());

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registar_usuario();
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });
        return inflater.inflate(R.layout.fragment_session, container, false);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se ha encontrado el usuario ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        User usuario = new User();
        Toast.makeText(getContext(), "Bienvenido " + cajaUser.getText().toString(), Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try{
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setId(jsonObject.optString("id"));
            usuario.setEmail(jsonObject.optString("email"));
            usuario.setPwd(jsonObject.optString("password"));
            usuario.setNombres(jsonObject.optString("nombres"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        Intent intencion = new Intent(getContext(), PrincipalActivity.class);
        intencion.putExtra(PrincipalActivity.id, usuario.getId());
        intencion.putExtra(PrincipalActivity.nombres, usuario.getNombres());
        startActivity(intencion);
    }

    void iniciarSesion(){
        String url= "https://roancomed.000webhostapp.com/vendor/android/servicios/validar_usuario.php?usuario="+cajaUser.getText().toString()+"&password="+cajaPwd.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    void registar_usuario(){
        RegistrarFragment fr = new RegistrarFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.escenario,fr)
                .addToBackStack(null)
                .commit();
    }
}