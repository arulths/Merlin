package cas.se3a04.merlin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

public class TempoInput extends ActionBarActivity {
    public static final String RESULT_TEMPO_KEY = "tempo";
    private static final double NANOSECONDS_PER_MINUTE = 6E+10;

    private long lastTap;
    private double tempo;
    private int numTaps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tempo_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Called when the user taps the TempoInButton
    public void onTap(View tapButton) {
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
        TextView tempoDisp = (TextView) this.findViewById(R.id.TempoDisp);
        tempoDisp.setText(numTaps < 2 ? getResources().getString(R.string.tempoDispDefaultText)
                : String.format("Tempo: %3.2f BPM", this.tempo));
    }

    public void onSubmit(View submitButton) {
        //Make sure there is actually data to return
        if (this.numTaps < 2) {
            Toast.makeText(this, "Please tap the \"Tap Me\" button", Toast.LENGTH_SHORT).show();
            return;
        }

        TempoExpert tempoExpert = TempoExpert.getInstance();
        FutureTask<Set<Song>> songs = tempoExpert.findSongs((int) this.tempo);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(songs);
        try {
            Iterator<Song> song = songs.get().iterator();
            int count = 0;
            while (count < 10 && song.hasNext()) {
                count++;
                System.out.println(song.next());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Bundle the return data
        Bundle tempoData = new Bundle();
        tempoData.putDouble(RESULT_TEMPO_KEY, this.tempo);

        //Put the data in the return intent
        Intent intent = new Intent();
        intent.putExtras(tempoData);

        //Set the result and return to the caller
        setResult(RESULT_OK, intent);
        finish();
    }
}
