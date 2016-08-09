package com.vic.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private MyDatabaseHelper dbHelper;
    private EditText name,author,pages,prices;
    private List<Book> bookList = new ArrayList<Book>();

    private ListView book_listView;
    private BookAdapter bookAdapter;
    private Button add_data;
    private TextView no_data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this,"bookStore.db",null,1);


        name = (EditText)findViewById(R.id.name);
        author = (EditText)findViewById(R.id.author);
        prices = (EditText)findViewById(R.id.prices);
        pages = (EditText)findViewById(R.id.pages);
        add_data = (Button)findViewById(R.id.add_data);

        book_listView = (ListView)findViewById(R.id.book_listView);
        no_data = (TextView)findViewById(R.id.no_data);
        initBook();
        book_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Book book1 =bookAdapter.getItem(position);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Delete Book From Database")
                        .setMessage("Do you want to delete:\n\tBook:"+book1.getName()
                        +"\n\tAuthor:"+book1.getAuthor()
                                +"\n\tPrice:"+book1.getPrice())
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.beginTransaction();
                                try{
                                    db.delete("Book","name = ?",new String[] {book1.getName()});
                                    db.setTransactionSuccessful();
                                    bookList.remove(book1);
                                    bookAdapter.notifyDataSetChanged();
                                    book_listView.setSelection(bookList.size());
                                    Toast.makeText(MainActivity.this,"Deleted Book:"+book1.getName()
                                            +"successfully",Toast.LENGTH_SHORT)
                                            .show();
                                    if(bookList.size() == 0){
//                                        no_data.clearAnimation();
                                        no_data.setVisibility(View.VISIBLE);

                                    }
                                    else {
//                                        no_data.clearAnimation();
                                        no_data.setVisibility(View.INVISIBLE);
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this,"Error occurred while deleting data from database",Toast.LENGTH_SHORT)
                                            .show();
                                }
                                db.endTransaction();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();


            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String b_name = name.getText().toString();
                String b_author = author.getText().toString();
                String b_pages_string = pages.getText().toString();
                String b_price_string = prices.getText().toString();

                if(!("".equals(b_name)||"".equals(b_author)||"".equals(b_pages_string)||"".equals(b_price_string))){

                    int b_pages = Integer.parseInt(b_pages_string);
                    double b_price = Double.parseDouble(b_price_string);

                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.beginTransaction();
                    try{
                        ContentValues values = new ContentValues();
                        values.put("name",b_name);
                        values.put("author",b_author);
                        values.put("pages",b_pages);
                        values.put("price",b_price);
                        db.insert("Book",null,values);
                        db.setTransactionSuccessful();
                        bookList.add(new Book(b_name,b_author,b_pages,b_price));
                        bookAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"Add data successfully",Toast.LENGTH_SHORT)
                                .show();
                        book_listView.setSelection(bookList.size());

                        if(bookList.size() == 0){
//                            no_data.clearAnimation();
                            no_data.setVisibility(View.VISIBLE);

                        }
                        else {
//                            no_data.clearAnimation();
                            no_data.setVisibility(View.INVISIBLE);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"Error occurred while adding data",Toast.LENGTH_SHORT)
                                .show();
                    }
                    finally {
                        db.endTransaction();
                    }
                }


            }
        });
    }

    private void initBook(){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Book",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String book_name = cursor.getString(cursor.getColumnIndex("name"));
                String book_author = cursor.getString(cursor.getColumnIndex("author"));
                double book_price = cursor.getDouble(cursor.getColumnIndex("price"));
                int book_page = cursor.getInt(cursor.getColumnIndex("pages"));

                bookList.add(new Book(book_name,book_author,book_page,book_price));

            }while (cursor.moveToNext());
        }
        cursor.close();

        bookAdapter = new BookAdapter(MainActivity.this,R.layout.book_item,bookList);
        book_listView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }

}
