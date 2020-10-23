package com.example.searchbygenre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClubActivity extends AppCompatActivity  {
    Button searchForBook;
    TextView bookName;

    //Chat feature
    ChatAdapter chatAdapter;
    EditText etMessageText;
    ImageButton sendMessageBtn;
    RecyclerView rvChat;

    List<ChatMessage> chatMessageList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        // TODO: set this from DB
        String club_name = getIntent().getExtras().getString("CLUB_NAME");
        getSupportActionBar().setTitle(club_name + " club room");

        bookName = findViewById(R.id.bookNameClub);
        sendMessageBtn= findViewById(R.id.sendMessage);
        etMessageText = findViewById(R.id.etEnterMessage);
        rvChat = findViewById(R.id.recyclerViewMessage);

        searchForBook = findViewById(R.id.searchBookBtn);
        searchForBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearchActivity();
            }
        });

        // Receive data -- DISPLAY RECEIVED BOOK
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        String Genre = intent.getExtras().getString("Description");

        Log.i("BOOK ","Received book: " + Title);
        Book book = new Book(Title, Genre, Description);

        if(Title != null){
            bookName.setText("Current Book "+ Title);
            searchForBook.setText("Reselect a book");

            // TODO: Add book to data base under club(Save permanently)
        }

        // Set array in chat adapter
        chatAdapter = new ChatAdapter(getBaseContext(), chatMessageList);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(chatAdapter);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMessages();
            }
        });


        // TODO: add chat to database
    }

    private void displayMessages() {
        String textMessage = etMessageText.getText().toString();
        etMessageText.setText("");
        if(textMessage.isEmpty()){
            etMessageText.setError("  ");
            etMessageText.requestFocus();
            return;
        }
        ChatMessage chatMessage = new ChatMessage(textMessage, "CHISOM");
        // Add to Array
        chatMessageList.add(chatMessage);

        // Notify adapter that an item is inserted
        chatAdapter.notifyItemInserted(chatMessageList.size() -1);
    }
    ;

    public void openSearchActivity(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


}