package com.example.searchbygenre;

import org.parceler.Parcel;

@Parcel
public class Book {

     String Title;
     String Category;
     String Description;
     String Thumbnail;

    public Book() {
    }

    public Book(String title, String category, String description, String thumbnail) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail =  thumbnail;
    }

}