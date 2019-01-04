package white.zac.hw5;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import java.util.ArrayList;

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        //initialize notification details
        NotificationCompat.InboxStyle details = new NotificationCompat.InboxStyle()
                .setBigContentTitle("Details")
                .setSummaryText("Due Items");

        //add notification details lines
        ArrayList<TodoItem> dueItems = Util.getDueItems(context);
        //limit due items shown to 5
        int maxItems = 5;
        for(int i = 0; i < dueItems.size(); i ++){
            TodoItem dueItem = dueItems.get(i);
            if(maxItems > 0){
                details.addLine(dueItem.name.get());
                maxItems--;
            }
            dueItem.status.set("Due");
            Util.updateTodo(context, dueItem);
        }

        //notification information:

        String itemName = "";
        if(!dueItems.isEmpty()){
            itemName = dueItems.get(0).name.get();
        }

        Intent intent1 = new Intent(context, TodoListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        //add actions
        Intent doneIntent = new Intent(context, TodoListActivity.class);
        doneIntent.putExtra("done", true);
        PendingIntent doneAction = PendingIntent.getActivity(context, 2, doneIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(context, TodoListActivity.class);
        snoozeIntent.putExtra("snooze", true);
        PendingIntent snoozeAction = PendingIntent.getActivity(context, 3, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(context)
                .setStyle(details)
                .setNumber(dueItems.size())
                .setContentTitle("Todo Alarm")
                .setContentText(itemName + " is due!")
                .setNumber(dueItems.size())
                .setSmallIcon(R.drawable.ic_assignment_white_24dp)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_replay_10_black_32dp, "Snooze", snoozeAction)
                .addAction(R.drawable.ic_check_circle_black_32dp, "Done", doneAction)
                .setAutoCancel(true)
                .setOngoing(true)
                .build();

        //update the widget and send the notification
        Util.updateWidget(context);
        Util.sendNotification(context, notification);
    }
}