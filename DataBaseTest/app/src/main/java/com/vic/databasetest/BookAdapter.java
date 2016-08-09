package com.vic.databasetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Vic on 7/31/2016.
 */
public class BookAdapter extends ArrayAdapter<Book> {
    private int layoutID;

    public BookAdapter(Context context, int textViewResourceID,List<Book> objects){
        super(context,textViewResourceID,objects);
        layoutID = textViewResourceID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Book book = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView ==null){
            view= LayoutInflater.from(getContext()).inflate(layoutID,null);
            viewHolder = new ViewHolder();
            viewHolder.bookName =(TextView)view.findViewById(R.id.book_name);
            viewHolder.bookAuthor = (TextView)view.findViewById(R.id.book_author);
            viewHolder.bookPrice = (TextView)view.findViewById(R.id.book_price);
            viewHolder.bookPage = (TextView)view.findViewById(R.id.book_pages);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder =(ViewHolder)view.getTag();
        }
        viewHolder.bookName.setText(book.getName());
        viewHolder.bookAuthor.setText(book.getAuthor());
        viewHolder.bookPage.setText(" "+book.getPages());
        viewHolder.bookPrice.setText(" "+book.getPrice());

        return view;
    }
    class ViewHolder{
        TextView bookName;
        TextView bookAuthor;
        TextView bookPage;
        TextView bookPrice;
    }
//    @Override
//    public void notifyDataSetChanged(){
//        super.notifyDataSetChanged();
//        if(objects.size() == 0){
//            no_data.clearAnimation();
//            no_data.setVisibility(View.VISIBLE);
//            Toast.makeText(getContext(),"Override and visible",Toast.LENGTH_SHORT)
//                    .show();
//        }
//        else {
//            no_data.clearAnimation();
//            no_data.setVisibility(View.INVISIBLE);
//            Toast.makeText(getContext(),"Override and invisible",Toast.LENGTH_SHORT)
//                    .show();
//        }
//
//    }
}
