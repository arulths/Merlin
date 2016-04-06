package cas.se3a04.merlin.searching;

public class Song {
    private final String artist;
    private final String name;
    private final int tempo;

    public Song(String artist, String name, int tempo) {
        this.artist = artist;
        this.name = name;
        this.tempo = tempo;
    }

    public int getTempo() {
        return tempo;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "artist='" + artist + '\'' +
                ", name='" + name + '\'' +
                ", tempo=" + tempo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (tempo != song.tempo) return false;
        if (!artist.equals(song.artist)) return false;
        return name.equals(song.name);

    }

    @Override
    public int hashCode() {
        int result = artist.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + tempo;
        return result;
    }
}
