package android.com.homeworkassignment4_3;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickImage(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.imageView_donut :
                intent = new Intent(this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView_froyo:
                intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView_ice_cream:
                intent = new Intent(this, ThirdActivity.class);
                startActivity(intent);
                break;
            default: break;
        }
    }
}
