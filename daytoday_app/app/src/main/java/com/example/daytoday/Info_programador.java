package com.example.daytoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.daytoday.databinding.ActivityInfoProgramadorBinding;

public class Info_programador extends AppCompatActivity {
    SharedPreferences sesion;
    private ActivityInfoProgramadorBinding binding;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoProgramadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamando();
            }
        });
    }

    private void llamando() {
        Intent llamada = new Intent(Intent.ACTION_CALL);
        llamada.setData(Uri.parse("tel:3332325275"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.CALL_PHONE}, 10
            );
        }
        startActivity(llamada);
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