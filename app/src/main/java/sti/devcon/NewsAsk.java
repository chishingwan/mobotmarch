package sti.devcon;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import java.util.ArrayList;
import java.util.Locale;


public class NewsAsk extends AppCompatActivity implements  TextToSpeech.OnInitListener, View.OnClickListener{

    public static EditText ans;
    TextView textview;
    private TextView resultText;
    Button bck;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_ask);
        ans = (EditText)findViewById(R.id.aTxt);
        textview = (TextView) findViewById(R.id.tv);
        resultText = (TextView) findViewById(R.id.qTxt);
        bck = (Button)findViewById(R.id.btnBackAsk);
        bck.setOnClickListener(this);
        tts = new TextToSpeech(this, this);
    }


    public void onButtonClick(View v){
        if (v.getId() == R.id.recBtn){

            promptSpeechInput();

        }


    }
    public void onClick(View v){
        if (v == bck){
            try {
                finish();
            }
            catch(Exception e){
                Toast.makeText(NewsAsk.this, e.toString(),
                        Toast.LENGTH_LONG).show();
            }

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
        catch(ActivityNotFoundException e){
            Toast.makeText(NewsAsk.this,"Sorry not supported",Toast.LENGTH_LONG).show();
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
                    if (resultText.getText().equals("hey")) {
                        ans.setText("Hey");

                    }
                    else if (resultText.getText().equals("what is a storm surge")) {
                        ans.setText("Storm surge is the abnormal rise in seawater level during a storm, measured as the height of the water above the normal predicted astronomical tide. Source: National Ocean Service");

                    }
                    else if (resultText.getText().equals("storm surge")) {
                        ans.setText("Storm surge is the abnormal rise in seawater level during a storm, measured as the height of the water above the normal predicted astronomical tide. Source: National Ocean Service");

                    }
                    else if (resultText.getText().equals("what is signal number two")) {
                        ans.setText("A tropical cyclone will affect an area. Winds of greater than 60 kph and up to 100 kph may be expected in at least 24 hours. Cancellation or suspension of classes at the pre-school, elementary, and secondary level in the affected area is required. Source: Official Gazette");

                    }

                    else if (resultText.getText().equals("what is signal number 2")) {
                        ans.setText("A tropical cyclone will affect an area. Winds of greater than 60 kph and up to 100 kph may be expected in at least 24 hours. Cancellation or suspension of classes at the pre-school, elementary, and secondary level in the affected area is required. Source: Official Gazette");

                    }
                    else if (resultText.getText().equals("signal number 2")) {
                        ans.setText("A tropical cyclone will affect an area. Winds of greater than 60 kph and up to 100 kph may be expected in at least 24 hours. Cancellation or suspension of classes at the pre-school, elementary, and secondary level in the affected area is required. Source: Official Gazette");

                    }
                    else if (resultText.getText().equals("signal number two")) {
                        ans.setText("A tropical cyclone will affect an area. Winds of greater than 60 kph and up to 100 kph may be expected in at least 24 hours. Cancellation or suspension of classes at the pre-school, elementary, and secondary level in the affected area is required. Source: Official Gazette");

                    }
                    else if (resultText.getText().equals("what is signal number 1")) {
                        ans.setText("A tropical cyclone will threaten or affect an area. Winds of 30-60 kph is expected. Cancellation or suspension of classes at the pre-school level in the affected area is required. Source: Official Gazette");

                    }
                    else if (resultText.getText().equals("what is signal number one")) {
                        ans.setText("A tropical cyclone will threate or affect an area. Winds of 30-60 kph is expected. Cancellation or suspension of classes at the pre-school level in the affected area is required. Source: Official Gazette");

                    }
                    else if (resultText.getText().equals("signal number one")) {
                        ans.setText("A tropical cyclone will threate or affect an area. Winds of 30-60 kph is expected. Cancellation or suspension of classes at the pre-school level in the affected area is required. Source: Official Gazette");

                    }
                    else if (resultText.getText().equals("signal number 1")) {
                        ans.setText("A tropical cyclone will threate or affect an area. Winds of 30-60 kph is expected. Cancellation or suspension of classes at the pre-school level in the affected area is required. Source: Official Gazette");

                    }
                    else if (resultText.getText().equals("super typhoon")) {
                        ans.setText("SUPER TYPHOON, a tropical cyclone with maximum wind speed exceeding 220 kph or more than 120 knots. Source: Philippine Atmospheric, Geophysical and Astronomical Services Administration");

                    }
                    else if (resultText.getText().equals("what is super typhoon")) {
                        ans.setText("SUPER TYPHOON, a tropical cyclone with maximum wind speed exceeding 220 kph or more than 120 knots. Source: Philippine Atmospheric, Geophysical and Astronomical Services Administration");

                    }


                    else {
                        ans.setText("Sorry?");
                    }

                }
                catch(Exception e){
                    Toast.makeText(NewsAsk.this, e.toString(),
                            Toast.LENGTH_LONG).show();

                }
                finally {
                    tts.speak(ans.getText().toString(), TextToSpeech.QUEUE_FLUSH,null);
                }
            }
                break;
        }

    }
    public void onInit(int status){
        try {
            if (status == TextToSpeech.SUCCESS) {
                Locale bahasa = tts.getLanguage();
                int result = tts.setLanguage(bahasa);


            }
        }
        catch(Exception e){

            Toast.makeText(NewsAsk.this, e.toString(),
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
