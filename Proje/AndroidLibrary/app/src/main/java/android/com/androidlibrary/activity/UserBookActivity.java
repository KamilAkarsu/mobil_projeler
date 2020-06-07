package android.com.androidlibrary.activity;

import android.com.androidlibrary.R;
import android.com.androidlibrary.db.DbContract;
import android.com.androidlibrary.model.Book;
import android.com.androidlibrary.model.Lend;
import android.com.androidlibrary.model.User;
import android.com.androidlibrary.widget.RecyclerAdapter;
import android.com.androidlibrary.widget.RecyclerViewLend;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserBookActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_book);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        String userString = getIntent().getStringExtra("USER");
        Gson gson = new Gson();
        user = gson.fromJson(userString, User.class);
        List<Lend> lendList = DbContract.LendEntry.query(getApplicationContext(), null, "user =" + this.user.getSchoolNo() + " AND  delivery_date is null");
        RecyclerViewLend adapter = new RecyclerViewLend(getApplicationContext(), lendList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(String.valueOf(user.getSchoolNo()));

        View inflate = getLayoutInflater().inflate(R.layout.user_info, null);
        builder.setView(inflate);

        EditText no = (EditText) inflate.findViewById(R.id.school_no);
        EditText name = (EditText) inflate.findViewById(R.id.name);
        EditText surname = (EditText) inflate.findViewById(R.id.surname);
        EditText mail = (EditText) inflate.findViewById(R.id.email);
        EditText pass = (EditText) inflate.findViewById(R.id.password);

        no.setText(String.valueOf(user.getSchoolNo()));
        name.setText(user.getName());
        surname.setText(user.getSurname());
        mail.setText(user.getEmail());
        pass.setText(user.getPassword());

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.info:
                show();
                break;
            case R.id.out:
                Intent outIntent = new Intent(this, MainActivity.class);
                startActivity(outIntent);
                break;

        }
        return super.onOptionsItemSelected(item);

    }


}
