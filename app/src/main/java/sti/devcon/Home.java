package sti.devcon;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

public class Home extends AppCompatActivity implements View.OnClickListener{
    Button prep;
    Button news;
    Button sk;
    TextView preptxt;
    DonutProgress dp;
    String per="";
    Button emer;
    int count;
    int ctr;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prep = (Button)findViewById(R.id.btnPrep);
        prep.setOnClickListener(this);
        news = (Button)findViewById(R.id.btnNews);
        news.setOnClickListener(this);
        sk= (Button)findViewById(R.id.btnSk);
        sk.setOnClickListener(this);

        emer = (Button) findViewById(R.id.btnEmer);
        emer.setOnClickListener(this);
        preptxt = (TextView)findViewById(R.id.preptxt);

        dp = (DonutProgress) findViewById(R.id.donut_Progress);
        try {
            db = openOrCreateDatabase("skDB", Context.MODE_PRIVATE, null);
            db.execSQL("Create table if not exists survtbl(fa varchar, fe varchar, bp varchar, fl varchar, bw varchar, matches varchar, whis varchar, tools varchar, food varchar , soap varchar);");
            Cursor c = db.rawQuery("Select * from survtbl", null);
            if (c.getCount() < 1) {
                db.execSQL("Insert into survtbl values('UNCHECKED','UNCHECKED','UNCHECKED','UNCHECKED','UNCHECKED','UNCHECKED','UNCHECKED','UNCHECKED','UNCHECKED','UNCHECKED');");
            }
            c = db.rawQuery("Select fa,fe,bp,fl,bw,matches,whis,tools,food,soap from survtbl", null);
            c.moveToFirst();
            if (c.getString(0).equals("CHECKED"))
                ctr++;
            if (c.getString(1).equals("CHECKED"))
                ctr++;
            if (c.getString(2).equals("CHECKED"))
                ctr++;
            if (c.getString(3).equals("CHECKED"))
                ctr++;
            if (c.getString(4).equals("CHECKED"))
                ctr++;
            if (c.getString(5).equals("CHECKED"))
                ctr++;
            if (c.getString(6).equals("CHECKED"))
                ctr++;
            if (c.getString(7).equals("CHECKED"))
                ctr++;
            if (c.getString(8).equals("CHECKED"))
                ctr++;
            if (c.getString(9).equals("CHECKED"))
                ctr++;

            if (ctr == 0)
                count = 0;
            else if (ctr == 1)
                count = 10;
            else if (ctr == 2)
                count = 20;
            else if (ctr == 3)
                count = 30;
            else if (ctr == 4)
                count = 40;
            else if (ctr == 5)
                count = 50;
            else if (ctr == 6)
                count = 60;
            else if (ctr == 7)
                count = 70;
            else if (ctr == 8)
                count = 80;
            else if (ctr == 9)
                count = 90;
            else if (ctr == 10)
                count = 100;

        }
        catch(Exception e){

            AlertDialog alertDialog = new AlertDialog.Builder(Home.this).create();
            alertDialog.setTitle("List of Voice Commands");
            alertDialog.setMessage(e.toString());
            alertDialog.show();
        }

        new progress().execute();

    }
     class progress extends AsyncTask<Void,Integer,Integer>{

        @Override
        protected void onPreExecute(){
            dp.setMax(100);

        }
         @Override
         protected  void onProgressUpdate(Integer... values){
             super.onProgressUpdate(values);
             dp.setProgress(values[0]);
             preptxt.setText("");
         }
         protected Integer doInBackground (Void... params){
            for(int i = 0; i <= count;i++){


                publishProgress(i);
                try{

                    Thread.sleep(50);
                }
                catch (Exception e){
                    Toast.makeText(Home.this, e.toString(),
                            Toast.LENGTH_LONG).show();

                }
            }
             return null;
         }
         protected void onPostExecute(Integer integer){

             super.onPostExecute(integer);
             try {


                 String res = "";
                 if (count ==0) {
                     preptxt.setText("You have a very bad preparation! \nPlease Open the Survival Kit for more info!");

                 } else if (count ==10 || count == 20) {
                     preptxt.setText("You have a low standard preparation! \nPlease Open the Survival Kit for more info!");

                 } else if (count == 40 || count == 30) {
                     preptxt.setText("You have some survival kit but it is not enough. \nOpen the Survival Kit for more info!");


                 } else if (count == 60 || count == 50) {
                     preptxt.setText("You have a decent preparation but it is not enough. \nYou can Open the Survival Kit for more info");


                 } else if (count == 80 || count == 70 ) {
                     preptxt.setText("You are almost ready for any disaster!");


                 } else if (count == 100 || count == 90) {
                     preptxt.setText("You have an excellent preparations for any type of disaster!");


                 }
             }
             catch (Exception e){
                 Toast.makeText(Home.this, e.toString(),
                         Toast.LENGTH_LONG).show();

             }
         }
    }


    public void onClick (View v){

        try {
            if (v == prep) {
                startActivity(new Intent(Home.this,
                        MainActivity.class));



            } else if (v == news) {
                startActivity(new Intent(Home.this,
                        News.class));

            }
            else if (v == sk) {
                startActivity(new Intent(Home.this,
                        Surv.class));
                finish();


            }
            if(v == emer){
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:911"));
                startActivity(callIntent);
            }

        }
        catch (Exception e){
            Toast.makeText(Home.this, e.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }
}
