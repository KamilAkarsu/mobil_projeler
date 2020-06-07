package android.com.androidlibrary.widget;

import android.com.androidlibrary.R;
import android.com.androidlibrary.db.DbContract;
import android.com.androidlibrary.model.Book;
import android.com.androidlibrary.model.Lend;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewLend extends RecyclerView.Adapter<RecyclerViewLend.Holder> {

    List<Lend> mDataList;
    LayoutInflater inflater;
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", new Locale("tr"));

    public RecyclerViewLend(Context context, List<Lend> data) {

        //inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerViewLend.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.book_item, parent, false);

        // Return a new holder instance
        RecyclerViewLend.Holder viewHolder = new RecyclerViewLend.Holder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewLend.Holder holder, int position) {
        Lend lend = mDataList.get(position);
        List<Book> book = DbContract.BookEntry.query(context, null, "oid = " + lend.getBookId());
        TextView nameTextView = holder.name;
        nameTextView.setText(book.get(0).getName());

        TextView authorTextView = holder.author;
        authorTextView.setText(book.get(0).getAuthor());

        TextView stockTextView = holder.stock;
        stockTextView.setText(dateFormat.format(lend.getLendDate()));

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView name;
        TextView author;
        TextView stock;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            author = (TextView) itemView.findViewById(R.id.author);
            stock = (TextView) itemView.findViewById(R.id.stock);

        }

    }
}
