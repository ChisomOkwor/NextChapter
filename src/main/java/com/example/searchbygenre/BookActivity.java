package com.example.searchbygenre;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.parceler.Parcels;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BookActivity extends AppCompatActivity {
    TextView tvtitle,tvdescription,tvcategory;
    ImageView ivCover;
    Button selectBook;
    String Title;
    String Description;
    String Subject;
    String Thumbnail;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);
        ivCover = (ImageView) findViewById(R.id.bookthumbnail);
        selectBook = (Button)  findViewById(R.id.button);

        // Receive data
        Intent intent = getIntent();
        book = Parcels.unwrap(intent.getParcelableExtra("book"));
        Title = book.getTitle();
        Description = book.getDescription();
        Subject = book.getCategory();
        Thumbnail = book.getThumbnail();

        // Setting values
        tvtitle.setText(Title);
        tvdescription.setText(Description);
        tvcategory.setText(Subject);

        Glide.with(this).load(Thumbnail).into(ivCover);

        selectBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClubActivity(Title, Description);
            }
        });
    }

    public void openClubActivity(String title, String description){
        Intent intent = new Intent(this, ClubActivity.class);
        intent.putExtra("bookSelected",Parcels.wrap(book));

        setResult(RESULT_OK, intent);
        // Closes the activity, pass data to parent
        startActivity(intent);
        finish();
    }

}
