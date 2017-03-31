package sti.devcon;

import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Locale;

public class LoadingScreen extends AppCompatActivity implements  TextToSpeech.OnInitListener{
    TextToSpeech tts;
    protected boolean active = true;
    protected int splashTime = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        tts = new TextToSpeech(this, this);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < splashTime)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }

                    }

                } catch (Exception e) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoadingScreen.this).create();
                    alertDialog.setTitle("error");
                    alertDialog.setMessage(e.toString());
                    alertDialog.show();

                } finally {
                    speech();
                    startActivity(new Intent(LoadingScreen.this,
                            Home.class));

                    try{
                    int waited = 0;
                    while (active && (waited < 10000)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }


                    }
                        finish();
                    }
                        catch(Exception ex){
                            AlertDialog alertDialog = new AlertDialog.Builder(LoadingScreen.this).create();
                            alertDialog.setTitle("error");
                            alertDialog.setMessage(ex.toString());
                            alertDialog.show();

                        }

                }

            };
        };
        splashTread.start();


    }
    public void speech(){


        tts.speak("Welcome user!, I'm Mo Bot, Your Mobile Support Bot for disaster preparedness and public safety.",TextToSpeech.QUEUE_FLUSH,null);
    }
    public void onInit(int status){
        try {
            if (status == TextToSpeech.SUCCESS) {
                Locale bahasa = tts.getLanguage();
                int result = tts.setLanguage(bahasa);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported");
                }


            } else {
                Log.e("TTS", "Initialization Failed!");
            }
        }
        catch (Exception e){

            AlertDialog alertDialog = new AlertDialog.Builder(LoadingScreen.this).create();
            alertDialog.setTitle("error");
            alertDialog.setMessage(e.toString());
            alertDialog.show();

        }

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        tts.shutdown();
    }

}
