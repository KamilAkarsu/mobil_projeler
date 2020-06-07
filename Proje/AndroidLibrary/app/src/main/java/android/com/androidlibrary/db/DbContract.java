package android.com.androidlibrary.db;

import android.com.androidlibrary.model.Book;
import android.com.androidlibrary.model.Lend;
import android.com.androidlibrary.model.User;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DbContract {

    public static final String CONTENT_AUTHORITY = "android.com.androidlibrary";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_USER = "users";
    public static final String PATH_BOOK = "books";
    public static final String PATH_LEND = "lends";


    public static final class UserEntry {

        private static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME = "users";

        public static final String COLUMN_OID = "oid";
        public static final String COLUMN_SCHOOL_NO = "school_no";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] COLUMNS = {
                COLUMN_OID,
                COLUMN_SCHOOL_NO,
                COLUMN_NAME,
                COLUMN_SURNAME,
                COLUMN_USER_EMAIL,
                COLUMN_PASSWORD,
        };

        public static void insert(Context context, User user) {
            ContentValues userData = new ContentValues();
            userData.put(COLUMN_SCHOOL_NO, user.getSchoolNo());
            userData.put(COLUMN_NAME, user.getName());
            userData.put(COLUMN_SURNAME, user.getSurname());
            userData.put(COLUMN_USER_EMAIL, user.getEmail());
            userData.put(COLUMN_PASSWORD, user.getPassword());

            Objects.requireNonNull((context)).getContentResolver().insert(
                    CONTENT_URI,
                    userData
            );
        }

        public static List<User> query(Context context, String[] projection, String selection) {

            Cursor cursor = Objects.requireNonNull((context)).getContentResolver().query(
                    CONTENT_URI,
                    projection,
                    selection,
                    new String[]{"*"},
                    null
            );

            if (cursor == null || cursor.getCount() == 0) {
                return new ArrayList<>();
            }

            List<User> userList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setId(cursor.getInt(0));
                    user.setSchoolNo(cursor.getInt(1));
                    user.setName(cursor.getString(2));
                    user.setSurname(cursor.getString(3));
                    user.setPassword(cursor.getString(4));
                    user.setEmail(cursor.getString(5));
                    userList.add(user);
                }
                while (cursor.moveToNext());
            }

            cursor.close();

            return userList;
        }


    }

    public static final class BookEntry {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOOK).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOK;

        public static final String TABLE_NAME = "books";

        public static final String COLUMN_OID = "oid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_STOCK = "stock";
        public static final String COLUMN_LIB_STOCK = "library_stock";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] COLUMNS = {
                COLUMN_OID,
                COLUMN_NAME,
                COLUMN_AUTHOR,
                COLUMN_STOCK,
                COLUMN_LIB_STOCK
        };

        public static void insert(Context context, Book book) {
            ContentValues bookData = new ContentValues();
            bookData.put(COLUMN_NAME, book.getName());
            bookData.put(COLUMN_AUTHOR, book.getAuthor());
            bookData.put(COLUMN_STOCK, book.getStock());
            bookData.put(COLUMN_LIB_STOCK, book.getTotalStock());


            Objects.requireNonNull((context)).getContentResolver().insert(
                    CONTENT_URI,
                    bookData
            );
        }

        public static void update(Context context, Book book) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_STOCK, book.getStock());

            Objects.requireNonNull(context).getContentResolver().update(
                    CONTENT_URI,
                    contentValues,
                    "oid = " + book.getId(),
                    null
            );

        }

        public static List<Book> query(Context context, String[] projection, String selection) {

            Cursor cursor = Objects.requireNonNull((context)).getContentResolver().query(
                    CONTENT_URI,
                    projection,
                    selection,
                    new String[]{"*"},
                    null
            );

            if (cursor == null || cursor.getCount() == 0) {
                return new ArrayList<>();
            }

            List<Book> bookList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                int i = 0;
                do {
                    Book book = new Book();
                    book.setId(cursor.getInt(0));
                    book.setName(cursor.getString(1));
                    book.setAuthor(cursor.getString(2));
                    book.setTotalStock(cursor.getInt(3));
                    book.setStock(cursor.getInt(4));
                    bookList.add(book);
                    i++;
                }
                while (cursor.moveToNext());
            }

            cursor.close();

            return bookList;
        }


    }

    public static final class LendEntry {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LEND).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LEND;

        public static final String TABLE_NAME = "lends";

        public static final String COLUMN_OID = "oid";
        public static final String COLUMN_SCHOOL_NO = "user";
        public static final String COLUMN_BOOK_NO = "book";
        public static final String COLUMN_LEND_DATE = "lend_date";
        public static final String COLUMN_GIVE_BACK_DATE = "give_back_date";
        public static final String COLUMN_DELIVERY_DATE = "delivery_date";


        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] COLUMNS = {
                COLUMN_OID,
                COLUMN_SCHOOL_NO,
                COLUMN_BOOK_NO,
                COLUMN_LEND_DATE,
                COLUMN_GIVE_BACK_DATE,
                COLUMN_DELIVERY_DATE,
        };

        public static void insert(Context context, Lend lend) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", new Locale("tr"));

            ContentValues lendData = new ContentValues();
            lendData.put(COLUMN_SCHOOL_NO, lend.getSchoolNo());
            lendData.put(COLUMN_BOOK_NO, lend.getBookId());
            lendData.put(COLUMN_LEND_DATE, dateFormat.format(lend.getLendDate()));
            lendData.put(COLUMN_GIVE_BACK_DATE, dateFormat.format(lend.getGiveBackDate()));

            Objects.requireNonNull((context)).getContentResolver().insert(
                    CONTENT_URI,
                    lendData
            );
        }

        public static List<Lend> query(Context context, String[] projection, String selection) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", new Locale("tr"));

            Cursor cursor = Objects.requireNonNull((context)).getContentResolver().query(
                    CONTENT_URI,
                    projection,
                    selection,
                    new String[]{"*"},
                    null
            );

            if (cursor == null || cursor.getCount() == 0) {
                return new ArrayList<>();
            }

            List<Lend> lendArrayList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    try {
                        Lend lend = new Lend();
                        lend.setId(cursor.getInt(0));
                        lend.setSchoolNo(cursor.getInt(1));
                        lend.setBookId(cursor.getInt(2));
                        lend.setLendDate(dateFormat.parse(cursor.getString(3)));
                        lend.setGiveBackDate(dateFormat.parse(cursor.getString(4)));
                        lendArrayList.add(lend);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                while (cursor.moveToNext());
            }

            cursor.close();

            return lendArrayList;
        }

        public static void update(Context context, Lend lend) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_DELIVERY_DATE, Calendar.getInstance().getTime().toString());

            Objects.requireNonNull(context).getContentResolver().update(
                    CONTENT_URI,
                    contentValues,
                    "user = " + lend.getSchoolNo() + " AND book = " + lend.getBookId(),
                    null
            );

        }


    }

}
