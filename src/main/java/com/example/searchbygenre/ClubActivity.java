package com.example.searchbygenre;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
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

public class ClubActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        myDb = new DatabaseHelper(this);

        String clubName = getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT);
        Log.i("ClubActivity", "ClubNAME " + clubName);
        getSupportActionBar().setTitle(clubName);

        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        final ListView listView = (ListView) findViewById(R.id.bookList);
        final TextView textView = (TextView) findViewById(R.id.textView);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ClubActivity.this, simple_list_item_1,
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
                } else {
                    textView.setText("Results");
                }
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray works = null;
                                List<String> mylist = null;
                                try {
                                    works = response.getJSONArray("works");
                                    mylist = new ArrayList<String>();

                                    for (int i = 0; i < works.length(); ++i) {
                                        JSONObject work = works.getJSONObject(i);
                                        String title = work.getString("title");
                                        mylist.add(title);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                        (ClubActivity.this, android.R.layout.simple_list_item_1, mylist);

                                // DataBind ListView with items from ArrayAdapter
                                listView.setAdapter(arrayAdapter);

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