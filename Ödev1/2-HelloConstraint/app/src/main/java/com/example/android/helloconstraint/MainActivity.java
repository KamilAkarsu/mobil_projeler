/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.helloconstraint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Displays two buttons and a text view.
 * - Pressing the TOAST button shows a toast.
 * - Pressing the COUNT button increases the displayed mCount.
 * <p>
 * Note: Fixing behavior when device is rotated is a challenge exercise for the student.
 */

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;
    private Button zeroButton, countButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);

        zeroButton = (Button) findViewById(R.id.zero_button);
        countButton = (Button) findViewById(R.id.button_count);

        //        CEVAP 1 :
//        hizamalama da zero button toast buttonun altında, counter buttonun yukarasında olmasını söylüyoruz
//        app:layout_constraintBottom_toTopOf="@+id/count_button"
//        app:layout_constraintTop_toBottomOf="@+id/toast_button"
//        ayrıca tüm butonlara da aynı marginleri tanımlıyoruz.
//        android:layout_marginBottom="10dp"
//        android:layout_marginStart="16dp"
//        android:layout_marginTop="10dp"


//        CEVAP 2 :
//        yukarıdakilerine ek olarak soldan hizalamayı layout un (parent 'ın) kendisi olduğunu 3 butona da söylüyoruz.
//        app:layout_constraintLeft_toLeftOf="parent"
//        yukarıdaki ve bu kodlar dikey ve yatay sıralamayı tamamlıyor.

//        CEVAP 3 :
//        her iki yöntem de çalışır.


//        CEVAP 4 :
//        public void callMethod(View view)
//        aşağıda örnekleri mevcuttur.

//        CEVAP 5 :
//        parametre olarak geçilen view nesnesinden setBackgroundColor() metodunu çalıştırmak daha verimli.
//        çünkü zaten elimde view yani button var ve rengini değiştiriyorum
//        diğer durumda nesneyi bulmak için findViewById'yi çağırarak ekstra işlem daha yapıyorum yapıyorum
//

    }

    /**
     * Increments the number in the text view when the COUNT button is clicked.
     *
     * @param view The view that triggered this onClick handler.
     *             Since the count always appears in the text view, the passed in view is not used.
     */
    public void countUp(View view) {
        if (mCount == 0) { //ilk countera tıklanıca renkler değişsin sürekli buraya girmesin
            zeroButton.setBackgroundColor(getResources().getColor(R.color.red));
        }
        mCount++;
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));

        if (mCount % 2 == 0) {
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.lime));
        }
    }

    /**
     * Shows a toast when the TOAST button is clicked.
     *
     * @param view The view that triggered this onClick handler.
     *             Since a toast always shows on the top, the passed in view is not used.
     */
    public void showToast(View view) {
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, R.string.toast_button_toast, duration);
        toast.show();
    }

    public void setZeroTextView(View view) {
        // eğer counter sıfırdan farklıysa işlemleri yap
        // diğer durumda zaten aşağıdaki işlemler gerçekleştiği için her butona basıldığında işlem yapmaya gerek yoktur.
        if (mCount != 0) {
            mCount = 0;
            mShowCount.setText(String.valueOf(mCount));
            countButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            view.setBackgroundColor(getResources().getColor(R.color.light_grey));
        }
    }
}
