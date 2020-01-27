package by.it.trudnitski.myplayer.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import by.it.trudnitski.myplayer.R;

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    private static final int DB_VERSION = 1;

    public DataBaseHelper(Context context){
        super(context, "PLAYER", null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table songs( " +
                "id INTEGER primary key autoincrement, " +
                "name TEXT, " +
                "title TEXT," +
                "genre TEXT," +
                "audio_id INTEGER)");
        insertSongs(db, "Океан Эльзы", "Long time ago", "Rock", R.raw.lont_time_ago );
        insertSongs(db, "Мираж", "На неби був", "Pop", R.raw.na_neby_buv );
        insertSongs(db, "Океан Эльзы", "Ото була весна", "Rock", R.raw.oto_bula_vesna );
        insertSongs(db, "Руки вверз", "Поезд", "Pop", R.raw.poizd );
        insertSongs(db, "Океан Эльзы", "Там дэ нас нема", "Rock", R.raw.tam_de_nas_nema );
        insertSongs(db, "Руки вверх", "Той дэнь", "Rock", R.raw.toi_den );
        insertSongs(db, "Океан Эльзы", "Видпусты", "Pop", R.raw.vidpusty );
        insertSongs(db, "Мираж", "Вона", "Rock", R.raw.vona );
        insertSongs(db, "Океан Эльзы", "Вставай", "Rock", R.raw.vstavai );
        Log.d("my log", "DB is create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Add some code for update DB
    }

    private void insertSongs(SQLiteDatabase db, String name, String title, String genre, int resourseId){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("title", title);
        values.put("genre", genre);
        values.put("audio_id", resourseId);
        db.insert("songs", null, values);
    }
}
