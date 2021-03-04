package com.example.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Animacion2 extends AppCompatActivity {
    private String ges;
    private String root;
    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferencias = getSharedPreferences("variables", Context.MODE_PRIVATE);
        ges = preferencias.getString("Extra_ges", "");
        root = preferencias.getString("Extra_root", "");
        page = preferencias.getString("Extra_page", "");
        SharedPreferences.Editor editor = preferencias.edit();
        editor.remove("Extra_ges");
        editor.remove("Extra_root");
        editor.remove("Extra_usu");
        editor.commit();

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
                Intent intent = new Intent(Animacion2.this, MainActivity.class);
                startActivity(intent);
            }
        }, 8000);

        // Va a modificar el telefono y asa las propiedades
        SharedPreferences preferencias2 = getSharedPreferences("variables", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferencias2.edit();
        editor2.putString("Extra_ges", ges);
        editor2.putString("Extra_root", root);
        editor2.commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (page.equals("tel")) {
                    Intent intent = new Intent(Animacion2.this, ModTel.class);
                    startActivity(intent);
                } else if (page.equals("borrar")) {
                    Intent intent = new Intent(Animacion2.this, BorrarUsuario.class);
                    startActivity(intent);
                } else if (page.equals("datos")) {
                    Intent intent = new Intent(Animacion2.this, VerDatos.class);
                    startActivity(intent);
                } else if (page.equals("rol")) {
                    Intent intent = new Intent(Animacion2.this, ModRol.class);
                    startActivity(intent);
                } else if (page.equals("video")) {
                    Intent intent = new Intent(Animacion2.this, Video.class);
                    startActivity(intent);
                } else if (page.equals("poke")) {
                    Intent intent = new Intent(Animacion2.this, Poke.class);
                    startActivity(intent);
                }
            }
        }, 1500);


    }


}
