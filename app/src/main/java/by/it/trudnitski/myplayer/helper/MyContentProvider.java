package by.it.trudnitski.myplayer.helper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher;
    private static HashMap<String, String> mSongsProjectionMap;
    private static final int SONGS = 1;
    private static final int SONGS_ID = 2;
    private static final String MY_LOG = "My log";

    DataBaseHelper dbHelper;
    SQLiteDatabase db;
    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(ContractClass.AUTHORITY, "songs", SONGS);
        mUriMatcher.addURI(ContractClass.AUTHORITY, "songs/#", SONGS_ID);
        mSongsProjectionMap = new HashMap<>();
        for (int i = 0; i < ContractClass.Songs.DEFAULT_PROJECTION.length; i++) {
            mSongsProjectionMap.put(
                    ContractClass.Songs.DEFAULT_PROJECTION[i],
                    ContractClass.Songs.DEFAULT_PROJECTION[i]);
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d("my log", "query");
        switch (mUriMatcher.match(uri)) {
            case SONGS:
                Log.d("my log", "SONGS");
                if (TextUtils.isEmpty(sortOrder)){
                    sortOrder = ContractClass.Songs.DEFAULT_SORT_ORDER;
                }
                break;
            case SONGS_ID:
                Log.d("my log", "SONGS_ID");
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    selection = ContractClass.Songs._ID + " = " + id;
                }
                break;
            default:
                Log.d(MY_LOG, "Wrong uri");
                Toast.makeText(getContext(), "Wrong uri", Toast.LENGTH_SHORT).show();
                //Log
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(ContractClass.Songs.TABLE_NAME, projection,
                selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), ContractClass.Songs.CONTENT_URI);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case SONGS:
                return ContractClass.Songs.CONTENT_TYPE;
            case SONGS_ID:
                return ContractClass.Songs.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Wrong Uri" + uri);
                //Log
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (mUriMatcher.match(uri) != SONGS) {
            throw new IllegalArgumentException("Wrong Uri:" + uri);
            //Log
        }
        db = dbHelper.getWritableDatabase();
        if (values != null) {
            values = new ContentValues(values);
        } else {
            values = new ContentValues();
        }
        long rowId;
        Uri rowUri = Uri.EMPTY;
        rowId = db.insert(ContractClass.Songs.TABLE_NAME, ContractClass.Songs.COLUMN_NAME_NAME, values);
        if (rowId > 0) {
            rowUri = ContentUris.withAppendedId(ContractClass.Songs.CONTENT_ID_URI_BASE, rowId);
            getContext().getContentResolver().notifyChange(rowUri, null);
        }
        return rowUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        String finalWhere;
        int count;
        switch (mUriMatcher.match(uri)) {
            case SONGS:
                count = db.delete(ContractClass.Songs.TABLE_NAME, selection, selectionArgs);
                break;
            case SONGS_ID:
                finalWhere = ContractClass.Songs._ID + " = " + uri.getPathSegments()
                        .get(ContractClass.Songs.SONGS_ID_PATH_POSITION);
                if (selection != null) {
                    finalWhere = finalWhere + " AND " + selection;
                }
                count = db.delete(ContractClass.Songs.TABLE_NAME, finalWhere, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("WRONG Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int count;
        String finalWhere;
        String id;

        switch (mUriMatcher.match(uri)) {
            case SONGS:
                count = db.update(ContractClass.Songs.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SONGS_ID:
                id = uri.getPathSegments().get(ContractClass.Songs.SONGS_ID_PATH_POSITION);
                finalWhere = ContractClass.Songs._ID + " = " + id;
                if (selection != null) {
                    finalWhere = finalWhere + " AND " + selection;
                }
                count = db.update(ContractClass.Songs.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("WRONG Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
