package android.com.androidlibrary.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static DatabaseHelper mOpenHelper;

    static final int USER = 100;
    static final int BOOKS = 101;
    static final int LENDS = 102;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DbContract.PATH_USER, USER);
        matcher.addURI(authority, DbContract.PATH_BOOK, BOOKS);
        matcher.addURI(authority, DbContract.PATH_LEND, LENDS);
        return matcher;
    }

    @Override
    public boolean onCreate() {

        mOpenHelper = DatabaseHelper.getInstance(getContext());
        return true;
    }


    public Cursor rawQuery(@NonNull Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case USER: {
                cursor = mOpenHelper.getReadableDatabase().rawQuery(
                        selection,
                        selectionArgs
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("URI not known: " + uri);
        }
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case USER: {
                cursor = getCursor(selection, selectionArgs, sortOrder, DbContract.UserEntry.TABLE_NAME);
                break;
            }
            case BOOKS: {
                cursor = getCursor(selection, selectionArgs, sortOrder, DbContract.BookEntry.TABLE_NAME);
                break;
            }
            case LENDS: {
                cursor = getCursor(selection, selectionArgs, sortOrder, DbContract.LendEntry.TABLE_NAME);
                break;
            }
            default:
                throw new UnsupportedOperationException("URI not known: " + uri);
        }
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    private Cursor getCursor(String selection, String[] selectionArgs, String sortOrder, String tableName) {
        Cursor cursor;
        String sql = "SELECT ";
        for (String fields : selectionArgs) {
            sql += fields + ",";
        }
        sql = sql.substring(0, sql.length() - 1);

        sql += " FROM " + tableName;

        if (!selection.isEmpty()) {
            sql += " WHERE " + selection;

        }
        if (sortOrder != null && !sortOrder.isEmpty()) {
            sql += " ORDER BY " + sortOrder;
        }
        cursor = mOpenHelper.getReadableDatabase().rawQuery(
                sql,
                null
        );
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case USER:
                return DbContract.UserEntry.CONTENT_TYPE;
            case BOOKS:
                return DbContract.BookEntry.CONTENT_TYPE;
            case LENDS:
                return DbContract.LendEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Uri Not Known: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case USER: {
                long id = db.insert(DbContract.UserEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = DbContract.UserEntry.buildUserUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row : " + uri);
                }
                break;
            }
            case BOOKS: {
                long id = db.insert(DbContract.BookEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = DbContract.BookEntry.buildUserUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row : " + uri);
                }
                break;
            }
            case LENDS: {
                long id = db.insert(DbContract.LendEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = DbContract.LendEntry.buildUserUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row : " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Uri Not Known: " + uri);
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) {
            selection = "1";
        }

        switch (match) {
            case USER:
                rowsDeleted = db.delete(DbContract.UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case BOOKS:
                rowsDeleted = db.delete(DbContract.BookEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case LENDS:
                rowsDeleted = db.delete(DbContract.LendEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Uri Not Known: " + uri);
        }

        if (rowsDeleted != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case USER:
                rowsUpdated = db.update(DbContract.UserEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case BOOKS:
                rowsUpdated = db.update(DbContract.BookEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case LENDS:
                rowsUpdated = db.update(DbContract.LendEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Uri Not Knwon: " + uri);
        }
        if (rowsUpdated != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}