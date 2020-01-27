package by.it.trudnitski.myplayer.helper;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import by.it.trudnitski.myplayer.R;

public class PlayerService extends Service {
    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    int res;


    @Override
    public void onCreate() {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.tam_de_nas_nema);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int play = intent.getExtras().getInt("play");
        switch (play) {
            case 11:
                mediaPlayer.stop();
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(ContractClass.Songs.CONTENT_URI, new String[]{"audio_id"}, "title = ?", new String[]{intent.getExtras().getString("title")}, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    res = cursor.getInt(cursor.getColumnIndexOrThrow("audio_id"));
                }
                mediaPlayer = MediaPlayer.create(this, res);
                break;
            case 25:
                mediaPlayer.start();
                break;
            case 15:
                mediaPlayer.pause();
                break;
            case 10:
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
