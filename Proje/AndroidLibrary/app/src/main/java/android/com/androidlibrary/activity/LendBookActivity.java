package android.com.androidlibrary.activity;

import android.com.androidlibrary.R;
import android.com.androidlibrary.db.DbContract;
import android.com.androidlibrary.model.Book;
import android.com.androidlibrary.model.Lend;
import android.com.androidlibrary.model.User;
import android.com.androidlibrary.widget.BookSpinnerAdapter;
import android.com.androidlibrary.widget.UserSpinnerAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LendBookActivity extends AppCompatActivity {
    Spinner bookSpinner, userSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lend_book);
        bookSpinner = findViewById(R.id.book);
        userSpinner = findViewById(R.id.user);

        List<Book> bookList = DbContract.BookEntry.query(getApplicationContext(), null, "stock != 0");
        BookSpinnerAdapter bookAdapter = new BookSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, bookList);
        bookSpinner.setAdapter(bookAdapter);

        List<User> userList = DbContract.UserEntry.query(getApplicationContext(), null, "1=1");
        UserSpinnerAdapter userAdapter = new UserSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, userList);
        userSpinner.setAdapter(userAdapter);

    }

    public void lendBook(View view) {
        if (userSpinner.getSelectedItem() == null || bookSpinner.getSelectedItem() == null) {
            Snackbar.make(view, "Alanlar dolu olmalıdır.", Snackbar.LENGTH_LONG).show();
            return;
        }

        User user = (User) userSpinner.getSelectedItem();
        Book book = (Book) bookSpinner.getSelectedItem();

        List<Lend> lendList = DbContract.LendEntry.query(getApplicationContext(),null,"user = " + user.getSchoolNo() + " AND book = " + book.getId() +" AND delivery_date is null");
        if(lendList != null && lendList.size() > 0){
            Snackbar.make(view, "Bu kitap bu öğrencide zaten var.", Snackbar.LENGTH_LONG).show();
            return;
        }

        Lend lend = new Lend();
        lend.setBookId(book.getId());
        lend.setSchoolNo(user.getSchoolNo());
        Calendar calendar = Calendar.getInstance();
        lend.setLendDate(calendar.getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, 2);
        lend.setGiveBackDate(calendar.getTime());

        DbContract.LendEntry.insert(getApplicationContext(), lend);
        int stock = book.getStock() - 1;
        book.setStock(stock);
        DbContract.BookEntry.update(getApplicationContext(), book);
        Snackbar.make(view, "Kayıt yapıldı.", Snackbar.LENGTH_LONG).show();
        List<Book> bookList = DbContract.BookEntry.query(getApplicationContext(), null, "stock != 0");
        BookSpinnerAdapter bookAdapter = new BookSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, bookList);
        bookSpinner.setAdapter(bookAdapter);


    }
}
