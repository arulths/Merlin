package cas.se3a04.merlin.searching;

import java.util.Iterator;
import java.util.Set;
//TODO Dummy class until this is implemented
public class SearchController {
    public static final String INPUT_DATA_KEY = "seachInput";

    public Song selectSong(Set<Song> songsWithMatchingTempo,Set<Song> songsWithMatchingLyrics,Set<Song> songsWithMatchingArtists){
        Set<Song> matchingSongs = songsWithMatchingTempo;
        matchingSongs.retainAll(songsWithMatchingLyrics);
        matchingSongs.retainAll(songsWithMatchingArtists);

        Iterator<Song> iter = matchingSongs.iterator();
        if (iter.hasNext()){
            return  iter.next();
        }
        return null;
    }
}
