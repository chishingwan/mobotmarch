package sti.devcon;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class News extends AppCompatActivity implements View.OnClickListener{
    Button news1;

    TextView stories;
    Button bck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        try {
            news1 = (Button) findViewById(R.id.news1);
            news1.setOnClickListener(this);

            bck = (Button) findViewById(R.id.btnBack);
            bck.setOnClickListener(this);
            stories = (TextView) findViewById(R.id.textView8);
            stories.setTextColor(Color.parseColor("#B7D6EA"));
        }
        catch (Exception e){
            Toast.makeText(News.this, e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        }
    public void onClick(View v){
        try {
            if (v == news1) {
                startActivity(new Intent(News.this,
                        NewsAsk.class));


            }

            if (v == bck) {

                //Intent i = new Intent(News.this, Home.class);

               // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(i);
                finish();
            }
        }
        catch (Exception e){
            Toast.makeText(News.this, e.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }
}
