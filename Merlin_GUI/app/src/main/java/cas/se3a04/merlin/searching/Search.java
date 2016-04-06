package cas.se3a04.merlin.searching;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import cas.se3a04.merlin.HomePage;
import cas.se3a04.merlin.R;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Search extends AppCompatActivity {
    private Future<Set<Song>> results;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Bundle searchIn = getIntent().getBundleExtra(SearchController.INPUT_DATA_KEY);
        SearchController searchController = new SearchController();
        results = searchController.search(searchIn);

        final ProgressBar pBar = (ProgressBar) findViewById(R.id.progressBar1);
        pBar.setVisibility(ProgressBar.VISIBLE);

        Thread resultsPoll = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!results.isDone()) {
                    try {
                        Thread.sleep(50L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Search.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dispResults();
                    }
                });
            }
        }, "ResultsPoll");
        resultsPoll.start();
    }

    private void dispResults() {
        Intent intent = new Intent(this, HomePage.class);
        if (results.isDone()) {
            try {
                ArrayList<Song> songs = new ArrayList<>(this.results.get().size());
                songs.addAll(this.results.get());
                intent.putExtra(HomePage.SEARCH_RESULTS_KEY, songs);
            } catch (Exception e) {
                Log.e("Merlin", e.getLocalizedMessage());
                intent.putExtra(HomePage.SEARCH_RESULTS_KEY, new ArrayList<Song>());
            }
        } else {
            intent.putExtra(HomePage.SEARCH_RESULTS_KEY, new ArrayList<Song>());
        }
        startActivity(intent);
    }
}
