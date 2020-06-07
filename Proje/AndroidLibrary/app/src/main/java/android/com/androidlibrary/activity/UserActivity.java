package android.com.androidlibrary.activity;

import android.com.androidlibrary.R;
import android.com.androidlibrary.db.DbContract;
import android.com.androidlibrary.model.User;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);
    }

    public void addNewUser(View view) {
        EditText name = findViewById(R.id.user_name);
        EditText surname = findViewById(R.id.user_surname);
        EditText no = findViewById(R.id.user_no);
        EditText email = findViewById(R.id.user_email);

        if (name.getText().toString().isEmpty() ||
                surname.getText().toString().isEmpty() ||
                no.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty()) {

            Snackbar.make(view, "Tüm alanları doldurunuz.", Snackbar.LENGTH_LONG).show();

        } else if (!validEmail(email.getText().toString())) {
            Snackbar.make(view, "Mail formatı yanlıştır.", Snackbar.LENGTH_LONG).show();

        } else if (!controlUserExistence(email.getText().toString(), no.getText().toString())) {
            Snackbar.make(view, "Mail ya da öğrenci numarası zaten var.", Snackbar.LENGTH_LONG).show();

        } else {
            User user = new User();
            user.setName(name.getText().toString());
            user.setSurname(surname.getText().toString());
            user.setEmail(email.getText().toString());
            user.setSchoolNo(Integer.parseInt(no.getText().toString()));
            user.setPassword(no.getText().toString());

            DbContract.UserEntry.insert(getApplicationContext(), user);

            Snackbar.make(view, "Kayıt başarılı.", Snackbar.LENGTH_LONG).show();

        }
    }

    private boolean controlUserExistence(String mail, String password) {
        List<User> userList = DbContract.UserEntry.query(getApplicationContext(), null, "1=1");
        for (User user : userList) {
            if (user.getEmail().equals(mail.trim())) {
                return false;
            }
            if (user.getSchoolNo() == Integer.valueOf(password.trim())) {
                return false;
            }
        }
        return true;
    }

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
