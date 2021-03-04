package com.example.api;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class Animacion2 extends AppCompatActivity {
    private static final String CHANNEL_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //CONFIGURA LA APLICACION PARA QUE OCUPE TODA LA PANTALLA
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.animacion);

        ImageView MiImagen = (ImageView) findViewById(R.id.image);
        //CARGA LA ANIACION EN LA IMAGEN Y LA INICIA
        Animation MiAnim = AnimationUtils.loadAnimation(this, R.anim.my_animation2);
        MiImagen.startAnimation(MiAnim);



        //TRAS UN DELAY DE 8 SEGUNDOS SE INICIA LA ACTIVIDAD
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Animacion2.this, Registrar.class);
                startActivity(intent);
            }
        }, 1500);
    }


}
