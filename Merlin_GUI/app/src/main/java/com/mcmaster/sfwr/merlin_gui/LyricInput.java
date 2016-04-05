package com.mcmaster.sfwr.merlin_gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Stephan on 2016-04-03.
 */
public class LyricInput extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyric_gui);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.home:
                //Return to home page with any data that has been entered
                Intent returnToHome = new Intent(LyricInput.this, HomePage.class);
                startActivity(returnToHome);
                return true;
            case R.id.skip:
                //Data does not need to be passed here
                Intent skipToArtist= new Intent(LyricInput.this, ArtistInput.class);
                startActivity(skipToArtist);
                return true;
            case R.id.done:
                //Put code in here that passes data to the main SearchController
                Intent finishToArtist = new Intent(LyricInput.this,ArtistInput.class);
                startActivity(finishToArtist);
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
