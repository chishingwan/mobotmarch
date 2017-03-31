package sti.devcon;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import  android.speech.tts.TextToSpeech;
import android.widget.VideoView;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements  android.speech.tts.TextToSpeech.OnInitListener, View.OnClickListener{
    TextToSpeech tts;
    VideoView vid;
    public static TextView editText;
    TextView srctxt;
    SQLiteDatabase db;
    Button play;
    Button bckMain;
    Button cal;
    Button emer;
    Button btnNext;
    TextView txtV;
    int ctr;
    EditText firstaid;
    String per="";
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctr = 0;
        setContentView(R.layout.activity_main);
        editText = (TextView) findViewById(R.id.tv1);
        srctxt = (TextView) findViewById(R.id.txtSrc);
        txtV = (TextView)findViewById(R.id.txtVid);
        bckMain = (Button)findViewById(R.id.bckMain);
        bckMain.setOnClickListener(this);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        btnNext.setVisibility(View.GONE);

        cal = (Button) findViewById(R.id.btnCall);
        emer = (Button) findViewById(R.id.btnEmer);
        cal.setOnClickListener(this);
        emer.setOnClickListener(this);
        cal.setVisibility(View.GONE);
        emer.setVisibility(View.GONE);

        firstaid=(EditText)findViewById(R.id.firstAid);
        firstaid.setVisibility(View.GONE);
        tts = new TextToSpeech(this, this);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        vid = (VideoView)findViewById(R.id.videoView);
        vid.setVisibility(View.GONE);

        play = (Button) findViewById(R.id.btnplay);
        play.setOnClickListener(this);
        play.setVisibility(View.GONE);
        resultText = (TextView)findViewById(R.id.tv);




    }
    //google api

    public void onClick(View v){
        if(v == btnNext){

            if (resultText.getText().equals("what is an earthquake") || resultText.getText().equals("earthquake")){

                String uriPath ="android.resource://" + getPackageName() +"/"+R.raw.eqdur;
                Uri uri = Uri.parse(uriPath);
                vid.setVisibility(View.VISIBLE);
                vid.setVideoURI(uri);
                vid.requestFocus();
                vid.start();

                editText.setText("During an earthquake, Stay where you are until the shaking stops. Do not run outside. Do not get in a doorway as this does not provide protection from falling or flying objects, and you may not be able to remain standing. Learn more about Safety by visiting the website below.");
                srctxt.setText("Source:  https://www.ready.gov/earthquakes");
                resultText.setText("during an earthquake");
                speech();


            }
        }
        if (v == cal){
            firstaid.setVisibility(View.VISIBLE);
            cal.setVisibility(View.GONE);
            emer.setVisibility(View.GONE);
        }
        if(v == emer){
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:9758883369"));
                        startActivity(callIntent);
        }

        if (v == bckMain){
            try {
                Intent i = new Intent(MainActivity.this, Home.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
            catch(Exception e){
                Toast.makeText(MainActivity.this, e.toString(),
                        Toast.LENGTH_LONG).show();
            }

        }
        if (v == play){


            if (resultText.getText().equals("what is an earthquake") || resultText.getText().equals("earthquake")){

                String uriPath ="android.resource://" + getPackageName() +"/"+R.raw.eqvid;
                Uri uri = Uri.parse(uriPath);
                vid.setVisibility(View.VISIBLE);
                vid.setVideoURI(uri);
                vid.requestFocus();
                vid.start();
                speech();
            }
            else if (resultText.getText().equals("what do i need to prepare before an earthquake")){

                String uriPath ="android.resource://" + getPackageName() +"/"+R.raw.eqduring;
                Uri uri = Uri.parse(uriPath);
                vid.setVisibility(View.VISIBLE);
                vid.setVideoURI(uri);
                vid.requestFocus();
                vid.start();
                speech();

            }
            else if (resultText.getText().equals("what do i need to do during an earthquake") || resultText.getText().equals("during an earthquake")){

                String uriPath ="android.resource://" + getPackageName() +"/"+R.raw.eqdur;
                Uri uri = Uri.parse(uriPath);
                vid.setVisibility(View.VISIBLE);
                vid.setVideoURI(uri);
                vid.requestFocus();
                vid.start();
                speech();
            }


        }

    }
    public void onButtonClick(View v){
        if (v.getId() == R.id.recBtn){

            promptSpeechInput();

        }
    }
    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Talk Now!");
        try{
            startActivityForResult(i,100);

        }
        catch(Exception e){
            Toast.makeText(MainActivity.this, e.toString(),
                    Toast.LENGTH_LONG).show();
        }


    }

    public void onActivityResult(int request_code,int result_code,Intent i){
        super.onActivityResult(request_code,result_code,i);
        switch (request_code){

            case 100: if (result_code == RESULT_OK && i != null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultText.setText(result.get(0).toLowerCase());
                try {
                    //Social condition
                    if (resultText.getText().equals("hey Moby")) {
                        editText.setText("Hey");
                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                    }
                    else if (resultText.getText().equals("hello")) {
                        editText.setText("Hi!");
                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);


                    }
                    else if (resultText.getText().equals("hi")) {
                        editText.setText("Hello!");
                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                    }
                    else if (resultText.getText().equals("good afternoon")) {
                        editText.setText("good afternoon too");
                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);
                    }
                    else if (resultText.getText().equals("good morning")) {
                        editText.setText("good morning too");
                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                    }
                    else if (resultText.getText().equals("good evening")) {
                        editText.setText("good evening too");
                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);


                    }
                        //Social app end

                    else if (resultText.getText().equals("can you help me")) {
                        editText.setText("Sure!");

                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                    }
                    else if (resultText.getText().equals("i have a question for you")) {
                        editText.setText("what is it?");

                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                    }
                    else if (resultText.getText().equals("i have a thing for you")) {
                        editText.setText("what is it?");

                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);
                    }
                    else if (resultText.getText().equals("do you know what is an earthquake")) {
                        editText.setText("Yes, why?");

                        play.setVisibility(View.GONE);
                        srctxt.setText("");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                    }
                    else if (resultText.getText().equals("tell us what is an earthquake")){
                        editText.setText("An earthquake is the perceptible shaking of the surface of the Earth, resulting from the sudden release of energy in the Earth's crust that creates seismic waves. Learn more about Earthquake by visiting the website below.");
                        srctxt.setText("Source:  http://www.eartheclipse.com/natural-disaster/causes-of-earthquakes.html\nVideo: https://www.youtube.com/watch?v=hkvkcN9rVHs");

                        play.setVisibility(View.VISIBLE);
                        String uriPath ="android.resource://" + getPackageName() +"/"+R.raw.eqvid;
                        Uri uri = Uri.parse(uriPath);
                        vid.setVisibility(View.VISIBLE);
                        vid.setVideoURI(uri);
                        vid.requestFocus();
                        vid.start();
                        txtV.setText("Video : https://www.youtube.com/watch?v=hkvkcN9rVHs");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                    }
                    else if (resultText.getText().equals("what is an earthquake") || resultText.getText().equals("earthquake") ) {
                        editText.setText("An earthquake is the perceptible shaking of the surface of the Earth, resulting from the sudden release of energy in the Earth's crust that creates seismic waves. Learn more about Earthquake by visiting the website below.");
                        srctxt.setText("Source:  http://www.eartheclipse.com/natural-disaster/causes-of-earthquakes.html");

                        play.setVisibility(View.VISIBLE);
                        String uriPath ="android.resource://" + getPackageName() +"/"+R.raw.eqvid;
                        Uri uri = Uri.parse(uriPath);
                        vid.setVisibility(View.VISIBLE);
                        vid.setVideoURI(uri);
                        vid.requestFocus();
                        vid.start();
                        btnNext.setVisibility(View.VISIBLE);
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                        txtV.setText("Video : https://www.youtube.com/watch?v=hkvkcN9rVHs");
                    }
                    else if (resultText.getText().equals("what do i need to prepare before an earthquake") || resultText.getText().equals("before an earthquake")) {
                        editText.setText("Keep on hand a flashlight; a portable radio with fresh batteries; a first-aid kit; a fire extinguisher; a three-day supply of fresh water; nonperishable, ready-to-eat foods; and an adjustable wrench for turning off gas and water.. Learn more about Safety by visiting the website below.");
                        srctxt.setText("Source:  https://dnr.mo.gov/geology/geosrv/geores/what2do.htm");

                        play.setVisibility(View.VISIBLE);
                        String uriPath ="android.resource://" + getPackageName() +"/"+R.raw.eqduring;
                        Uri uri = Uri.parse(uriPath);
                        vid.setVisibility(View.VISIBLE);
                        vid.setVideoURI(uri);
                        vid.requestFocus();
                        vid.start();
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                        txtV.setText("Video : https://www.youtube.com/watch?v=bsHfCj472HA");
                    }
                    else if (resultText.getText().equals("what do i need to do during an earthquake") || resultText.getText().equals("during an earthquake")) {
                        editText.setText("Stay where you are until the shaking stops. Do not run outside. Do not get in a doorway as this does not provide protection from falling or flying objects, and you may not be able to remain standing. Learn more about Safety by visiting the website below.");
                        srctxt.setText("Source:  https://www.ready.gov/earthquakes");
                        play.setVisibility(View.VISIBLE);


                        String uriPath ="android.resource://" + getPackageName() +"/"+R.raw.eqdur;
                        Uri uri = Uri.parse(uriPath);
                        vid.setVisibility(View.VISIBLE);
                        vid.setVideoURI(uri);
                        vid.requestFocus();
                        vid.start();
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);
                        txtV.setText("Video : https://www.youtube.com/watch?v=liw3hnAyV8U");

                    }
                    else if (resultText.getText().equals("what do i need to do after an earthquake") || resultText.getText().equals("after an earthquake")) {
                        editText.setText("Stay away from beaches. Tsunamis and seiches sometimes hit after the ground has stopped shaking. Learn more about Safety by visiting the website below.");
                        srctxt.setText("Source:  http://www.geo.mtu.edu/UPSeis/bda.html");
                        txtV.setText("");
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);
                    }


                    //voice command

                    else if (resultText.getText().equals("open voice command")){
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("List of Voice Commands");
                        alertDialog.setMessage("-show me the survival kit\n-open voice command");
                        alertDialog.show();
                        editText.setText("List of Voice Commands Granted");
                        txtV.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);


                    }
                    else if (resultText.getText().equals("open emergency sms")){
                        startActivity(new Intent(MainActivity.this,
                                GetLocation.class));
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                    }
                    else if (resultText.getText().equals("i need help") || resultText.getText().equals("I need help") ){

                        editText.setText("what is your situation?");
                        btnNext.setVisibility(View.GONE);

                        firstaid.setVisibility(View.GONE);
                        vid.setVisibility(View.GONE);
                        play.setVisibility(View.GONE);
                        txtV.setText("");
                        srctxt.setText("");
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                    }
                    else if (resultText.getText().equals("i got hurt") || resultText.getText().equals("I got hurt")  || resultText.getText().equals("i got sick") || resultText.getText().equals("i've got sick") || resultText.getText().equals("i've got hurt")){

                        editText.setText("I suggest the following: \nFollow First Aid Instructions or Call your Emergency Contact");
                        cal.setVisibility(View.VISIBLE);
                        emer.setVisibility(View.VISIBLE);
                        vid.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        play.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);



                        txtV.setText("");
                        srctxt.setText("");
                    }
                    else {
                        editText.setText("Sorry?");
                        srctxt.setText("");
                        getWindow().setFormat(PixelFormat.UNKNOWN);
                        vid = (VideoView)findViewById(R.id.videoView);
                        vid.setVisibility(View.GONE);
                        play.setVisibility(View.GONE);
                        cal.setVisibility(View.GONE);
                        emer.setVisibility(View.GONE);
                        btnNext.setVisibility(View.GONE);
                        firstaid.setVisibility(View.GONE);

                        txtV.setText("");

                    }
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, e.toString(),
                            Toast.LENGTH_LONG).show();

                }
                finally {

                    speech();
                    }
            }
                break;
        }

    }
    //google api end
    public void speech(){
        try {
            tts.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

        }
        catch(Exception e){
            Toast.makeText(MainActivity.this, e.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onInit(int status) {

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

    catch(Exception e){
        Toast.makeText(MainActivity.this, e.toString(),
                Toast.LENGTH_LONG).show();


    }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        tts.shutdown();
    }


}
