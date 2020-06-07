package android.com.androidlibrary.activity;

import android.Manifest;
import android.com.androidlibrary.R;
import android.com.androidlibrary.db.DbContract;
import android.com.androidlibrary.model.User;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {


    private final String[] reqPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE};

    private final int ALL_PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
    }

    private void checkPermissions() {
        // If an error is found, handle the failure to start.
        // Check permissions to see if failure may be due to lack of permissions.

        boolean permissionReadFilePermission = ContextCompat.checkSelfPermission(MainActivity.this, reqPermissions[0]) == PackageManager.PERMISSION_GRANTED;
        boolean permissionWriteFilePermission = ContextCompat.checkSelfPermission(MainActivity.this, reqPermissions[1]) == PackageManager.PERMISSION_GRANTED;
        boolean permissionInternetPermission = ContextCompat.checkSelfPermission(MainActivity.this, reqPermissions[2]) == PackageManager.PERMISSION_GRANTED;
        boolean permissionInternetStatePermission = ContextCompat.checkSelfPermission(MainActivity.this, reqPermissions[3]) == PackageManager.PERMISSION_GRANTED;


        if (!permissionReadFilePermission || !permissionWriteFilePermission || !permissionInternetPermission || !permissionInternetStatePermission) {
            ActivityCompat.requestPermissions(MainActivity.this, reqPermissions, ALL_PERMISSIONS_REQUEST_CODE);
        } else {
            Button loginButton = findViewById(R.id.btn_login);
            EditText email = findViewById(R.id.input_email);
            EditText password = findViewById(R.id.input_password);
            loginButton.setOnClickListener(view -> {
                if (email.getText().toString().equals("admin@cbu.edu.tr") && password.getText().toString().equals("admin")) {
                    Intent intent = new Intent(this, BooksActivity.class);
                    startActivity(intent);

                } else {
                    String mail = email.getText().toString();
                    String pass = password.getText().toString();
                    String selection = "email = '" + mail.trim() + "' AND password ='" + pass.trim() + "'";
                    List<User> userList = DbContract.UserEntry.query(getApplicationContext(), null, selection);
                    if (userList != null && userList.size() != 0) {
                        Intent intent = new Intent(this, UserBookActivity.class);
                        Gson gson = new Gson();
                        intent.putExtra("USER", gson.toJson(userList.get(0)));
                        startActivity(intent);

                    } else {
                        Snackbar.make(view, "Mail ya da şifre hatalı.", Snackbar.LENGTH_LONG).show();
                    }

                }

            });
        }
    }
}
