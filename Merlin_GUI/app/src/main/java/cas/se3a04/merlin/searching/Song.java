package cas.se3a04.merlin.searching;

import android.os.Parcel;
import android.os.Parcelable;
import cas.se3a04.merlin.input.TempoInput;

public class Song implements Parcelable {
    private final String artist;
    private final String name;
    private final int tempo;

    public Song(String artist, String name, int tempo) {
        this.artist = artist;
        this.name = name;
        this.tempo = tempo;
    }

    protected Song(Parcel in) {
        artist = in.readString();
        name = in.readString();
        tempo = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

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
        int range = 50;
        if (tempo < song.tempo - range || tempo > song.tempo + range) return  false;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(artist);
        parcel.writeString(name);
        parcel.writeInt(tempo);
    }
}
