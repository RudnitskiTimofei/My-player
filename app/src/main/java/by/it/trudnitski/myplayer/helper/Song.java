package by.it.trudnitski.myplayer.helper;

public class Song {
    private String mName;
    private String mTitle;
    private String mGenre;
   // private int mIdResourse;

    public Song(String title, String name, String genre) {
        mTitle = title;
        mName = name;
        mGenre = genre;
       // mIdResourse = IdResourse;
    }

    public String getmName() {
        return mName;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmGenre() {
        return mGenre;
    }

    //public int getmIdResourse() {
//        return mIdResourse;
//    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    //public void setmIdResourse(int mIdResourse) {
      //  this.mIdResourse = mIdResourse;
    //}
}