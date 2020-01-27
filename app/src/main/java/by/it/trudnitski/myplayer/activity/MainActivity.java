package by.it.trudnitski.myplayer.activity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import by.it.trudnitski.myplayer.R;
import by.it.trudnitski.myplayer.helper.ContractClass;
import by.it.trudnitski.myplayer.helper.DataBaseHelper;
import by.it.trudnitski.myplayer.helper.PlayerService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView title;
    TextView name;
    TextView genre;
    Button buttonPlay;
    Button buttonPause;
    Button buttonStop;
    Button buttonChoose;
    String nameIntent;
    String titleIntent;
    String genreIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        genre = findViewById(R.id.genre);
        buttonPlay = findViewById(R.id.button_play);
        buttonPause = findViewById(R.id.button_pause);
        buttonStop = findViewById(R.id.button_stop);
        buttonChoose = findViewById(R.id.button_choose);

        buttonPlay.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonChoose.setOnClickListener(this);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                nameIntent = intent.getExtras().getString("name");
                titleIntent = intent.getExtras().getString("title");
                genreIntent = intent.getExtras().getString("genre");
                name.setText(nameIntent);
                title.setText(titleIntent);
                genre.setText(genreIntent);
                startService(new Intent(context, PlayerService.class).putExtra("title", titleIntent).putExtra("play", 11));
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("broadcast"));
    }

    @Override
    public void onClick(View v) {
        if (v == buttonPlay) {
            startService(new Intent(this, PlayerService.class).putExtra("play", 25));
        } else if (v == buttonPause) {
            startService(new Intent(this, PlayerService.class).putExtra("play", 15));
        } else if (v == buttonStop) {
            startService(new Intent(this, PlayerService.class).putExtra("play", 10));
        } else if (v == buttonChoose) {
            startActivity(new Intent(this, ChooseTrackActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

