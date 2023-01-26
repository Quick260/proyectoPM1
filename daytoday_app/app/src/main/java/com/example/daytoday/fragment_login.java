package com.example.daytoday;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.daytoday.databinding.FragmentCreateAccountBinding;
import com.example.daytoday.databinding.FragmentLoginBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Pojo.usuario;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_login extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_login.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_login newInstance(String param1, String param2) {
        fragment_login fragment = new fragment_login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private FragmentLoginBinding binding;
    SharedPreferences sesion;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        sesion = root.getContext().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(root.getContext());

        if (sesion.contains("id_user")){
            Intent intentlog = new Intent(getContext(), MainActivity.class);
            startActivity(intentlog);
        }


        binding.btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });

        return root;
    }

    private void onClickLogin() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Ingresando");
        progressDialog.show();

        String url = "https://kratoskique26.000webhostapp.com/daytoday/webServiceLogin.php?" +
                "correo=" + binding.correoLogin.getText().toString() +
                "&contrasena=" + binding.passwordLogin.getText().toString();
        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(getContext(), "La conexion fallo " + error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();

        Pojo.usuario usuario = new usuario();

        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setId_usuario(jsonObject.optInt("id_user"));
            usuario.setNombre(jsonObject.optString("nombre_user"));
            usuario.setApellidoP(jsonObject.optString("apellido_p"));
            usuario.setApellidoM(jsonObject.optString("apellido_m"));
            usuario.setPassword(jsonObject.optString("password"));
            usuario.setCorreo(jsonObject.optString("correo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (usuario.getId_usuario() != 0){
            Intent intentlog = new Intent(getContext(), MainActivity.class);

            SharedPreferences.Editor editor = sesion.edit();
            editor.putInt("id_user", usuario.getId_usuario());
            editor.putString("nombre_user", usuario.getNombre());
            editor.putString("apellido_p", usuario.getApellidoP());
            editor.putString("apellido_m", usuario.getApellidoM());
            editor.putString("password", usuario.getPassword());
            editor.putString("correo", usuario.getCorreo());

            editor.commit();
            startActivity(intentlog);
        }else {
            Toast.makeText(getContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }

    }
}