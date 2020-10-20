package com.example.searchbygenre;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    List<Club> items =  new ArrayList<>();
    Button joinClubBtn;
    Button createBtn;
    EditText etClubName;
    RecyclerView rvItems;
    ClubsAdapter clubsAdapter;

    DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Next Chapter: Clubs");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Clubs");
        user = mAuth.getCurrentUser();

        userID = user.getUid();
        joinClubBtn = findViewById(R.id.joinClubBtn);
        createBtn = findViewById(R.id.createBtn);
        etClubName = findViewById(R.id.etClubName);
        rvItems = findViewById(R.id.rvItems);

        loadClubList();
        getUser();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               // FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.i("LOGIN STAT", "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(getApplicationContext(), "Item updated!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Successfully signed in with: " + user.getEmail(), Toast.LENGTH_SHORT ).show();
                } else {
                    // User is signed out
                    Log.i("LOGIN STAT", "onAuthStateChanged:signed_out");
                    Toast.makeText(getApplicationContext(), "Successfully signed out", Toast.LENGTH_SHORT ).show();
                }
            }
        };

        // Button to Join a new Club by Club ID
        joinClubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openJoinClubActivity();
            }
        });

        ClubsAdapter.OnLongClickListener onLongClickListener = new ClubsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                items.remove(position);
                // Notify the adapter
                clubsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item removed ", Toast.LENGTH_SHORT).show();
                // DELETE FROM FIREBASE DB HERE, MAYBE BY ID;
            }
        };

        ClubsAdapter.OnClickListener onClickListener = new ClubsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {

                openSlectedClub(position);

            }
        };

        clubsAdapter = new ClubsAdapter(items, onLongClickListener, onClickListener);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setAdapter(clubsAdapter);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerClub();
            }
        });
    }

    private void openSlectedClub(int position) {
        Log.d("MainActivity", "Single click at position " + position);
        // Create the new Activity
        Intent i = new Intent(MainActivity.this, ClubActivity.class);
        // Pass the data being edited

        Club club = items.get(position);
        // Pass the Club name
        i.putExtra("CLUB_NAME", club.getClubName());
        i.putExtra(KEY_ITEM_POSITION, position);
        //Start the Club Activity
        startActivityForResult(i, EDIT_TEXT_CODE);
    }

    // Push Club Info to DB
    private void registerClub(){
        String club_name = etClubName.getText().toString();
        if(club_name.isEmpty()){
            etClubName.setError("A Club name is Required");
            etClubName.requestFocus();
            return;
        }
        // Change edit text item to empty

        Club club = new Club(club_name);
        etClubName.setText("");
        // Add to Array
        items.add(club);

        // Notify adapter that an item is inserted
        clubsAdapter.notifyItemInserted(items.size() -1);

        // Add to Firebase DB
        String key = myRef.push().getKey();
        myRef.child(key).setValue(club);
    }

    public void loadClubList(){
        //DatabaseReference myRef = database.getReference("Clubs");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                  //  String clubId = postSnapshot.getKey();
                   // DatabaseReference clubIdRef = myRef.child("Clubs").child(clubId);
                    Club club = postSnapshot.getValue(Club.class);
                    System.out.println(club.club_name);
                    items.add(club);
                   // Log.i("FIREBASE DB", club.getClubName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });
    }

    public void getUser(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again.
                // whenever data at this location is updated.
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User uInfo = new User();
                    Log.i("GET USER INFO", "onDataChange: " + uInfo);

                    //display all the information
                    Log.d("user info", "showData: name: " + uInfo.getName());
                    Log.d("user info", "showData: email: " + uInfo.getEmail());

                    ArrayList<String> array  = new ArrayList<>();
                    array.add(uInfo.getName());
                    array.add(uInfo.getEmail());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void openJoinClubActivity(){
        Intent intent = new Intent(this, JoinClub.class);
        startActivity(intent);
    }
}
