package com.example.jana.myreminder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Reminders extends ActionBarActivity {

    DBHandler myhandler = new DBHandler(this,null,null,1);
    private List<Events> myEvents=new ArrayList<Events>();

      @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

          getResults();
          creatMyListView();
          registerClickCallBack();

          TextView txt = (TextView) findViewById(R.id.textView4);
          Typeface font = Typeface.createFromAsset(getAssets(), "icomoon.ttf");
          txt.setTypeface(font);
          txt.setText("\ue604");
      }

    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Events clickedEvent = myEvents.get(position);
                String name = clickedEvent.getmName();
                String datetime = clickedEvent.getDateTime();
                String photo = clickedEvent.getmPhoto();
                String comment = clickedEvent.getmComment();

                Intent intent = new Intent(Reminders.this, SingleEvent.class);

                intent.putExtra("nameOut", name);
                intent.putExtra("timeOut", datetime);
                intent.putExtra("single_event_id", photo);
                intent.putExtra("detailsOut", comment);
                startActivity(intent);
            }
        });
    }

    private void getResults() {
        Cursor c = myhandler.getEvent();
        c.moveToFirst();
        do {
            Events event = new Events(c.getString(0),c.getString(1),c.getString(2),c.getString(3));
             myEvents.add(event);
        } while (c.moveToNext());

    }
   private void creatMyListView(){
       ArrayAdapter<Events> adapter=new CustomAdapter();
       ListView list = (ListView) findViewById(R.id.listView);
       list.setAdapter(adapter);
   }

    private class CustomAdapter extends ArrayAdapter<Events> {

        public CustomAdapter() {
            super(Reminders.this, R.layout.custom_row, myEvents);
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View customView=convertView;

            if(customView==null) {
                customView=getLayoutInflater().inflate(R.layout.custom_row, parent, false);
            }

            // find the event to work with
            Events currentEvent = myEvents.get(position);

            //fill the view
            ImageView custom_Image=(ImageView)customView.findViewById(R.id.imageView);
            Bitmap myBitmap = BitmapFactory.decodeFile(currentEvent.getmPhoto());
            custom_Image.setImageBitmap(myBitmap);

            TextView custom_Name=(TextView)customView.findViewById(R.id.textView);
            custom_Name.setText( currentEvent.getmName());

            TextView custom_Date=(TextView)customView.findViewById(R.id.textView2);
            String[] date=currentEvent.getDateTime().split(" ");
            custom_Date.setText( date[0]);

        return customView;
        }
    }

    public void onClickAdd(View view){
        Intent intent=new Intent(this,AddEvent.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.add_event:
                Intent intent = new Intent(this, AddEvent.class);
                startActivity(intent);
                break;
        }

        return true;
    }


}
