package com.example.searchbygenre;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import static android.R.layout.simple_list_item_1;

public class ClubActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    Button searchbooks;
    TextView bookName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        String clubName = getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT);

        getSupportActionBar().setTitle(clubName);

        searchbooks = findViewById(R.id.searchBookBtn);
        searchbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchActivity();
            }
        });

        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String Genre = intent.getExtras().getString("Description");
        String Image = intent.getExtras().getString("Thumbnail") ;
        Book book = new Book(Title, Genre, Description, Image);
    };

    public void openSearchActivity(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}