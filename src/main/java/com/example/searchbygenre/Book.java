package com.example.searchbygenre;

public class Book {

    private String Title;
    private String Category;
    private String Description;


    public Book() {
    }

    public Book(String title, String category, String description) {
        Title = title;
        Category = category;
        Description = description;

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




    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    //public void setThumbnail(String thumbnail) {
      //  ThumbnailURL = thumbnailurl;
  // }
}