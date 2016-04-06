package cas.se3a04.merlin.searching;

import android.net.Uri;
import cas.se3a04.merlin.input.TempoInput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ArtistLyricExpert {
    private static final String DB_SCHEME = "http";
    private static final String DB_HOST = "www.tristankelley.com";
    private static final String DB_PATH = "search-bf";
    private static final String DB_TABLE_ID = "thelist";

    private static ArtistLyricExpert instance = new ArtistLyricExpert();

    public static ArtistLyricExpert getInstance() {
        return instance;
    }

    private ArtistLyricExpert() { }

    private String buildGetURL(CharSequence artist, CharSequence lyrics) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(DB_SCHEME);
        uriBuilder.authority(DB_HOST);
        uriBuilder.appendPath(DB_PATH);

        uriBuilder.appendQueryParameter("mqa", artist == null ? "" : artist.toString());
        uriBuilder.appendQueryParameter("mqt", ""); //title
        uriBuilder.appendQueryParameter("mql", ""); //album
        uriBuilder.appendQueryParameter("mqy", lyrics == null ? "" : lyrics.toString());

        uriBuilder.appendQueryParameter("ob", "1"); //order "relevance"
        uriBuilder.appendQueryParameter("mm", "0"); //string matching spec "all words"

        return uriBuilder.build().toString();
    }

    public Callable<Set<Song>> findSongs(final CharSequence artist, final CharSequence lyrics) {
        if ((artist == null || artist.length() == 0) && (lyrics == null || lyrics.length() == 0))
            return new Callable<Set<Song>>() {
                @Override
                public Set<Song> call() throws Exception {
                    return new HashSet<>();
                }
            };
        else
            return new Callable<Set<Song>>() {
                @Override
                public Set<Song> call() throws Exception {
                    Set<Song> songs = new HashSet<>();

                    Document document = Jsoup.connect(buildGetURL(artist, lyrics)).timeout(0).get();

                    Element dataTable = document.getElementById(DB_TABLE_ID);
                    Elements dataRows = dataTable.getElementsByTag("tr");

                    System.out.println(dataRows.size());
                    Iterator<Element> it = dataRows.iterator();
                    if (!it.hasNext()) return songs;
                    it.next(); //Throw out the hearder row
                    while (it.hasNext()) {
                        Elements data = it.next().getElementsByTag("td");
                        songs.add(new Song(data.get(0).text(), data.get(1).text(), ((int) TempoInput.NO_TEMPO)));
                    }

                    return songs;
                }
            };
    }
}
