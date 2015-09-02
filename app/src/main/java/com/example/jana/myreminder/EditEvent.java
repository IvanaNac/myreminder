package com.example.jana.myreminder;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jmagdeska on 8/31/15.
 */
public class EditEvent extends ActionBarActivity {

    TextView insertDate;
    TextView insertTime;
    String realPath;
    DBHandler handler = new DBHandler(this,null,null,1);

    int day_x, month_x, year_x;
    int minute_x, hour_x;
    static final int DIALOG_ID = 0;
    static final int DIALOG_ID2 = 1;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        showDialogOnButtonClick();
        showTimePickerDialog();
        onEditButtonClicked();

    }
    // For EDITING the Single event from the LIST
    private void onEditButtonClicked() {

        EditText nameInput = (EditText) findViewById(R.id.nameInput);
        EditText commentText = (EditText) findViewById(R.id.commentText);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView inserDate = (TextView) findViewById(R.id.insertDate);
        TextView inserTime = (TextView) findViewById(R.id.insertTime);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            String name = bundle.getString("name");
            nameInput.setText(name);

            String comment = bundle.getString("details");
            commentText.setText(comment);

            String image = bundle.getString("image");
            Drawable drawable = Drawable.createFromPath(image);
            imageView.setImageDrawable(drawable);
            realPath = image;

            String dateTime = bundle.getString("dateTime");
            String[] separate = dateTime.split(" ");
            inserDate.setText(separate[0]);
            inserTime.setText(separate[1]);

            String[] cal = separate[0].split("-");
            year_x = Integer.parseInt(cal[0]);
            month_x = Integer.parseInt(cal[1]);
            day_x = Integer.parseInt(cal[2]);
            insertDate.setText(day_x + " / " + (month_x+1) + " / " + year_x);

            String[] time = separate[1].split(":");
            hour_x = Integer.parseInt(time[0]);
            minute_x = Integer.parseInt(time[1]);
            insertTime.setText(hour_x + ":" + minute_x);
        }
    }


    //DatePickerDialog
    public void showDialogOnButtonClick(){
        insertDate = (TextView)findViewById(R.id.insertDate);
        insertDate.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_ID) {
            return new DatePickerDialog(this, datePickerListener, year_x, month_x, day_x);
        }
        else if(id == DIALOG_ID2) {
            return new TimePickerDialog(this, timePickerListener, hour_x, minute_x, true );
        }
        else return null;
    }

    public DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            day_x = dayOfMonth;
            month_x = monthOfYear + 1;
            year_x = year;
            insertDate.setText(day_x + " / " + month_x + " / " + year_x);
        }
    };

    //TimePicker Dialog
    public void showTimePickerDialog() {
        insertTime = (TextView)findViewById(R.id.insertTime);
        insertTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID2);
                    }
                }
        );
    }

    protected TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            minute_x = minute;
            insertTime.setText(hour_x + " : " + minute_x);
        }
    };

    //Pick a background from gallery
    public void changePhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView)findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
                realPath = getRealPathFromURI(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Get image path
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    //Print details
    public void printDetails(){
        TextView printDetails = (TextView)findViewById(R.id.printDetails);
        DBHandler handler = new DBHandler(this,null,null,1);
        Cursor c = handler.getEvent();
        String print = "";
        c.moveToFirst();

        do {
            print += c.getString(0) + " " + c.getString(1) + " " + c.getString(2) + " " + c.getString(3) + " " + "\n";
            printDetails.setText(print);
        } while (c.moveToNext());
    }

    //Check if db is empty
    public boolean isEmpty () {
        DBHandler handler = new DBHandler(this,null,null,1);
        Cursor c = handler.getEvent();
        String print = "";
        c.moveToFirst();

        do {
        } while (c.moveToNext());
        if(c.getCount() == 0) return true;
        else return false;
    }

    //SetReminder
    public void reminder (Date date, Time time, String title, String photo, String comment, int ID) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(),date.getMonth(), date.getDate(), time.getHours(), time.getMinutes(),0);

        scheduleNotification(getNotification(title, calendar.getTimeInMillis()), calendar.getTimeInMillis(), ID);
    }

    private void scheduleNotification(Notification notification, long when, int ID) {

        Intent notificationIntent = new Intent(this, OnAlarmReceiver.class);
        notificationIntent.putExtra(OnAlarmReceiver.NOTIFICATION_ID, ID);
        notificationIntent.putExtra(OnAlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, when, pendingIntent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotification(String content, long when) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle("You have a new event!");
        builder.setWhen(when);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.icon);
        return builder.build();
    }

    //Save changes
    public void saveChanges (View view) {
        EditText name = (EditText)findViewById(R.id.nameInput);
        final String nameInput = name.getText().toString();
        final Date insertDate = new Date(year_x,month_x,day_x);
        final Time insertTime = new Time(hour_x,minute_x,0);
        EditText commentText = (EditText)findViewById(R.id.commentText);
        final String commentInput = commentText.getText().toString();
        int notificationID = 0;

        if(nameInput.equals("")) { Toast.makeText(this, "Please add a name!", Toast.LENGTH_SHORT).show(); }
        if(realPath.equals("")) { Toast.makeText(this, "Please add a photo!", Toast.LENGTH_SHORT).show(); }
        else {

            SQLiteDatabase db = handler.getWritableDatabase();
            Events event = new Events(nameInput, insertDate, insertTime, realPath, commentInput);
            Bundle bundle = getIntent().getExtras();
            handler.editEvent(event, db, bundle.getString("name"));

            if (isEmpty()) {
                notificationID = 1;
            } else {
                notificationID++;
            }

            db.close();
            ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
            if (toggle.isChecked()) {
                reminder(insertDate, insertTime, nameInput, realPath, commentInput, notificationID);
            }

            Toast.makeText(this, "Event added!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Reminders.class);
            startActivity(intent);
        }
    }
}