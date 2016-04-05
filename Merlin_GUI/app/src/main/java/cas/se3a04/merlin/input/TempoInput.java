package cas.se3a04.merlin.input;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import cas.se3a04.merlin.HomePage;
import cas.se3a04.merlin.R;
import cas.se3a04.merlin.searching.SearchController;

public class TempoInput extends AppCompatActivity {
    public static final String RESULT_TEMPO_KEY = "tempo";
    public static final double NO_TEMPO = -1d;
    private static final double NANOSECONDS_PER_MINUTE = 6E+10;

    private long lastTap;
    private double tempo;
    private int numTaps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempo_gui);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate actionbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle data = getIntent().getBundleExtra(SearchController.INPUT_DATA_KEY);
        Intent targetActivity;
        //Stuff happens when you select options in the actionbar
        switch(item.getItemId()){
            case R.id.home:
                //Return to home page with any data that has been entered
                targetActivity = new Intent(TempoInput.this, HomePage.class);
                data.putDouble(RESULT_TEMPO_KEY, NO_TEMPO);
                break;
            case R.id.skip:
                //Data does not need to be passed here
                targetActivity = new Intent(TempoInput.this, LyricInput.class);
                data.putDouble(RESULT_TEMPO_KEY, NO_TEMPO);
                break;
            case R.id.done:
                //Put code in here that passes data to the main SearchController
                targetActivity = new Intent(TempoInput.this, LyricInput.class);
                //If the submit fails we want to remain in this activity so return
                if (!submit(data))
                    return true;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        //One of the appropriate menu items were pressed so startup the next activity
        targetActivity.putExtra(SearchController.INPUT_DATA_KEY, data);
        startActivity(targetActivity);
        return true;
    }

    public void onTempoTap(View tempoButton) {
        this.numTaps++;
        if (this.numTaps == 1) {
            //This is the first tap
            this.lastTap = System.nanoTime();
        } else {
            //Calculate the bpm of the last beat
            long currentTime = System.nanoTime();
            double bpm = NANOSECONDS_PER_MINUTE / (currentTime - this.lastTap);

            //Include it in the running average
            this.tempo -= tempo / this.numTaps;
            this.tempo += bpm / this.numTaps;

            //Save this time as the last tap for the next calculation
            this.lastTap = currentTime;
        }

        //Set the text in the tempo display
        ((Button) tempoButton).setText(numTaps < 2 ? getResources().getString(R.string.tempo_disp_default_text)
                : String.format("%3.2f BPM", this.tempo));
    }

    private boolean submit(Bundle data) {
        //Make sure there is actually data to return
        if (this.numTaps < 2) {
            Toast.makeText(this, "Please tap a tempo or skip", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Bundle the return data
        data.putDouble(RESULT_TEMPO_KEY, this.tempo);
        return true;
    }
}



