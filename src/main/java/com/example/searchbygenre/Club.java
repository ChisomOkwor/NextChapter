package com.example.searchbygenre;

public class Club {
    public String club_name;
    public Book book_assigned;

    public Club() {
    }

    public Club(String club_name){
        this.club_name = club_name;
    }

    public Club(String club_name, Book book_assigned) {
        this.club_name = club_name;
        this.book_assigned = book_assigned;
    }

    public void setBook(Book book_assigned) {
        this.book_assigned = book_assigned;
    }


    public String getClubName() {
        return club_name;
    }

    public Book getBook() {
        return book_assigned;
    }

}
