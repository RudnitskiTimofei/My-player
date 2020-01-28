package by.it.trudnitski.myplayer.helper;

import android.net.Uri;
import android.provider.BaseColumns;

public final class ContractClass {
    public static final String AUTHORITY = "by.it.trudnitski.myplayer.helper.provider";

    private ContractClass() {
    }

    public static final class Songs implements BaseColumns {

        private Songs() {
        }

        private static final String SCHEME = "content://";
        private static final String PATH_SONGS = "/songs";
        private static final String PATH_SONGS_ID = "/songs/";
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_SONGS);
        public static final String _ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_GENRE = "genre";
        static final String TABLE_NAME = "songs";
        static final int SONGS_ID_PATH_POSITION = 1;
        static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_SONGS_ID);
        static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.";
        static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.";
        static final String DEFAULT_SORT_ORDER = "name ASC";
        static final String COLUMN_NAME_AUDIO_ID = "audio_id";
        static final String[] DEFAULT_PROJECTION = new String[]{
                ContractClass.Songs._ID,
                ContractClass.Songs.COLUMN_NAME_NAME,
                ContractClass.Songs.COLUMN_NAME_TITLE,
                ContractClass.Songs.COLUMN_NAME_GENRE,
                ContractClass.Songs.COLUMN_NAME_AUDIO_ID
        };
    }
}
