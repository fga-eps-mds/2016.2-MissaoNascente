package com.example.jbbmobile.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jbbmobile.R;
import com.example.jbbmobile.controller.BooksController;
import com.example.jbbmobile.controller.HistoryController;

public class CustomAdapter extends BaseAdapter{

    private String [] nameElement;
    private Context context;
    private int [] imageId;
    private int idBook;
    private static LayoutInflater inflater = null;
    private int[] idElements;
    private int[] history;
    private HistoryController historyController;

    public CustomAdapter(AlmanacScreenActivity mainActivity, BooksController booksController, int idBook) {
        this.idElements = booksController.getElementsId(idBook);
        this.nameElement = booksController.getElementsForBook(idBook);
        this.imageId = booksController.getElementsImage(mainActivity, idBook);
        this.history = booksController.getElementsHistory(idBook);
        this.context = mainActivity;
        this.idBook = idBook;
        this.historyController = new HistoryController(context);

        historyController.loadSave();
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nameElement.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(nameElement[position]);

        roundedBitmapDrawable(position , holder.img);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent elementIntent = new Intent(CustomAdapter.this.context, ElementScreenActivity.class);
                elementIntent.putExtra("idElement", idElements[position]);
                elementIntent.putExtra("idBook", idBook);
                CustomAdapter.this.context.startActivity(elementIntent);
            }
        });

        return rowView;
    }

    private int changeColor(int history, int idElement){
        int color;
        int currentElementHistory;

        currentElementHistory = historyController.getCurrentElement();

        if(history == 1 && (currentElementHistory >idElement || currentElementHistory == -10) ){
            color = R.color.colorElementHistory;
        }else{
            color = R.color.colorGreenDark;
        }
        return  color;
    }

    private void roundedBitmapDrawable(int position , ImageView imageView){
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(imageView.getResources(), BitmapFactory.decodeResource(context.getResources(), imageId[position]));
        dr.setCornerRadius(40);

        imageView.setImageDrawable(dr);

        imageView.setPadding(10,10,10,10);
        imageView.setBackgroundResource(R.drawable.background_element);
        imageView.getBackground().setColorFilter(ContextCompat.getColor(context, changeColor(history[position], idElements[position])), PorterDuff.Mode.SRC_ATOP);

    }
}