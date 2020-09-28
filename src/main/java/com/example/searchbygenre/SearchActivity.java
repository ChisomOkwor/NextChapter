package com.example.searchbygenre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;

import static android.R.layout.simple_list_item_1;

public class SearchActivity extends AppCompatActivity {
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("Select A Book");
        myDb = new DatabaseHelper(this);

        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SearchActivity.this, simple_list_item_1,
                getResources().getStringArray(R.array.Genre));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Hashtable<String, String> my_dict = new Hashtable<String, String>();
        my_dict.put("Fiction", "fiction.json?");
        my_dict.put("Biographies", "biography.json?");
        my_dict.put("Mental Health", "mental_health.json?");
        my_dict.put("Romance", "romance.json?");
        my_dict.put("Children", "juvenile_fiction.json?");
        my_dict.put("Science", "science.json?");
        my_dict.put("History", "history.json?");
        my_dict.put("Select a Genre", "none");

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = mySpinner.getSelectedItem().toString();

                String apiTitle = my_dict.get(text);
                Log.i("Dropdown api Response", apiTitle);
                Log.i("Dropdown Response", text);

                String URL = "http://openlibrary.org/subjects/" + apiTitle;


                if (apiTitle == "none") {
                    URL = "http://openlibrary.org/subjects/horror.json?published_in=1500-1600";
                }
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray works = null;
                                List<Book> lstBook ;
                                try {
                                    works = response.getJSONArray("works");
                                    lstBook = new ArrayList<Book>();

                                    for (int i = 0; i < works.length(); ++i) {
                                        final JSONObject work = works.getJSONObject(i);
                                        String title = work.getString("title");
                                        //work.getInt("isbn")
                                        final String isbn = "0201558025";
                                        String bookURL = "http://openlibrary.org/api/books?bibkeys=ISBN:"+ isbn + "&format=json";

                                        JsonObjectRequest coverObjectRequest = new JsonObjectRequest(
                                                Request.Method.GET,
                                                bookURL,
                                                null,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Log.e("URL RESPONSE", String.valueOf(response));
                                                        String key = "isbn:" + isbn;
                                                        try {
                                                            JSONObject book = response.getJSONObject(key);
                                                            String thumbnail_url = work.getString("thumbnail_url");
                                                            String info_url = work.getString("info_url");
                                                            String preview_url = work.getString("preview_url");
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                },new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                Log.e("Rest Response", error.toString());
                                                }
                                            });


                                        lstBook.add(new Book(title, "Category", "Description","thumbnail_url" ));
                                    }
                                    //   Log.i("Book array Response", lstBook.toString());

                                    RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);

                                    ItemsAdapter myAdapter = new ItemsAdapter(getBaseContext(), lstBook);
                                    myrv.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
                                    myrv.setAdapter(myAdapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest Response", error.toString());
                            }
                        }
                );

                requestQueue.add(objectRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
}