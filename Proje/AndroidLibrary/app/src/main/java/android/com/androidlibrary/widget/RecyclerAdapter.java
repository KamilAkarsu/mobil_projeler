package android.com.androidlibrary.widget;

import android.com.androidlibrary.R;
import android.com.androidlibrary.model.Book;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    List<Book> mDataList;
    LayoutInflater inflater;

    public RecyclerAdapter(Context context, List<Book> data) {

        //inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(context);
        this.mDataList = data;

    }

    @NonNull
    @Override
    public RecyclerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.book_item, parent, false);

        // Return a new holder instance
        Holder viewHolder = new Holder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.Holder holder, int position) {
        Book book = mDataList.get(position);
        TextView nameTextView = holder.name;
        nameTextView.setText(book.getName());

        TextView authorTextView = holder.author;
        authorTextView.setText(book.getAuthor());

        TextView stockTextView = holder.stock;
        stockTextView.setText("Stok = " + String.valueOf(book.getStock()));


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void addData(Book book) {
        mDataList.add(book);
        notifyDataSetChanged();
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
