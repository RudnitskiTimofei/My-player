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
    String select;
    ChooseTrackActivity activity;
    LinearLayoutManager manager = new LinearLayoutManager(this);
    String[] projection = {
            ContractClass.Songs._ID,
            ContractClass.Songs.COLUMN_NAME_NAME,
            ContractClass.Songs.COLUMN_NAME_TITLE,
            ContractClass.Songs.COLUMN_NAME_GENRE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_track);
        nameSpinner = findViewById(R.id.spinner_name);
        nameSpinner.setPrompt("Выберите исполнителя");
        genreSpinner = findViewById(R.id.spinner_genre);
        genreSpinner.setPrompt("Выберите жанр");
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(manager);
        ContentResolver contentResolver = getContentResolver();
        mCursor = contentResolver.query(ContractClass.Songs.CONTENT_URI, projection,
                null, null, null, null);
        initializeData();

        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_name = nameSpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "You chose " + select, Toast.LENGTH_SHORT).show();
                temp = changeList(select_name, (ArrayList<Song>) data);
                recyclerView.setAdapter(new SongAdapter( temp ));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_genre = genreSpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), " You choose " + select, Toast.LENGTH_SHORT).show();
                temp = changeList(select_genre, (ArrayList<Song>) data);
                recyclerView.setAdapter(new SongAdapter( temp ));
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
        if (mCursor != null) {
            for (mCursor.moveToFirst(); !mCursor.isAfterLast(); ) {
                name = mCursor.getString(mCursor.getColumnIndex(ContractClass.Songs.COLUMN_NAME_NAME));
                Log.d("ARRAYLIST", name);
                title = mCursor.getString(mCursor.getColumnIndex(ContractClass.Songs.COLUMN_NAME_TITLE));
                Log.d("ARRAYLIST", title);
                genre = mCursor.getString(mCursor.getColumnIndex(ContractClass.Songs.COLUMN_NAME_GENRE));
                Log.d("ARRAYLIST", genre);
                Song song = new Song(title, name, genre);
                data.add(song);
                mCursor.moveToNext();
                Log.d("ARRAYLIST", "song added\n");
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