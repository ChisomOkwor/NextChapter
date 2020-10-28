package com.example.searchbygenre;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

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
    String userEmail;

    List<ChatMessage> chatMessageList =  new ArrayList<>();

    private DatabaseReference dbr;
    String user_msg_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        userEmail = getIntent().getExtras().getString("userEmail");
        // TODO: set this from DB
        String club_name = getIntent().getExtras().getString("CLUB_NAME");
        getSupportActionBar().setTitle(club_name + " Club Room");

        dbr = FirebaseDatabase.getInstance().getReference().child(club_name);

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

        loadPreviousMessages();

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
                appendMessage();
            }
        });

    }

    private void loadPreviousMessages() {
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                chatMessageList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    //  String clubId = postSnapshot.getKey();
                    // DatabaseReference clubIdRef = myRef.child("Clubs").child(clubId);
                    ChatMessage chatMessage = postSnapshot.getValue(ChatMessage.class);
                   // System.out.println(club.club_name);
                    chatMessageList.add(chatMessage);
                    // Log.i("FIREBASE DB", club.getClubName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });


    }

    private void appendMessage() {
        String textMessage = etMessageText.getText().toString();
        etMessageText.setText("");
        if(textMessage.isEmpty()){
            etMessageText.setError("  ");
            etMessageText.requestFocus();
            return;
        }
        ChatMessage chatMessage = new ChatMessage(textMessage, userEmail);
        // Add to Array
        chatMessageList.add(chatMessage);
        // Add to DB
        user_msg_key = dbr.push().getKey();
        dbr.child(user_msg_key).setValue(chatMessage);
        // Notify adapter that an item is inserted
        chatAdapter.notifyItemInserted(chatMessageList.size() -1);
    }
    ;

    public void openSearchActivity(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


}