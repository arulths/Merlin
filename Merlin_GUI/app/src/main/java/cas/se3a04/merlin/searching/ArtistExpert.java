package cas.se3a04.merlin.searching;

import android.net.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ArtistExpert {
    private static final String BPM_DB_SCHEME = "http";
    private static final String BPM_DB_HOST = "www.tristankelley.com";
    private static final String BPM_DB_PATH = "bpm-database";
    private static final String BPM_DB_TABLE_ID = "bpm";

    private static ArtistExpert instance = new ArtistExpert();

    public static ArtistExpert getInstance() {
        return instance;
    }

    private ArtistExpert() { }

    private String buildGetURL(String artist) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(BPM_DB_SCHEME);
        uriBuilder.authority(BPM_DB_HOST);
        uriBuilder.appendPath(BPM_DB_PATH);

        uriBuilder.appendQueryParameter("bpmstart", "");
        uriBuilder.appendQueryParameter("bpmend", "");
        uriBuilder.appendQueryParameter("artist", artist);
        uriBuilder.appendQueryParameter("title", "");
        uriBuilder.appendQueryParameter("key", "");
        uriBuilder.appendQueryParameter("mode", "");
        uriBuilder.appendQueryParameter("musicstyle", "");
        uriBuilder.appendQueryParameter("mood", "");

        return uriBuilder.build().toString();
    }

    public FutureTask<Set<Song>> findSongs(final String artist) {
        return new FutureTask<>(new Callable<Set<Song>>() {
            @Override
            public Set<Song> call() throws Exception {
                Set<Song> songs = new HashSet<>();

                Document document = Jsoup.connect(buildGetURL(artist)).timeout(0).get();

                Element dataTable = document.getElementById(BPM_DB_TABLE_ID);
                Elements threadBody = dataTable.getElementsByTag("tbody");
                Elements dataRows = threadBody.get(0).getElementsByTag("tr");

                for (Element row : dataRows) {
                    Elements data = row.getElementsByTag("td");
                    songs.add(new Song(data.get(0).text(), data.get(1).text(), (int) Double.parseDouble(data.get(2).text())));
                }

                return songs;
            }
        });
    }
}
