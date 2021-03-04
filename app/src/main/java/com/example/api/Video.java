package com.example.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;


public class Video extends Activity implements View.OnClickListener {
    String ges;
    String root;
    private Button btnPlay;
    private Button btnPause;
    private VideoView video;
    private VideoView video2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //Inicializamos la clase VideoView asociandole el fichero de Video

        video = (VideoView) findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.opening;
        video.setVideoURI(Uri.parse(path));

        video2 = (VideoView) findViewById(R.id.videoView2);
        path = "android.resource://" + getPackageName() + "/" + R.raw.videoplayback;
        video2.setVideoURI(Uri.parse(path));
        video2.start();

        // shared preference recibe el mensaje cuando registra si va bien.
        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        ges = preferencias.getString("Extra_ges", "");
        root = preferencias.getString("Extra_root", "");
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("Extra_ges");
        editor.remove("Extra_root");
        editor.remove("Extra_usu");
        editor.commit();

        //Obtenemos los tres botones de la interfaz
        btnPlay = (Button) findViewById(R.id.buttonPlay);
        btnPause = (Button) findViewById(R.id.buttonPause);

        //Y les asignamos el controlador de eventos
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //Comprobamos el identificador del boton que ha llamado al evento para ver
        //cual de los botones es y ejecutar la acción correspondiente
        switch (v.getId()) {
            case R.id.buttonPlay:
                //Iniciamos el video
                video.start();
                break;
            case R.id.buttonPause:
                //Pausamos el video
                video.pause();
                break;

        }
    }

    public void miVolver(View view) {
        // Va a ver roles y asa las propiedades
        SharedPreferences preferencias5 = getSharedPreferences("variables", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor5 = preferencias5.edit();
        editor5.putString("Extra_ges", ges);
        editor5.putString("Extra_root", root);
        editor5.commit();

        Intent intent5 = new Intent(Video.this, MenuUsuario.class);
        startActivity(intent5);
    }
}

