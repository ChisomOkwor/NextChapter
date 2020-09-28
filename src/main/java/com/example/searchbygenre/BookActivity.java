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

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BookActivity extends AppCompatActivity {
    TextView tvtitle,tvdescription,tvcategory;
    ImageView img;
    Button selectBook;

    String Title;
    String Description;
    String Genre;
    String Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);

        selectBook = (Button)  findViewById(R.id.button);

        // Recieve data
        Intent intent = getIntent();
        Title = intent.getExtras().getString("Title");
        Description = intent.getExtras().getString("Description");
        Genre = intent.getExtras().getString("Category");
        Image = intent.getExtras().getString("Thumbnail") ;

        // Setting values
        tvtitle.setText(Title);
        tvdescription.setText(Description);


        Bitmap bmp = null;
        try {
            URL url = new URL(Image);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        img.setImageBitmap(bmp);
      //  img.LoadImageFromWebOperations(Image);
     //   img.setImageResource(Image);

        selectBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClubActivity(Title, Description, Image);
            }
        });
    }
    public void openClubActivity(String title, String description, String image){
        Intent intent = new Intent(this, ClubActivity.class);
        intent.putExtra("Title", title);
        intent.putExtra("Thumbnail", title);
        intent.putExtra("Description", title);
        startActivity(intent);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
