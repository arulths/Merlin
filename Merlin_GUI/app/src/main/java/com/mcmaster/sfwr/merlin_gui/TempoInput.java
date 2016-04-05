package com.mcmaster.sfwr.merlin_gui;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.Toast;
import android.view.View.OnClickListener;


/**
 * Created by Stephan on 2016-04-01.
 */
public class TempoInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempo_gui);
        Button tempo_button = (Button) findViewById(R.id.tempo_tapped);
        tempo_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Stuff happens when the screen is tapped
                Toast.makeText(TempoInput.this, "Beat Added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate actionbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Stuff happens when you select options in the actionbar
        switch(item.getItemId()){
            case R.id.home:
                //Return to home page with any data that has been entered
                Intent returnToHome = new Intent(TempoInput.this, HomePage.class);
                startActivity(returnToHome);
                return true;
            case R.id.skip:
                //Data does not need to be passed here
                Intent skipToLyric = new Intent(TempoInput.this, LyricInput.class);
                startActivity(skipToLyric);
                return true;
            case R.id.done:
                //Put code in here that passes data to the main SearchController
                Intent finishToLyric = new Intent(TempoInput.this, LyricInput.class);
                startActivity(finishToLyric);
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}



