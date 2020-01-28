package by.it.trudnitski.myplayer.helper;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import by.it.trudnitski.myplayer.R;

public class PlayerService extends Service {
    private final static String KEY_ON_START = "play";
    private final static String EXEPTION_MESSAGE = "Unexpected value: ";
    private final static int PLAY_MESSAGE = 25;
    private final static int PAUSE_MESSAGE = 15;
    private final static int STOP_MESSAGE = 10;
    private final static int START_MESSAGE = 11;
    AudioManager audioManager;
    MediaPlayer mediaPlayer;


    @Override
    public void onCreate() {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.tam_de_nas_nema);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int play = intent.getExtras().getInt(KEY_ON_START);
        int res=0;
        switch (play) {
            case START_MESSAGE:
                mediaPlayer.stop();
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(ContractClass.Songs.CONTENT_URI,
                        new String[]{ContractClass.Songs.COLUMN_NAME_AUDIO_ID},
                        "title = ?", new String[]{intent.getExtras()
                                .getString("title")}, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    res = cursor.getInt(cursor.getColumnIndexOrThrow(ContractClass.Songs.COLUMN_NAME_AUDIO_ID));
                }
                mediaPlayer = MediaPlayer.create(this, res);
                break;
            case PLAY_MESSAGE:
                mediaPlayer.start();
                break;
            case PAUSE_MESSAGE:
                mediaPlayer.pause();
                break;
            case STOP_MESSAGE:
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                break;
            default:
                throw new IllegalStateException(EXEPTION_MESSAGE + play);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
