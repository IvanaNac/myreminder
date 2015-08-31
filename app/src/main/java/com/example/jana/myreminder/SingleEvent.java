package com.example.jana.myreminder;

import android.app.Activity;
<<<<<<< HEAD
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
=======
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
>>>>>>> d57718ef1181e21330f67ce8fa07a60e0242e957
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import java.io.ByteArrayOutputStream;

=======
>>>>>>> d57718ef1181e21330f67ce8fa07a60e0242e957

/**
 * Created by Nacevski on 29.06.2015.
 */
public class SingleEvent extends Activity {
    DBHandler myHandler = new DBHandler(this,null,null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_event);


        TextView nameOut= (TextView)findViewById(R.id.nameOut);
        TextView timeOut =(TextView)findViewById(R.id.timeOut);
       // View photo = (View)findViewById(R.id.single_event_id);
        TextView detailOut = (TextView)findViewById(R.id.detailsOut);
        //Button btnEdit = (Button) findViewById(R.id.editButton);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
<<<<<<< HEAD
        TextView editText=(TextView)findViewById(R.id.editBtn);
        TextView deleteText = (TextView)findViewById(R.id.deleteBtn);
=======
        TextView editText=(TextView)findViewById(R.id.edit);
        TextView deleteText = (TextView)findViewById(R.id.delete);
>>>>>>> d57718ef1181e21330f67ce8fa07a60e0242e957
        editText.setTypeface(font);
        deleteText.setTypeface(font);
        editText.setText(getResources().getText(R.string.edit_icon));
        deleteText.setText((getResources().getText(R.string.delete_icon)));

        Intent i = getIntent();
        String name = i.getStringExtra("nameOut");
        String date = i.getStringExtra("timeOut");
        String details = i.getStringExtra("detailsOut");
        String photo = i.getStringExtra("single_event_id");

        nameOut.setText(name);
        timeOut.setText(date);
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        Drawable drawable=Drawable.createFromPath(photo);
        imageView.setImageDrawable(drawable);
        detailOut.setText(details);

    }
    public void deleteEvent(View view) {
        SQLiteDatabase db = myHandler.getWritableDatabase();
        TextView nameOut = (TextView)findViewById(R.id.nameOut);
        db.execSQL("delete from " + DBHandler.TABLE_NAME + " where " + DBHandler.COLUMN_NAME + "='" + nameOut.getText() + "'");
        Toast.makeText(this, "Event deleted!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Reminders.class);
        startActivity(intent);
    }

    public void editEvent(View view) {

        TextView nameOut = (TextView)findViewById(R.id.nameOut);
        TextView timeOut =(TextView)findViewById(R.id.timeOut);
        TextView detailOut = (TextView)findViewById(R.id.detailsOut);
        ImageView imageView = (ImageView)findViewById(R.id.imageView2);

<<<<<<< HEAD
        Intent intent = new Intent(SingleEvent.this, EditEvent.class);

        intent.putExtra("name", nameOut.getText().toString());
        intent.putExtra("dateTime", timeOut.getText().toString());
        Intent i = getIntent();
        String photo = i.getStringExtra("single_event_id");
        intent.putExtra("image",photo);
        intent.putExtra("details", detailOut.getText().toString());
=======
        Intent intent = new Intent(SingleEvent.this, AddEvent.class);

        intent.putExtra("name", nameOut.getText().toString());
        intent.putExtra("dateTime", timeOut.getText().toString());
        intent.putExtra("image",imageView.toString());
        intent.putExtra("details",detailOut.getText().toString());
>>>>>>> d57718ef1181e21330f67ce8fa07a60e0242e957

        startActivity(intent);

    }
<<<<<<< HEAD

=======
>>>>>>> d57718ef1181e21330f67ce8fa07a60e0242e957
}

