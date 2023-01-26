package com.example.daytoday;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.daytoday.databinding.FragmentCreateAccountBinding;

import org.json.JSONObject;

import java.util.Calendar;

import Pojo.usuario;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_createAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_createAccount extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_createAccount() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_createAccount.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_createAccount newInstance(String param1, String param2) {
        fragment_createAccount fragment = new fragment_createAccount();
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
    
    private FragmentCreateAccountBinding binding;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    usuario usuario = new usuario();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateAccountBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        requestQueue = Volley.newRequestQueue(root.getContext());

        
        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCreateAccount();
            }
        });
        
        return root;
    }

    private void onClickCreateAccount() {
        if (binding.CANombre.getText().toString().isEmpty() ||
        binding.CACorreo.getText().toString().isEmpty() ||
        binding.CAContrasena.getText().toString().isEmpty()||
        binding.CAConfContra.getText().toString().isEmpty()||
        binding.CAApellidop.getText().toString().isEmpty() ||
        binding.CAApellidom.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Faltan campos necesarios", Toast.LENGTH_SHORT).show();
        }else{
            if (binding.CAConfContra.getText().toString().equals(binding.CAContrasena.getText().toString())){

                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Creando cuenta");
                progressDialog.show();

                String url = "https://kratoskique26.000webhostapp.com/daytoday/webServiceCreateAccount.php?" +
                        "nombre_user=" + binding.CANombre.getText().toString() +
                        "&contrasena=" + binding.CAContrasena.getText().toString() +
                        "&correo="+ binding.CACorreo.getText().toString() +
                        "&apellido_p="+ binding.CAApellidop.getText().toString() +
                        "&apellido_m=" + binding.CAApellidom.getText().toString();

                url = url.replace(" ", "%20");

                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                requestQueue.add(jsonObjectRequest);
            }else{
                Toast.makeText(getContext(), "La contraseña no coincide", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(getContext(), "La conexion fallo " + error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();
        Toast.makeText(getContext(), "Cuenta creada", Toast.LENGTH_SHORT).show();
        binding.CANombre.setText("");
        binding.CAContrasena.setText("");
        binding.CACorreo.setText("");
        binding.CAApellidop.setText("");
        binding.CAApellidom.setText("");
        binding.CAConfContra.setText("");
    }
}