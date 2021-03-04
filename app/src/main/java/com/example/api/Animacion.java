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

public class Animacion extends AppCompatActivity {
    private static final String CHANNEL_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BBDD cbdd = new BBDD(this);
        cbdd.openForRead();

        cbdd.openForWrite();
        if (null == cbdd.getUsuarios()) {
            cbdd.close();
            CapituloBaseSQLite bd = new CapituloBaseSQLite(this, "Manga", null, 1);
            cbdd.openForWrite();
            cbdd.removeAll();
        }
        cbdd.close();

        super.onCreate(savedInstanceState);
        //CONFIGURA LA APLICACION PARA QUE OCUPE TODA LA PANTALLA
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.animacion);

        ImageView MiImagen = (ImageView) findViewById(R.id.image);
        //CARGA LA ANIACION EN LA IMAGEN Y LA INICIA
        Animation MiAnim = AnimationUtils.loadAnimation(this, R.anim.my_animation);
        MiImagen.startAnimation(MiAnim);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default");

        Intent notifyIntent = new Intent(this, MainActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        mBuilder.setSmallIcon(R.drawable.lanzador)
                .setContentTitle(getResources().getString(R.string.notification))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentText(getResources().getString(R.string.gracias))
                .setContentIntent(resultPendingIntent);


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

        //TRAS UN DELAY DE 8 SEGUNDOS SE INICIA LA ACTIVIDAD
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Animacion.this, MainActivity.class);
                startActivity(intent);
            }
        }, 8000);
    }


}
