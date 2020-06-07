package android.com.androidlibrary.activity;

import android.com.androidlibrary.R;
import android.com.androidlibrary.db.DbContract;
import android.com.androidlibrary.model.Lend;
import android.com.androidlibrary.widget.LendedBookAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TakeBookActivity extends AppCompatActivity {

    private ListView listView;
    private Lend selectedLend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_book);
        listView = findViewById(R.id.listview);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            for (int j = 0; j < adapterView.getChildCount(); j++)
                adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

            // change the background color of the selected element
            view.setBackgroundColor(Color.DKGRAY);
            selectedLend = (Lend) listView.getItemAtPosition(i);
        });

    }

    public void findUserBookList(View view) {
        EditText userNo = findViewById(R.id.user_no);
        if (userNo.getText().toString().isEmpty()) {
            Snackbar.make(view, "Öğrenci Numarası Giriniz.", Snackbar.LENGTH_LONG).show();
            return;
        }
        List<Lend> lendList = DbContract.LendEntry.query(getApplicationContext(), null, "user =" + userNo.getText().toString() + " AND  delivery_date is null");
        LendedBookAdapter adapter = new LendedBookAdapter(getApplicationContext(), lendList);
        listView.setAdapter(adapter);
        findViewById(R.id.back_btn).setVisibility(View.VISIBLE);

    }

    public void takeBackBook(View view) {
        DbContract.LendEntry.update(getApplicationContext(), selectedLend);
        Snackbar.make(view, "İşlem tamamlandı.", Snackbar.LENGTH_LONG).show();
        LendedBookAdapter adapter = (LendedBookAdapter) listView.getAdapter();
        selectedLend.getBook().setStock(selectedLend.getBook().getStock() + 1);
        DbContract.BookEntry.update(getApplicationContext(), selectedLend.getBook());
        adapter.removeItem(selectedLend);

    }
}
