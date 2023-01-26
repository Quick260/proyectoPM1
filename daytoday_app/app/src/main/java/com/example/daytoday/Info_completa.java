package com.example.daytoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.daytoday.databinding.ActivityInfoCompletaBinding;
import com.example.daytoday.databinding.ActivityMainBinding;

import org.json.JSONObject;

import Global.arraylist;

public class Info_completa extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    private ActivityInfoCompletaBinding binding;
    Toolbar toolbar;
    SharedPreferences sesion;
    int posArray , whatOnClick;
    ArrayAdapter<CharSequence> adapterMenuArt;


    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    ProgressDialog progressDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoCompletaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sesion = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        dropMenuArt();
        cachar();
        llenarText();

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEdit();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelete();
            }
        });
    }

    private void onClickDelete() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Borrando...");
        progressDialog.show();

        whatOnClick = 1;

        String url = "https://kratoskique26.000webhostapp.com/daytoday/webServiceDelete.php?id="
                + arraylist.archivosDatos.get(posArray).getId_archivo();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    private void onClickEdit(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Modificando...");
        progressDialog.show();

        whatOnClick = 0;
        String url = "https://kratoskique26.000webhostapp.com/daytoday/webServiceModificar.php?" +
                "id=" + arraylist.archivosDatos.get(posArray).getId_archivo() +
                "&nom=" + binding.EDNombre.getText().toString() +
                "&art=" + binding.EDArte.getText().toString() +
                "&fecha=" + subir_archivo.convertirFechaASQL(binding.EDFecha.getText().toString()) +
                "&desc=" + binding.EDDescripcion.getText().toString();
        url = url.replace(" ", "%20");

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
        if (whatOnClick == 0){
            progressDialog.hide();
            Toast.makeText(this, "Obra modificada exitosamente ", Toast.LENGTH_SHORT).show();
        }
        if (whatOnClick == 1){
            progressDialog.hide();
            Toast.makeText(this, "Obra Borrada exitosamente ", Toast.LENGTH_SHORT).show();
        }
        Intent irGaleria = new Intent(this, MainActivity.class);
        startActivity(irGaleria);
    }

    private void llenarText() {
        String imageUrl = arraylist.archivosDatos.get(posArray).getPath();
        Glide.with(this).load(imageUrl).into(binding.foto);

        binding.EDNombre.setText(arraylist.archivosDatos.get(posArray).getNomObra());
        binding.EDArte.setText(arraylist.archivosDatos.get(posArray).getTipoArt());
        binding.EDFecha.setText(arraylist.archivosDatos.get(posArray).getFecha());
        binding.EDDescripcion.setText(arraylist.archivosDatos.get(posArray).getDescripcion());
    }

    private void cachar() {
        Bundle extras = getIntent().getExtras();
        posArray = extras.getInt("pos");
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

    void dropMenuArt(){

        adapterMenuArt = ArrayAdapter.createFromResource(this, R.array.Artes, R.layout.drop_menu_items);
        binding.EDArte.setAdapter(adapterMenuArt);

        binding.EDArte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }


}