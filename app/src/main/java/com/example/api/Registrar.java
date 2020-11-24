package com.example.api;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entradas.Entradas;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Registrar extends AppCompatActivity {

    private final String Extra_res = "res";

    CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
    BBDD cbdd = new BBDD(this);

    final int COD_MARCADA = 10;
    final int COD_FOTO = 20;

    ImageView miFoto;
    Button miBoton;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_registro);
        miFoto = (ImageView) findViewById(R.id.laFoto);
        miBoton = (Button) findViewById(R.id.btnCargarFoto);
        if (validarPermisos()) {
            miBoton.setEnabled(true);
        } else {
            miBoton.setEnabled(false);
        }
    }

    public void Aceptar(View v) {
        cbdd.openForWrite();

        EditText campoNombre = findViewById(R.id.editText1);
        EditText campoContra = findViewById(R.id.editText2);
        EditText campoTel = findViewById(R.id.editText3);

        String nom = campoNombre.getText().toString();
        String con = campoContra.getText().toString();
        String tel = campoTel.getText().toString();
        if ((nom == null) || (con == null) || (tel == null) || (currentPhotoPath==null)) {
            Toast.makeText(getApplicationContext(), "Todos los datos son obligatorios", Toast.LENGTH_SHORT).show();
        }else{
            if (Entradas.comprNumerico(tel.toString()) == true) {

                Usuario usu = new Usuario(nom, con, Integer.parseInt(tel),  0, 0, currentPhotoPath);
                System.out.println(usu);
                cbdd.insertUsuario(usu);

                SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("Mensaje", "Registro realizado");
                editor.commit();

                Intent intent = new Intent(Registrar.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Introduce solo numeros en el campo telefono", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Volver(View v) {

        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);

    }

    private boolean validarPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }
        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogo();
        } else { //primera vez
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }
        return false;
    }

    private void cargarDialogo() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder((Registrar.this));
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe dar permisos para que funcione la aplicación");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
                }
            }
        });
        dialogo.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                miBoton.setEnabled(true);
            } else {
                pedirPermisosManual();
            }
        }
    }

    private void pedirPermisosManual() {
        final CharSequence[] opc = {"si", "no"};
        final AlertDialog.Builder alerta_Opc = new AlertDialog.Builder((Registrar.this));
        alerta_Opc.setTitle("¿Desea dar permisos de forma manual?");
        alerta_Opc.setItems(opc, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opc[which].equals("si")) {
                    Intent miInten = new Intent();
                    miInten.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri miUri = Uri.fromParts("package", getPackageName(), null);
                    miInten.setData(miUri);
                    startActivity(miInten);
                    /////////////////////////////////////////////
                    //Para comprobar de nuevo si se activaron permisos de forma manual y así activar botón
                    if (validarPermisos()) {
                        miBoton.setEnabled(true);
                    }
                    /////////////////////////////////////////////
                } else {
                    Toast.makeText(getApplicationContext(), "Los permisos no fueron aceptados", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
        alerta_Opc.show();
    }

    public void onClick(View view) {
        cargarFoto();
    }

    private void cargarFoto() {
        final CharSequence[] opc = {"Hacer Foto", "Cancelar"};
        final AlertDialog.Builder alerta_Opc = new AlertDialog.Builder((Registrar.this));
        alerta_Opc.setTitle("Marque una opción");
        alerta_Opc.setItems(opc, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opc[which].equals("Hacer Foto")) {
                    hacerFoto();
                    Toast.makeText(getApplicationContext(), "Hacer fotos", Toast.LENGTH_LONG).show();
                }

            }
        });
        alerta_Opc.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case COD_MARCADA:
                    Uri mipath = data.getData();
                    miFoto.setImageURI(mipath);
                    break;
                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento", "path: " + path);
                                    Log.i("ExternalStorage", "-> uri=" + uri);
                                }
                            });
                    //Toast.makeText(getApplicationContext(), "Hacer fotos", Toast.LENGTH_LONG).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                    miFoto.setImageBitmap(bitmap);
                    break;
            }
        }
    }

    private void hacerFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // cerramos el activity principal y ponemos la camara
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // verificamos si se realizó la foto previamente
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("OJO:", "Error en camara");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.api.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, COD_FOTO); //REQUEST_TAKE_PHOTO);
            }
        }
    }

    //Para crear un nombre único de la foto
    String currentPhotoPath; //ojo este es el path que usaremos en onActivityResult

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath(); //Variable global
        return image;
    }

}
