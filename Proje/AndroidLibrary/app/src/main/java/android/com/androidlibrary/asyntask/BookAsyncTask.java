package android.com.androidlibrary.asyntask;

import android.com.androidlibrary.model.Book;
import android.com.androidlibrary.util.NetworkUtil;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookAsyncTask extends AsyncTask<String, Void,String> {

    private List<Book> bookList = new ArrayList<>();
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtil.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String title = null;
            String authors = null;
            while (i < itemsArray.length() && (authors == null && title == null)) {
                JSONObject jsonBook = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = jsonBook.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                    authors = authors.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\"", "");
                    Book book = new Book();
                    book.setAuthor(authors);
                    book.setName(title);
                    bookList.add(book);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }
            System.out.println(bookList.size());

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
