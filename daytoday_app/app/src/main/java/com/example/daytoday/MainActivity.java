package com.example.daytoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.daytoday.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Global.arraylist;
import Pojo.archivo;
import adapters.AdaptadorGaleria;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    private ActivityMainBinding binding;
    SharedPreferences sesion;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sesion = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        arraylist.archivosDatos.clear();


        binding.RVGaleria.setLayoutManager(new GridLayoutManager(this, 3));
        binding.RVGaleria.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(this);
        cargarWebService();

        binding.fabGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irANuevoArchivo();
            }
        });
    }

    private void cargarWebService() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String url = "https://kratoskique26.000webhostapp.com/daytoday/webServiceConsultarArchivos.php?id="
                + sesion.getInt("id_user", 0);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(this, "La conexion fallo " + error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        archivo archivo = null;

        try {
            JSONArray json = response.optJSONArray("archivo");
            for (int i = 0; i < json.length(); i++) {
                archivo = new archivo();
                JSONObject jsonObject = null;

                jsonObject = json.getJSONObject(i);

                archivo.setId_archivo(jsonObject.optInt("id_archivos"));
                archivo.setNomObra(jsonObject.optString("nombre_obra"));
                archivo.setTipoArt(jsonObject.optString("tipo_arte"));
                archivo.setFecha(convertirFechaAComun(jsonObject.getString("fecha_obra")));
                archivo.setUsuario(jsonObject.optString("usuario_obra"));
                archivo.setDescripcion(jsonObject.optString("descripcion"));
                archivo.setPath(jsonObject.optString("path"));

                arraylist.archivosDatos.add(archivo);
            }
            progressDialog.hide();
            AdaptadorGaleria adapter = new AdaptadorGaleria(arraylist.archivosDatos);
            binding.RVGaleria.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se ha podido establecer conexion" +
                    " " + response, Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }
    }

    public static String convertirFechaAComun(String fecha){
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date fechaConvertida = formatoEntrada.parse(fecha);
            return formatoSalida.format(fechaConvertida);
        } catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    void irANuevoArchivo(){
        Intent intent = new Intent(this, subir_archivo.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.verPerfil:
                return true;

            case R.id.contacProgramador:
                Intent infoProgra = new Intent(this, Info_programador.class);
                startActivity(infoProgra);
                return true;

            case R.id.closeSesion:
               if (sesion.contains("id_user")){
                    SharedPreferences.Editor editor = sesion.edit();
                    editor.remove("id_user");
                    editor.commit();
                    Intent cerSesion = new Intent(this, login_createAccount.class);
                    startActivity(cerSesion);
                    finish();
                }
                return true;

            case R.id.galeria:
                Intent irGaleria = new Intent(this, MainActivity.class);
                startActivity(irGaleria);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}