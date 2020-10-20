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

public class ClubActivity extends AppCompatActivity {
    Button searchForBook;
    TextView bookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        String club_name = getIntent().getExtras().getString("CLUB_NAME");
        getSupportActionBar().setTitle(club_name);

        searchForBook = findViewById(R.id.searchBookBtn);
        bookName = findViewById(R.id.bookNameClub);
        searchForBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchActivity();
            }
        });

        // Receive data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String Genre = intent.getExtras().getString("Description");

        Log.i("BOOK ","Received book " + Title);
        Book book = new Book(Title, Genre, Description);

        if(Title != null){
            bookName.setText("Current Book "+ Title);
            searchForBook.setText("Reselect a book");
            // TODO: Add book to data base under club
        }
    };

    public void openSearchActivity(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}