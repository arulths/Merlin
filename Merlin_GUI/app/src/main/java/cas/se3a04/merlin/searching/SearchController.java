package cas.se3a04.merlin.searching;

import android.os.Bundle;
import android.util.Log;
import cas.se3a04.merlin.input.ArtistInput;
import cas.se3a04.merlin.input.LyricInput;
import cas.se3a04.merlin.input.TempoInput;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchController {
    public static final String INPUT_DATA_KEY = "seachInput";

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private TempoExpert tempoExpert = TempoExpert.getInstance();
    private ArtistLyricExpert artistLyricExpert = ArtistLyricExpert.getInstance();

    public Future<Set<Song>> search(Bundle searchParams) {
        final CharSequence artist = searchParams.getCharSequence(ArtistInput.RESULT_ARTIST_KEY);
        final CharSequence lyrics = searchParams.getCharSequence(LyricInput.RESULT_LYRIC_KEY);
        final double tempo = searchParams.getDouble(TempoInput.RESULT_TEMPO_KEY);

        Callable<Set<Song>> search = new Callable<Set<Song>>() {
            @Override
            public Set<Song> call() throws Exception {
                Set<Song> songs = new HashSet<>();
                if (tempo != TempoInput.NO_TEMPO) {
                    Future<Set<Song>> tempoResults = executorService.submit(tempoExpert.findSongs((int) tempo));
                    songs.addAll(tempoResults.get());
                }
                if (!ArtistInput.NO_ARTIST.equals(artist) || !LyricInput.NO_LYRIC.equals(lyrics)) {
                    Future<Set<Song>> artistLyricResults = executorService.submit(artistLyricExpert.findSongs(artist, lyrics));
                    songs.addAll(artistLyricResults.get());
                }
                return songs;
            }
        };

        return executorService.submit(search);
    }

    /*public Song selectSong(Set<Song> songsWithMatchingTempo, Set<Song> songsWithMatchingLyrics, Set<Song> songsWithMatchingArtists){
        Set<Song> matchingSongs = songsWithMatchingTempo;
        matchingSongs.retainAll(songsWithMatchingLyrics);
        matchingSongs.retainAll(songsWithMatchingArtists);

        Iterator<Song> iter = matchingSongs.iterator();
        if (iter.hasNext()){
            return  iter.next();
        }
        return null;
    }*/
}
