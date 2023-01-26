package com.example.daytoday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.daytoday.databinding.ActivitySubirArchivoBinding;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Pojo.archivo;

public class subir_archivo extends AppCompatActivity{
    private ActivitySubirArchivoBinding binding;
    ArrayAdapter<CharSequence> adapterMenuArt;
    String currentPhotoPath;
    Bitmap decoded;
    SharedPreferences sesion;
    ProgressDialog progressDialog;
    archivo archivo = new archivo();

    RequestQueue requestQueue;
    //ExitInterface para la rotacion de la imagen

    static final int REQUEST_PERMISSION_CAMERA = 100;
    static final int REQUEST_TAKE_PHOTO = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubirArchivoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sesion = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        dropMenuArt();

        binding.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

        binding.btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        binding.SAFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFecha();
            }
        });
    }

    private void onClickFecha() {
        int dia, mes, anio;
        Calendar actual = Calendar.getInstance();
        dia = actual.get(Calendar.DAY_OF_MONTH);
        mes = actual.get(Calendar.MONTH);
        anio = actual.get(Calendar.YEAR);
        DatePickerDialog datPd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                archivo.dia = dayOfMonth;
                archivo.mes = month + 1;
                archivo.anio = year;
                binding.SAFecha.setText("" + archivo.dia + "/" + archivo.mes + "/" + archivo.anio);
            }
        }, dia, mes, anio);
        datPd.show();
    }


    //Crear file de la foto
    private File createImageFile() throws IOException{
        String timeStap = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG" + timeStap + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    };

    //Toma la foto
    private void takePictureFullSize(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(this, "com.example.daytoday", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //Revisa los permisos
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                takePictureFullSize();
            }else{ ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSION_CAMERA);
            }
        }else{
            takePictureFullSize();
        }
    }

    //Ajusta el tamaño de la imagen
    private void setToImageView(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        binding.foto.setImageBitmap(decoded);
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        if (width <= maxSize && height <= maxSize){
            return bitmap;
        }

        float bitmapRadio = (float) width / (float) height;
        if (bitmapRadio > 1){
            width = maxSize;
            height = (int) (width/bitmapRadio);
        }else{
            height = maxSize;
            width = (int) (height * bitmapRadio);
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] imageBytes = baos.toByteArray();
        
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void uploadImage(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Subiendo archivo");
        progressDialog.show();
        String url = "https://kratoskique26.000webhostapp.com/daytoday/webServiceUpload.php";
        String fechaFormatoMYSQL = convertirFechaASQL(binding.SAFecha.getText().toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
                        Toast.makeText(subir_archivo.this, "Correcto", Toast.LENGTH_SHORT).show();

                        Intent irGaleria = new Intent(subir_archivo.this, MainActivity.class);
                        startActivity(irGaleria);
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toast.makeText(subir_archivo.this, "La conexion fallo " + error.toString(), Toast.LENGTH_SHORT).show();
                }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                params.put("path", getStringImage(decoded));
                params.put("idUsuario", "" + sesion.getInt("id_user", 0));
                params.put("nombreObra", binding.SANombre.getText().toString());
                params.put("tipo", binding.SAArte.getText().toString());
                params.put("fecha", fechaFormatoMYSQL);
                params.put("desc", binding.SADescripcion.getText().toString());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public static String convertirFechaASQL(String fecha){
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date fechaConvertida = formatoEntrada.parse(fecha);
            return formatoSalida.format(fechaConvertida);
        } catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_CAMERA){
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePictureFullSize();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            try {
                ExifInterface exif = new ExifInterface(currentPhotoPath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);
                setToImageView(rotatedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation){
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    void dropMenuArt(){

        adapterMenuArt = ArrayAdapter.createFromResource(this, R.array.Artes, R.layout.drop_menu_items);
        binding.SAArte.setAdapter(adapterMenuArt);

        binding.SAArte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }


}