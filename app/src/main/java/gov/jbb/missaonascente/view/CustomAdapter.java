package gov.jbb.missaonascente.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.BooksController;
import gov.jbb.missaonascente.controller.HistoryController;
import gov.jbb.missaonascente.controller.RegisterElementController;

public class CustomAdapter extends BaseAdapter{

    private String [] nameElement;
    private Context context;
    private int [] imageId;
    private int idBook;
    private static LayoutInflater inflater = null;
    private int[] idElements;
    private int[] history;
    private HistoryController historyController;
    private BooksController booksController;


    public CustomAdapter(AlmanacScreenActivity mainActivity, BooksController booksController, int idBook) {
        this.booksController = booksController;
        this.idElements = booksController.getElementsId(idBook);
        this.nameElement = booksController.getElementsForBook(idBook);
        this.imageId = booksController.getElementsImage(mainActivity, idBook);
        this.history = booksController.getElementsHistory(idBook);
        this.context = mainActivity;
        this.idBook = booksController.getBook(idBook).getIdBook();
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
        Holder holder = new Holder();
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

    private int changeColor(int history){
        int backgroundElement;
        int currentElementHistory;

        booksController.currentPeriod();
        currentElementHistory = historyController.getCurrentElement();


        if(currentElementHistory > history && booksController.getCurrentPeriod() == idBook && history != 0){
            backgroundElement = R.drawable.background_almanac_element_history;
        }else{
            backgroundElement = R.drawable.background_almanac_element;
        }
        return  backgroundElement;
    }

    private void roundedBitmapDrawable(int position , ImageView imageView){
        String path = RegisterElementController.findImagePathByAssociation(context, idElements[position]);

        Bitmap image = null;

        if(!path.equals("")){
            image = RegisterElementController.loadImageFromStorage(path, context);
        }

        if(image == null){
            Log.d("Path", "id a custom = " + idElements[position]);
            image = BitmapFactory.decodeResource(context.getResources(), imageId[position]);
        }

        Log.d("Path2", "path2 custom = " + image);

        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(imageView.getResources(), image);
        dr.setCornerRadius(40);

        imageView.setImageDrawable(dr);

        imageView.setPadding(10,10,10,10);
        imageView.setBackgroundResource(changeColor(history[position]));

    }
}