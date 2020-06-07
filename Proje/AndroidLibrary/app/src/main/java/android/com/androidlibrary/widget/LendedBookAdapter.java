package android.com.androidlibrary.widget;

import android.annotation.SuppressLint;
import android.com.androidlibrary.R;
import android.com.androidlibrary.db.DbContract;
import android.com.androidlibrary.model.Book;
import android.com.androidlibrary.model.Lend;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LendedBookAdapter extends ArrayAdapter<Lend> {

    List<Lend> mDataList;
    LayoutInflater inflater;
    Context context;
    private View rowView;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", new Locale("tr"));

    public LendedBookAdapter(Context context, List<Lend> data) {
        super(context, R.layout.lended_booked_item);
        //inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
        this.context = context;

    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Lend getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        rowView = convertView;
        Holder viewHolder;

        try {

            if (rowView == null) {
                rowView = inflater.inflate(R.layout.lended_booked_item, parent, false);
                viewHolder = new Holder();
                viewHolder.setName(rowView.findViewById(R.id.name));
                viewHolder.setAuthor(rowView.findViewById(R.id.author));
                viewHolder.setDate(rowView.findViewById(R.id.date));

                rowView.setTag(viewHolder);

            } else {
                viewHolder = (Holder) rowView.getTag();

            }

            Lend lend = mDataList.get(position);

            List<Book> book = DbContract.BookEntry.query(context, null, "oid = " + lend.getBookId());
            viewHolder.getName().setText(book.get(0).getName());
            viewHolder.getAuthor().setText(book.get(0).getAuthor());
            viewHolder.getDate().setText(dateFormat.format(lend.getLendDate()));
            lend.setBook(book.get(0));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rowView;

    }

    public void removeItem(Lend lend){
        mDataList.remove(lend);
        notifyDataSetChanged();
    }

    class Holder {

        TextView name;
        TextView author;
        TextView date;

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getAuthor() {
            return author;
        }

        public void setAuthor(TextView author) {
            this.author = author;
        }

        public TextView getDate() {
            return date;
        }

        public void setDate(TextView date) {
            this.date = date;
        }
    }
}
