package white.zac.hw5;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import static android.content.Context.ALARM_SERVICE;

public class Util {

    //method for finding the item closest to being due
    public static TodoItem findNextDueItem(Context context){
        Uri uri = TodoProvider.CONTENT_URI;

        // set up a projection to show which columns we want to retrieve
        String[] projection = {
                TodoProvider.ID,
                TodoProvider.NAME,
                TodoProvider.DESCRIPTION,
                TodoProvider.DUETIME,
                TodoProvider.PRIORITY,
                TodoProvider.STATUS
        };

        // declare a cursor outside the try so we can close it in a finally
        Cursor cursor = null;

        TodoItem item = null;

        try {
            // ask the content resolver to find the data for the URI
            cursor = context.getContentResolver().
                    query(uri, projection, null, null, null);

            if (cursor == null || !cursor.moveToFirst()){
                return null;
            }
            // while still rows left to read
            while(!cursor.isAfterLast()){
                long currentTime = System.currentTimeMillis();
                // if the item isn't already due
                if(cursor.getLong(3) > currentTime){
                    if (item == null){
                        //not already marked as done
                        if(cursor.getString(5).equals("Pending")){
                            item = todoItemFromCursor(cursor);
                        }
                    }
                    else{
                        if(cursor.getLong(3) < item.dueTime.get()){
                            //not already marked as done
                            if(cursor.getString(5).equals("Pending")){
                                item = todoItemFromCursor(cursor);
                            }
                        }
                    }
                }
                cursor.moveToNext();
            }

        } finally {
            // BE SURE TO CLOSE THE CURSOR!!!
            if (cursor != null)
                cursor.close();
        }
        return item;
    }

    public static ArrayList<TodoItem> getDueItems(Context context){
        ArrayList<TodoItem> dueItems = new ArrayList<>();
        Uri uri = TodoProvider.CONTENT_URI;

        // set up a projection to show which columns we want to retrieve
        String[] projection = {
                TodoProvider.ID,
                TodoProvider.NAME,
                TodoProvider.DESCRIPTION,
                TodoProvider.DUETIME,
                TodoProvider.PRIORITY,
                TodoProvider.STATUS
        };

        // declare a cursor outside the try so we can close it in a finally
        Cursor cursor = null;

        //sort by duetime in ascending order
        String sortOrder = TodoProvider.DUETIME + " ASC";

        try {
            // ask the content resolver to find the data for the URI
            cursor = context.getContentResolver().
                    query(uri, projection, null, null, sortOrder);
            if( cursor != null && cursor.moveToFirst() ){
                while(!cursor.isAfterLast()){
                    if(cursor.getLong(3) < System.currentTimeMillis()){
                        // if not already marked done
                        if(!cursor.getString(5).equals("Done")){
                            dueItems.add(todoItemFromCursor(cursor));
                        }
                    }
                    cursor.moveToNext();
                }
            }

        } finally {
            // BE SURE TO CLOSE THE CURSOR!!!
            if (cursor != null)
                cursor.close();
        }

        return dueItems;
    }


    // helper method to find an item
    public static TodoItem findTodo(Context context, long id) {
        // set up a URI that represents the specific item
        Uri uri = Uri.withAppendedPath(TodoProvider.CONTENT_URI, "" + id);


        // set up a projection to show which columns we want to retrieve
        String[] projection = {
                TodoProvider.ID,
                TodoProvider.NAME,
                TodoProvider.DESCRIPTION,
                TodoProvider.DUETIME,
                TodoProvider.PRIORITY,
                TodoProvider.STATUS
        };

        // declare a cursor outside the try so we can close it in a finally
        Cursor cursor = null;
        try {
            // ask the content resolver to find the data for the URI
            cursor = context.getContentResolver().
                    query(uri, projection, null, null, null);


            // if nothing found, return null
            if (cursor == null || !cursor.moveToFirst())
                return null;

            // otherwise return the located item

            return todoItemFromCursor(cursor);
        } finally {
            // BE SURE TO CLOSE THE CURSOR!!!
            if (cursor != null)
                cursor.close();
        }
    }

    // helper method to update or insert an item
    public static void updateTodo(Context context, TodoItem todo) {
        // set up the data to store or update
        ContentValues values = new ContentValues();
        values.put(TodoProvider.NAME, todo.name.get());
        values.put(TodoProvider.DESCRIPTION, todo.description.get());
        values.put(TodoProvider.DUETIME, todo.dueTime.get());
        values.put(TodoProvider.PRIORITY, todo.priority.get());
        values.put(TodoProvider.STATUS, todo.status.get());

        // if the item didn't yet have an id, insert it and set the id on the object
        if (todo.id.get() == -1) {
            Uri uri = TodoProvider.CONTENT_URI;
            Uri insertedUri = context.getContentResolver().insert(uri, values);
            String idString = insertedUri.getLastPathSegment();
            long id = Long.parseLong(idString);
            todo.id.set(id);
            todo.dueTime.set(System.currentTimeMillis() + 10000);

        // otherwise, update the item with that id
        } else {
            // create a URI that represents the item
            Uri uri = Uri.withAppendedPath(TodoProvider.CONTENT_URI, "" + todo.id.get());
            context.getContentResolver().update(uri, values, TodoProvider.ID + "=" + todo.id.get(), null);
        }

        //alarm only sets if there is a pending item
        setAlarm(context);
    }

    public static void setAlarm(Context context) {
        //cancel any existing alarm
        cancelAlarm(context);
        TodoItem nextDue = findNextDueItem(context);

        //set the alarm if there are pending items
        if(nextDue != null){
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    nextDue.dueTime.get(), pendingIntent);
        }
    }

    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    public static void sendNotification(Context context, Notification notification){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

        //check for the next due item
        TodoItem nextDue = findNextDueItem(context);

        //if there are more pending items
        if(nextDue != null){
            setAlarm(context);
        }
    }

    public static void cancelNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
    }


    public static TodoItem todoItemFromCursor(Cursor cursor) {
        return new TodoItem(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getInt(4), cursor.getString(5));
    }

    //method for "snoozing" all due items
    public static void snoozeAllDue(Context context) {
        ArrayList<TodoItem> due = getDueItems(context);
        if(!due.isEmpty()){
            for(TodoItem item : due){
                item.status.set("Pending");
                item.dueTime.set(System.currentTimeMillis() + 10000);
                updateTodo(context, item);
            }
            setAlarm(context);

            updateWidget(context);
            cancelNotification(context);
        }
    }
    //method for marking all due items as done
    public static void setDueToDone(Context context) {
        ArrayList<TodoItem> due = getDueItems(context);
        for(TodoItem item : due){
            item.status.set("Done");
            updateTodo(context, item);
        }
        updateWidget(context);
        cancelNotification(context);
    }

    public static void updateWidget(Context context){
        //get due items, report the number due
//        int numDueItems = getDueItems(context).size();
        Intent intent = new Intent(context, WidgetProvider.class);
//        intent.putExtra("itemsDue", numDueItems);

        context.sendBroadcast(intent);
    }

}
