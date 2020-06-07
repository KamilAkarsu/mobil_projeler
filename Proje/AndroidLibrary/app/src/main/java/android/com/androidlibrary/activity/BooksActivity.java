package android.com.androidlibrary.activity;


import android.com.androidlibrary.R;
import android.com.androidlibrary.db.DbContract;
import android.com.androidlibrary.model.Book;
import android.com.androidlibrary.widget.RecyclerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BooksActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);

        List<Book> bookList = DbContract.BookEntry.query(getApplicationContext(), null, "1=1");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerAdapter adapter = new RecyclerAdapter(getApplicationContext(), bookList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = null;
//        if (connectivityManager != null){
//            networkInfo = connectivityManager.getActiveNetworkInfo();
//        }
//
//        if (networkInfo != null && networkInfo.isConnected()){
//            new BookAsyncTask().execute("othello");
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.android_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void addNewBook(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kitap Ekle");

        View inflate = getLayoutInflater().inflate(R.layout.add_book, null);
        builder.setView(inflate);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText bookName = (EditText) inflate.findViewById(R.id.book_name);
                EditText bookAuthor = (EditText) inflate.findViewById(R.id.book_author);
                EditText stock = (EditText) inflate.findViewById(R.id.stock);

                if (bookName.getText().toString().isEmpty() ||
                        bookAuthor.getText().toString().isEmpty() ||
                        stock.getText().toString().isEmpty()) {

                    Snackbar.make(view, "Lütfen alanları doldurunuz.", Snackbar.LENGTH_LONG).show();
                } else {
                    Book book = new Book();
                    book.setName(bookName.getText().toString());
                    book.setAuthor(bookAuthor.getText().toString());
                    book.setStock(Integer.parseInt(stock.getText().toString()));
                    book.setTotalStock(book.getStock());

                    DbContract.BookEntry.insert(getApplicationContext(), book);
                    ((RecyclerAdapter) recyclerView.getAdapter()).addData(book);
                    dialog.dismiss();
                    Snackbar.make(view, "Kitap kaydedildi.", Snackbar.LENGTH_LONG).show();

                }


            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.add_user:
                Intent userIntent = new Intent(this, UserActivity.class);
                startActivity(userIntent);

                break;

            case R.id.lend_book:
                Intent lendIntent = new Intent(this, LendBookActivity.class);
                startActivity(lendIntent);

                break;

            case R.id.take_book:
                Intent takeIntent = new Intent(this, TakeBookActivity.class);
                startActivity(takeIntent);

                break;

            case R.id.out:
                Intent outIntent = new Intent(this, MainActivity.class);
                startActivity(outIntent);
                break;

        }


        return super.onOptionsItemSelected(item);
    }
}
