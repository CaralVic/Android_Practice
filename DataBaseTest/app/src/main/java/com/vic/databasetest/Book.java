package com.vic.databasetest;

import android.widget.Button;

/**
 * Created by Vic on 7/31/2016.
 */
public class Book {
    private String name;
    private String author;
    private int pages;
    private double price;

    public Book(String name,String author,int pages,double price){
        this.name = name;
        this.author = author;
        this.price = price;
        this.pages = pages;
    }
    public String getName(){
        return name;
    }
    public String getAuthor(){
        return author;
    }
    public int getPages(){
        return pages;
    }
    public double getPrice(){
        return  price;
    }
}
