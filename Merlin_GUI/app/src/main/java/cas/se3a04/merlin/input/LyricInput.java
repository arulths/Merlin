package cas.se3a04.merlin.input;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import cas.se3a04.merlin.HomePage;
import cas.se3a04.merlin.LaunchScreen;
import cas.se3a04.merlin.R;
import cas.se3a04.merlin.searching.SearchController;

/**
 * Created by Stephan on 2016-04-03.
 */
public class LyricInput extends AppCompatActivity{
    public static final String RESULT_LYRIC_KEY = "lyric";
    public static final String NO_LYRIC = "";

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyric_gui);

        txtSpeechInput = (TextView) findViewById(R.id.lyric_userinput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        //getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });



    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle data = getIntent().getBundleExtra(SearchController.INPUT_DATA_KEY);
        if (data == null) data = new Bundle();
        Intent targetActivity;
        //Stuff happens when you select options in the actionbar
        switch(item.getItemId()){
            case R.id.home:
                //Return to home page with any data that has been entered
                targetActivity = new Intent(LyricInput.this, LaunchScreen.class);
                data.putCharSequence(RESULT_LYRIC_KEY, NO_LYRIC);
                break;
            case R.id.skip:
                //Data does not need to be passed here
                targetActivity = new Intent(LyricInput.this, ArtistInput.class);
                data.putCharSequence(RESULT_LYRIC_KEY, NO_LYRIC);
                break;
            case R.id.done:
                //Put code in here that passes data to the main SearchController
                targetActivity = new Intent(LyricInput.this, ArtistInput.class);
                CharSequence in = txtSpeechInput.getText();
                if (in == null) in = "";
                data.putCharSequence(RESULT_LYRIC_KEY, in);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        //One of the appropriate menu items were pressed so startup the next activity
        targetActivity.putExtra(SearchController.INPUT_DATA_KEY, data);
        startActivity(targetActivity);
        return true;
    }
}
