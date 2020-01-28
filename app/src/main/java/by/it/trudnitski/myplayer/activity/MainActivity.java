package by.it.trudnitski.myplayer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import by.it.trudnitski.myplayer.R;
import by.it.trudnitski.myplayer.helper.PlayerService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_MESSAGE_NAME = "name";
    private static final String EXTRA_MESSAGE_TITLE = "title";
    private static final String EXTRA_MESSAGE_GENRE = "genre";
    private static final String EXTRA_MESSAGE_PLAY = "play";
    private static final String BROADCAST_MESSAGE = "broadcast";
    private TextView title;
    private TextView name;
    private TextView genre;
    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonStop;
    private Button buttonChoose;
    private String nameIntent;
    private String titleIntent;
    private String genreIntent;
    private LocalBroadcastManager manager;
    private BroadcastReceiver broadcastReceiver;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                nameIntent = intent.getExtras().getString(EXTRA_MESSAGE_NAME);
                titleIntent = intent.getExtras().getString(EXTRA_MESSAGE_TITLE);
                genreIntent = intent.getExtras().getString(EXTRA_MESSAGE_GENRE);
                name.setText(nameIntent);
                title.setText(titleIntent);
                genre.setText(genreIntent);
                startService(new Intent(context, PlayerService.class).putExtra(EXTRA_MESSAGE_TITLE, titleIntent).putExtra(EXTRA_MESSAGE_PLAY, 11));
            }
        };
        manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver, new IntentFilter(BROADCAST_MESSAGE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonPlay) {
            startService(new Intent(this, PlayerService.class).putExtra(EXTRA_MESSAGE_PLAY, 25));
        } else if (v == buttonPause) {
            startService(new Intent(this, PlayerService.class).putExtra(EXTRA_MESSAGE_PLAY, 15));
        } else if (v == buttonStop) {
            startService(new Intent(this, PlayerService.class).putExtra(EXTRA_MESSAGE_PLAY, 10));
        } else if (v == buttonChoose) {
            startActivity(new Intent(this, ChooseTrackActivity.class));
        }
    }
}

