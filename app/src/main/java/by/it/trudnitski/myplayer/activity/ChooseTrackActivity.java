package by.it.trudnitski.myplayer.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.it.trudnitski.myplayer.R;
import by.it.trudnitski.myplayer.helper.ContractClass;
import by.it.trudnitski.myplayer.helper.Song;
import by.it.trudnitski.myplayer.helper.SongAdapter;

public class ChooseTrackActivity extends AppCompatActivity {

    List<Song> data;
    List<Song> temp;
    RecyclerView recyclerView;
    Spinner nameSpinner;
    Spinner genreSpinner;
    Cursor mCursor;
    LinearLayoutManager manager = new LinearLayoutManager(this);
    String[] projection = {
            ContractClass.Songs._ID,
            ContractClass.Songs.COLUMN_NAME_NAME,
            ContractClass.Songs.COLUMN_NAME_TITLE,
            ContractClass.Songs.COLUMN_NAME_GENRE
    };
    private final static String LOG_MYLOG = "MY LOG";
    private final static String SONG_ADD = "Song add";
    private final static String CHOOSE_ARTIST = "Выберите исполнителя";
    private final static String CHOOSE_GENRE = "Выберите жанр";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_track);
        nameSpinner = findViewById(R.id.spinner_name);
        genreSpinner = findViewById(R.id.spinner_genre);
        recyclerView = findViewById(R.id.list);

        nameSpinner.setPrompt(CHOOSE_ARTIST);
        genreSpinner.setPrompt(CHOOSE_GENRE);
        recyclerView.setLayoutManager(manager);
        initializeData();
        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_name = nameSpinner.getSelectedItem().toString();
                temp = changeList(select_name, (ArrayList<Song>) data);
                recyclerView.setAdapter(new SongAdapter(temp));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_genre = genreSpinner.getSelectedItem().toString();
                temp = changeList(select_genre, (ArrayList<Song>) data);
                recyclerView.setAdapter(new SongAdapter(temp));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void initializeData() {
        String name;
        String title;
        String genre;
        data = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        mCursor = contentResolver.query(ContractClass.Songs.CONTENT_URI, projection,
                null, null, null, null);
        if (mCursor != null) {
            for (mCursor.moveToFirst(); !mCursor.isAfterLast(); ) {
                name = mCursor.getString(mCursor.getColumnIndex(ContractClass.Songs.COLUMN_NAME_NAME));
                Log.d(LOG_MYLOG, name);
                title = mCursor.getString(mCursor.getColumnIndex(ContractClass.Songs.COLUMN_NAME_TITLE));
                Log.d(LOG_MYLOG, title);
                genre = mCursor.getString(mCursor.getColumnIndex(ContractClass.Songs.COLUMN_NAME_GENRE));
                Log.d(LOG_MYLOG, genre);
                Song song = new Song(title, name, genre);
                data.add(song);
                mCursor.moveToNext();
                Log.d(LOG_MYLOG, SONG_ADD + "\n");
            }
        }
    }

    public ArrayList<Song> changeList(String select, ArrayList<Song> list) {
        ArrayList<Song> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getmGenre().equals(select) || list.get(i).getmName().equals(select)) {
                temp.add(list.get(i));
            }
        }
        return temp;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }
}
