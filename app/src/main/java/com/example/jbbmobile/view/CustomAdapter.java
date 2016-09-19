package com.example.jbbmobile.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbbmobile.R;

public class CustomAdapter extends BaseAdapter{

    private String [] result;
    private Context context;
    private int [] imageId;
    private int idBook;
    private static LayoutInflater inflater=null;
    private int[] idElements;

    public CustomAdapter(AlmanacScreenActivity mainActivity, String[] prgmNameList, int[] prgmImages,
                         int idBook, int[] idElements) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        this.idBook = idBook;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.idElements = idElements;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);

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
}