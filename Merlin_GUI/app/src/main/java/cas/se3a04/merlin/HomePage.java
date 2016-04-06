package cas.se3a04.merlin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cas.se3a04.merlin.searching.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomePage extends AppCompatActivity {
    public static final String SEARCH_RESULTS_KEY = "songs";
    private List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_gui);

        ListView results = (ListView) findViewById(R.id.search_results);
        songs = this.getIntent().getParcelableArrayListExtra(SEARCH_RESULTS_KEY);
        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song = songs.get(i);
                Intent searchAddress = new  Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + song.getArtist()));
                startActivity(searchAddress);
            }
        });
        String[] songText = new String[songs.size()];
        for (int i = 0; i < songs.size(); i++) {
            songText[i] = songs.get(i).getName() + " by " + songs.get(i).getArtist();
        }
        results.setAdapter(new ArrayAdapter<>(this, R.layout.list_contents, songText));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate actionbar
        //getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LaunchScreen.class);
        startActivity(intent);
    }

    public void onDonePressed(View view) {
        onBackPressed();
    }
}
