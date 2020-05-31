package android.com.homeworkassignment4_1;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSubmit(View view) {
        String message = "Toppings: ";

        CheckBox chocolateCheckbox = findViewById(R.id.checkbox_chocolate);
        if (chocolateCheckbox.isChecked()) {
            message += chocolateCheckbox.getText() + " ";
        }

        CheckBox sprinklesCheckbox = findViewById(R.id.checkbox_sprinkles);
        if (sprinklesCheckbox.isChecked()) {
            message += sprinklesCheckbox.getText() + " ";
        }

        CheckBox crushedNutCheckbox = findViewById(R.id.checkbox_crushed_nuts);
        if (crushedNutCheckbox.isChecked()) {
            message += crushedNutCheckbox.getText() + " ";
        }

        CheckBox cherriesCheckbox = findViewById(R.id.checkbox_cherries);
        if (cherriesCheckbox.isChecked()) {
            message += cherriesCheckbox.getText() + " ";
        }

        CheckBox orioCookieCheckbox = findViewById(R.id.checkbox_orio_cookie);
        if (orioCookieCheckbox.isChecked()) {
            message += orioCookieCheckbox.getText() + " ";
        }

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


    }
}
