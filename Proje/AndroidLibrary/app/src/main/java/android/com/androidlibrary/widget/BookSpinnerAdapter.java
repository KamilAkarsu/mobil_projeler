package android.com.androidlibrary.widget;

import android.com.androidlibrary.R;
import android.com.androidlibrary.model.Book;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;

public class BookSpinnerAdapter extends ArrayAdapter {

    private Context context;
    // Your custom values for the spinner (User)
    private List<Book> values;

    public BookSpinnerAdapter(@NonNull Context context, int resource, List<Book> values) {
        super(context, resource);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = View.inflate(context, R.layout.spinner_main, null);
        final TextView textView = view.findViewById(R.id.spinner_main_text);
        if (values.get(position) != null) {
            textView.setText(values.get(position).getName() + " - " + values.get(position).getAuthor());
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        View view = View.inflate(context, R.layout.spinner_main, null);
        TextView textView = view.findViewById(R.id.spinner_main_text);
        if (values == null || values.get(position) == null) {
            Snackbar.make(view, "Değer null.", Snackbar.LENGTH_LONG).show();
            return textView;
        }
        textView.setText(values.get(position).getName() + "-" + values.get(position).getAuthor());

        return textView;
    }
}
