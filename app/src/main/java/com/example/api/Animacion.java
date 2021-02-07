package com.example.api;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Animacion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CONFIGURA LA APLICACION PARA QUE OCUPE TODA LA PANTALLA
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.animacion);

        ImageView MiImagen = (ImageView) findViewById(R.id.image);
        //CARGA LA ANIACION EN LA IMAGEN Y LA INICIA
        Animation MiAnim = AnimationUtils.loadAnimation(this, R.anim.my_animation);
        MiImagen.startAnimation(MiAnim);

        //TRAS UN DELAY DE 8 SEGUNDOS SE INICIA LA ACTIVIDAD
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Animacion.this, MainActivity.class);
                startActivity(intent);
            }
        },8000);
    }


}
