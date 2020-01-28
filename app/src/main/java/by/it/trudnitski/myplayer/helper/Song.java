package by.it.trudnitski.myplayer.helper;

public class Song {
    private String mName;
    private String mTitle;
    private String mGenre;
//serializable
    public Song(String title, String name, String genre) {
        mTitle = title;
        mName = name;
        mGenre = genre;
    }

    public String getName() {
        return mName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getGenre() {
        return mGenre;
    }


    public void setmName(String name) {
        mName = name;
    }

    public void setmTitle(String title) {
        mTitle = title;
    }

    public void setmGenre(String genre) {
        mGenre = genre;
    }

    //equalse, hashcode, toString, убрать this
}
