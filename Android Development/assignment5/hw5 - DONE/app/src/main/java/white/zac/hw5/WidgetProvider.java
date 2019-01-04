package white.zac.hw5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int i = 0; i < appWidgetIds.length; i++){
            updateAppWidget(context, appWidgetManager, appWidgetIds[i], 0);
        }

    }
    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int itemsDue) {
        //update widget
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
        String widgetString;
        Resources res = context.getResources();
        switch (itemsDue){
            case -1:
                widgetString = "No Items Due";
                break;

            case 0:
                widgetString = "No Items Due";
                break;
            case 1:
                widgetString = res.getQuantityString(R.plurals.itemsDue, 1);
                break;
            case 2:
                widgetString = res.getQuantityString(R.plurals.itemsDue, 2);
                break;
            default:
                widgetString = res.getQuantityString(R.plurals.itemsDue, 3);
                break;
        }

        rv.setTextViewText(R.id.text, widgetString);
        rv.setViewVisibility(R.id.widgetAdd, 0);
        rv.setViewVisibility(R.id.widgetSnooze, 0);

        //adding action for tapping the "N items due" text
        Intent intent = new Intent(context, TodoListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.text, pendingIntent);

        //add action for the add new button
        Intent editIntent = new Intent(context, EditActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.widgetAdd, pendingIntent2);

        //add action for the snooze button
        Intent snoozeIntent = new Intent(context, TodoListActivity.class);
        snoozeIntent.putExtra("snooze", true);
        PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.widgetSnooze, pendingIntent3);

        appWidgetManager.updateAppWidget(appWidgetId, rv);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int numItemsDue = Util.getDueItems(context).size();
        ComponentName componentName = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

        for(int i = 0; i < appWidgetIds.length; i++){
            updateAppWidget(context, appWidgetManager, appWidgetIds[i], numItemsDue);
        }

    }
}
