package android.com.androidlibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "/sdcard/Android/data/library.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper sInstance;

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS  " + DbContract.UserEntry.TABLE_NAME
                    + " (" +
                    DbContract.UserEntry.COLUMN_OID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    DbContract.UserEntry.COLUMN_SCHOOL_NO + " INTEGER, " +
                    DbContract.UserEntry.COLUMN_NAME + " TEXT, " +
                    DbContract.UserEntry.COLUMN_SURNAME + " TEXT, " +
                    DbContract.UserEntry.COLUMN_PASSWORD + " TEXT, " +
                    DbContract.UserEntry.COLUMN_USER_EMAIL + " TEXT, " +
                    "UNIQUE (" + DbContract.UserEntry.COLUMN_SCHOOL_NO + ")" +
                    " );";

    private static final String CREATE_BOOKS_TABLE =
            "CREATE TABLE IF NOT EXISTS  " + DbContract.BookEntry.TABLE_NAME
                    + " (" +
                    DbContract.BookEntry.COLUMN_OID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    DbContract.BookEntry.COLUMN_NAME + " TEXT, " +
                    DbContract.BookEntry.COLUMN_AUTHOR + " TEXT, " +
                    DbContract.BookEntry.COLUMN_LIB_STOCK + " INTEGER, " +
                    DbContract.BookEntry.COLUMN_STOCK + " INTEGER " +
                    " );";

    private static final String CREATE_LENDS_TABLE =
            "CREATE TABLE IF NOT EXISTS  " + DbContract.LendEntry.TABLE_NAME
                    + " (" +
                    DbContract.LendEntry.COLUMN_OID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    DbContract.LendEntry.COLUMN_SCHOOL_NO + " INTEGER, " +
                    DbContract.LendEntry.COLUMN_BOOK_NO + " INTEGER, " +
                    DbContract.LendEntry.COLUMN_LEND_DATE + " DATE, " +
                    DbContract.LendEntry.COLUMN_GIVE_BACK_DATE + " DATE, " +
                    DbContract.LendEntry.COLUMN_DELIVERY_DATE + " DATE " +
                    " );";

    static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_BOOKS_TABLE);
        db.execSQL(CREATE_LENDS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
