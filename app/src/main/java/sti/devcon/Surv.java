package sti.devcon;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.LightingColorFilter;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Surv extends AppCompatActivity implements View.OnClickListener {

    Button survbck;
    SQLiteDatabase db;
    ImageButton btnfak;
    ImageButton btnRad;
    ImageButton btnFire;
    ImageButton btnBot;
    ImageButton btnFlash;

    ImageButton btnMatch;
    ImageButton btnWhis;
    ImageButton btnTool;
    ImageButton btnFood;
    ImageButton btnSoap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surv);
        btnfak = (ImageButton)findViewById(R.id.btnFAK);
        btnfak.setOnClickListener(this);
        btnRad = (ImageButton)findViewById(R.id.btnRadio);
        btnRad.setOnClickListener(this);
        btnFire = (ImageButton)findViewById(R.id.btnFire);
        btnFire.setOnClickListener(this);
        btnBot = (ImageButton)findViewById(R.id.btnBottle);
        btnBot.setOnClickListener(this);
        btnFlash = (ImageButton)findViewById(R.id.btnFlashlight);
        btnFlash.setOnClickListener(this);

        btnMatch = (ImageButton)findViewById(R.id.btnMatch);
        btnMatch.setOnClickListener(this);
        btnWhis = (ImageButton)findViewById(R.id.btnWhis);
        btnWhis.setOnClickListener(this);
        btnTool = (ImageButton)findViewById(R.id.btnTools);
        btnTool.setOnClickListener(this);
        btnFood = (ImageButton)findViewById(R.id.btnfood);
        btnFood.setOnClickListener(this);
        btnSoap = (ImageButton)findViewById(R.id.btnSoap);
        btnSoap.setOnClickListener(this);

            survbck = (Button) findViewById(R.id.survback);
            survbck.setOnClickListener(this);

        try {
            db = openOrCreateDatabase("skDB", Context.MODE_PRIVATE, null);
            Cursor c = db.rawQuery("Select fa,fe,bp,fl,bw,matches,whis,tools,food,soap from survtbl", null);
            c.moveToFirst();
            if (c.getString(0).equals("UNCHECKED"))
                btnfak.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnfak.clearColorFilter();
            }
            if (c.getString(1).equals("UNCHECKED"))
                btnFire.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnFire.clearColorFilter();
            }

            if (c.getString(2).equals("UNCHECKED"))
                btnRad.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnRad.clearColorFilter();
            }

            if (c.getString(3).equals("UNCHECKED"))
                btnFlash.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnFlash.clearColorFilter();
            }

            if (c.getString(4).equals("UNCHECKED"))
                btnBot.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnBot.clearColorFilter();
            }



            if (c.getString(5).equals("UNCHECKED"))
                btnMatch.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnMatch.clearColorFilter();
            }
            if (c.getString(6).equals("UNCHECKED"))
                btnWhis.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnWhis.clearColorFilter();
            }

            if (c.getString(7).equals("UNCHECKED"))
                btnTool.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnTool.clearColorFilter();
            }

            if (c.getString(8).equals("UNCHECKED"))
                btnFood.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnFood.clearColorFilter();
            }

            if (c.getString(9).equals("UNCHECKED"))
                btnSoap.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));
            else{
                btnSoap.clearColorFilter();
            }

        }
        catch(Exception e){
            Toast.makeText(Surv.this, e.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onClick(View v){
        if(v == survbck){
            startActivity(new Intent(Surv.this,
                    Home.class));
            finish();
        }
        if(v == btnBot){
            Cursor c = db.rawQuery("Select bw from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  bw='CHECKED'");
                btnBot.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set  bw='UNCHECKED'");
                btnBot.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }
        if(v == btnFlash){
            Cursor c = db.rawQuery("Select fl from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  fl='CHECKED'");
                btnFlash.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set  fl='UNCHECKED'");
                btnFlash.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }
        if(v == btnFire){
            Cursor c = db.rawQuery("Select fe from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  fe='CHECKED'");
                btnFire.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set  fe='UNCHECKED'");
                btnFire.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }
        if(v == btnRad){
            Cursor c = db.rawQuery("Select bp from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  bp='CHECKED'");
                btnRad.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set  bp='UNCHECKED'");
                btnRad.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }
        if(v == btnfak){
            Cursor c = db.rawQuery("Select fa from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  fa='CHECKED'");
                btnfak.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set  fa='UNCHECKED'");
                btnfak.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }

        if(v == btnMatch){
            Cursor c = db.rawQuery("Select matches from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  matches='CHECKED'");
                btnMatch.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set  matches='UNCHECKED'");
                btnMatch.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }

        if(v == btnWhis){
            Cursor c = db.rawQuery("Select whis from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  whis='CHECKED'");
                btnWhis.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set  whis='UNCHECKED'");
                btnWhis.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }

        if(v == btnTool){
            Cursor c = db.rawQuery("Select tools from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  tools='CHECKED'");
                btnTool.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set tools='UNCHECKED'");
                btnTool.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }
        if(v == btnFood){
            Cursor c = db.rawQuery("Select food from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  food='CHECKED'");
                btnFood.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set  food='UNCHECKED'");
                btnFood.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }
        if(v == btnSoap){
            Cursor c = db.rawQuery("Select soap from survtbl", null);
            c.moveToFirst();
            if(c.getString(0).equals("UNCHECKED")) {
                db.execSQL("UPDATE survtbl set  soap='CHECKED'");
                btnSoap.clearColorFilter();
            }
            else{
                db.execSQL("UPDATE survtbl set  soap='UNCHECKED'");
                btnSoap.setColorFilter(new LightingColorFilter(0xff808080, 0x000000));

            }
        }


    }
}
